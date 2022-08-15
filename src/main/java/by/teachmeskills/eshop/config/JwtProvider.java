package by.teachmeskills.eshop.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Log4j
public class JwtProvider {
    @Value("$(jwt.secret.access)")
    private String jwtAccess;

    @Value("$(jwt.secret.refresh)")
    private String jwtRefresh;

    public String generateAccessToken(String login) {
        Date date = Date.from(LocalDateTime.now().plusSeconds(20)
                .atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtAccess)
                .compact();
    }

    public String generateRefreshToken(String login) {
        Date date = Date.from(LocalDateTime.now().plusDays(15)
                .atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtRefresh)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, jwtAccess);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, jwtRefresh);
    }

    private boolean validateToken(String token, String jwt) {
        try {
            Jwts.parser().setSigningKey(jwt).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt");
        } catch (SignatureException sEx) {
            log.error("Invalid signature");
        } catch (Exception e) {
            log.error("invalid token");
        }
        return false;
    }

    public String getLoginFromAccessToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtAccess).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getLoginFromRefreshToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtRefresh).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}

package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.config.JwtProvider;
import by.teachmeskills.eshop.dto.JwtRequest;
import by.teachmeskills.eshop.dto.JwtResponse;
import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.dto.converters.UserConverter;
import by.teachmeskills.eshop.entities.Role;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.RoleRepository;
import by.teachmeskills.eshop.repositories.UserRepository;
import by.teachmeskills.eshop.services.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static by.teachmeskills.eshop.utils.EshopConstants.ID_ROLE_USER;


@Service
@Log4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, @Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User create(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        Optional<Role> role = roleRepository.findById(ID_ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role.get());
        entity.setRoles(roles);
        return userRepository.save(entity);
    }

    @Override
    public List<User> read() {
        return userRepository.findAll();
    }

    @Override
    public User update(User entity) {
        Optional<User> user = userRepository.findById(entity.getId());
        if (user.isPresent()) {
            return userRepository.save(entity);
        }
        log.error("User doesn't exist");
        return null;
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findUserById(int id) {
        Optional<User> loggedUser = userRepository.findById(id);
        if (loggedUser.isPresent()) {
            return userConverter.toDto(loggedUser.get());
        } else {
            log.warn("User doesn't exist in the DB");
            return null;
        }
    }

    @Override
    public UserDto registration(UserDto userDto) {
        if (Optional.ofNullable(userDto).isPresent()
                && Optional.ofNullable(userDto.getLogin()).isPresent()
                && Optional.ofNullable(userDto.getName()).isPresent()
                && Optional.ofNullable(userDto.getSurname()).isPresent()
                && Optional.ofNullable(userDto.getEmail()).isPresent()
                && Optional.ofNullable(userDto.getPassword()).isPresent()) {
            User user = userConverter.fromDto(userDto);
            if (checkUserExists(user)) {
                log.warn("User doesn't exist in the DB");
                return null;
            } else {
                User createdUser = create(user);
                return userConverter.toDto(createdUser);
            }
        } else {
            log.warn("UserDto parameters don't satisfy input conditions");
            return null;
        }
    }

    @Override
    public JwtResponse authenticate(UserDto userDto) {
        UserDto logUserDto = findUserByLogin(userDto.getLogin());
        String accessToken = jwtProvider.generateAccessToken(logUserDto.getLogin());
        String refreshToken = jwtProvider.generateRefreshToken(logUserDto.getLogin());
        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse getNewAccessToken(JwtRequest jwtRequest) {
        String refreshToken = jwtRequest.getRefreshToken();
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            String login = jwtProvider.getLoginFromRefreshToken(refreshToken);
            UserDto logUserDto = findUserByLogin(login);
            String newAccessToken = jwtProvider.generateAccessToken(logUserDto.getLogin());
            return new JwtResponse(newAccessToken);
        }
        return new JwtResponse(null);
    }

    private boolean checkUserExists(User user) {
        User userFromBase = userRepository.getUserById(user.getId());
        return Optional.ofNullable(userFromBase).isPresent();
    }

    private UserDto findUserByLogin(String login) {
        if (Optional.ofNullable(login).isPresent()) {
            User loggedUser = userRepository.getUserByLogin(login);
            if (Optional.ofNullable(loggedUser).isPresent()) {
                return userConverter.toDto(loggedUser);
            } else {
                log.warn("User doesn't exist in the DB");
                return null;
            }
        }
        log.warn("Login doesn't satisfy input conditions");
        return null;
    }
}

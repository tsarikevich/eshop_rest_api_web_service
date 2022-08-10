package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.JwtRequest;
import by.teachmeskills.eshop.dto.JwtResponse;
import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Authorize user",
            description = "Authorize user for Eshop")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully logged in",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User is not found - user is not exist in DB"
            )})
    @PostMapping
    public JwtResponse login(@Valid @RequestBody(required = false) UserDto userDto) {
        return userService.authenticate(userDto);
    }

    @PostMapping("/token")
    public JwtResponse getNewAccessToken(@RequestBody JwtRequest jwtRequest) {
        return userService.getNewAccessToken(jwtRequest);
    }
}
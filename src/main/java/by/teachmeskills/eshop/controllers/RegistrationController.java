package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto) {
        UserDto createdUserDto = userService.registration(userDto);
        if (Optional.ofNullable(createdUserDto).isPresent()) {
            return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
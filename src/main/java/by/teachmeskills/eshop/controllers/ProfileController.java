package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDto> getProfilePage(@RequestBody UserDto userDto) {
        UserDto logUserDto = userService.findUserDtoById(userDto);
        if (Optional.ofNullable(logUserDto).isPresent()) {
            return new ResponseEntity<>(logUserDto, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

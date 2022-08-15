package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.JwtRequest;
import by.teachmeskills.eshop.dto.JwtResponse;
import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.entities.User;

public interface UserService extends BaseService<User> {

    UserDto findUserById(int id);

    UserDto registration(UserDto userDto);

    JwtResponse authenticate(UserDto userDto);

    JwtResponse getNewAccessToken(JwtRequest jwtRequest);
}

package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.entities.User;

public interface UserService extends BaseService<User> {
    UserDto findUserDtoByLoginAndPassword(UserDto userDto);

    UserDto findUserDtoById(UserDto userDto);

    UserDto registration(UserDto userDto);
}

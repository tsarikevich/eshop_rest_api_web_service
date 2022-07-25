package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.entities.User;

public interface UserService extends BaseService<User> {
    UserDto findUserByLoginAndPassword(String login, String password);

    UserDto findUserById(int id);

    UserDto registration(UserDto userDto);
}

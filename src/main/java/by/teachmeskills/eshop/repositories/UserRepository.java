package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.User;

public interface UserRepository {

    User getUserByLoginAndPassword(String login, String password);

    User findUserById(int id);
}

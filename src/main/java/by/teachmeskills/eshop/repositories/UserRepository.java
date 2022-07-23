package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.User;

public interface UserRepository {

    User getUserFromBaseByLoginAndPassword(User user);

    User getUserFromBaseById(User user);

    User findUserById(int id);
}

package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {

    User getUserByLogin(String login);

    Optional<User> findUserByLogin(String login);

    User getUserById(int id);
}

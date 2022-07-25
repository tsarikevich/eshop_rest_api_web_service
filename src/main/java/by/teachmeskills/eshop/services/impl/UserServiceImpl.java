package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.dto.converters.UserConverter;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.impl.UserRepositoryImpl;
import by.teachmeskills.eshop.services.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class UserServiceImpl implements UserService {
    private final UserRepositoryImpl userRepository;
    private final UserConverter userConverter;

    public UserServiceImpl(UserRepositoryImpl userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public User create(User entity) {
        return userRepository.create(entity);
    }

    @Override
    public List<User> read() {
        return userRepository.read();
    }

    @Override
    public User update(User entity) {
        return userRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public UserDto findUserByLoginAndPassword(String login, String password) {
        if (Optional.ofNullable(login).isPresent()
                && Optional.ofNullable(password).isPresent()) {
            User loggedUser = userRepository.getUserByLoginAndPassword(login, password);
            if (Optional.ofNullable(loggedUser).isPresent()) {
                return userConverter.toDto(loggedUser);
            } else {
                log.warn("User doesn't exist in the DB");
                return null;
            }
        }
        log.warn("Login or password don't satisfy input conditions");
        return null;
    }

    @Override
    public UserDto findUserById(int id) {
        User loggedUser = userRepository.findUserById(id);
        if (Optional.ofNullable(loggedUser).isPresent()) {
            return userConverter.toDto(loggedUser);
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
                User createdUser = userRepository.create(user);
                return userConverter.toDto(createdUser);
            }
        } else {
            log.warn("UserDto parameters don't satisfy input conditions");
            return null;
        }
    }

    private boolean checkUserExists(User user) {
        User userFromBase = userRepository.findUserById(user.getId());
        return Optional.ofNullable(userFromBase).isPresent();
    }
}

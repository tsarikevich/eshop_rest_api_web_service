package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.dto.converters.UserConverter;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.impl.UserRepositoryImpl;
import by.teachmeskills.eshop.services.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepositoryImpl userRepository;
    private final UserConverter userConverter;
    private final ProductRepository productRepository;

    public UserServiceImpl(UserRepositoryImpl userRepository, UserConverter userConverter, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.productRepository = productRepository;
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
    public UserDto findUserDtoByLoginAndPassword(UserDto userDto) {
        if (Optional.ofNullable(userDto).isPresent()
                && Optional.ofNullable(userDto.getLogin()).isPresent()
                && Optional.ofNullable(userDto.getPassword()).isPresent()) {
            User user = userConverter.fromDto(userDto);
            User loggedUser = userRepository.getUserFromBaseByLoginAndPassword(user);
            List<Order> productIntegerMap = loggedUser.getOrders();
            Map<Product, Integer> productIntegerMap1 = new HashMap<>();
            for (Order order : productIntegerMap) {
                productIntegerMap1=productRepository.getProductsByOrderId(order.getId());
            }
            if (Optional.ofNullable(loggedUser).isPresent()) {
                UserDto userDtoFromDb = userConverter.toDto(loggedUser);
                return userDtoFromDb;
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public UserDto findUserDtoById(UserDto userDto) {
        if (Optional.ofNullable(userDto).isPresent()
                && Optional.ofNullable(userDto.getId()).isPresent()) {
            User user = userConverter.fromDto(userDto);
            User loggedUser = userRepository.getUserFromBaseById(user);
            if (Optional.ofNullable(loggedUser).isPresent()) {
                UserDto userDtoFromDb = userConverter.toDto(loggedUser);
                return userDtoFromDb;
            } else {
                return null;
            }
        }
        return null;
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
            if (isUserInBase(user)) {
                return null;
            } else {
                User createdUser = userRepository.create(user);
                UserDto createdUserDto = userConverter.toDto(createdUser);
                return createdUserDto;
            }
        } else {
            return null;
        }
    }

    private boolean isUserInBase(User user) {
        User userFromBase = userRepository.getUserFromBaseById(user);
        return Optional.ofNullable(userFromBase).isPresent();
    }
}

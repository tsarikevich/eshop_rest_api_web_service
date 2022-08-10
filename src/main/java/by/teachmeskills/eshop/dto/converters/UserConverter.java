package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UserConverter {
    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;
    private final RoleConverter roleConverter;

    public UserConverter(OrderConverter orderConverter, OrderRepository orderRepository, RoleConverter roleConverter) {
        this.orderConverter = orderConverter;
        this.orderRepository = orderRepository;
        this.roleConverter = roleConverter;
    }

    public UserDto toDto(User user) {
        return Optional.ofNullable(user).map(u -> UserDto.builder()
                        .id(u.getId())
                        .birthDate(u.getBirthDate())
                        .name(u.getName())
                        .surname(u.getSurname())
                        .email(u.getEmail())
                        .login(u.getLogin())
                        .password(u.getPassword())
                        .balance(u.getBalance())
                        .orders(Optional.ofNullable(u.getOrders()).map(orders -> orders.stream().map(orderConverter::toDto).toList()).orElse(List.of()))
                        .roles(Optional.ofNullable(u.getRoles()).map(roles -> roles.stream().map(roleConverter::toDto).collect(Collectors.toSet())).orElse(Set.of()))
                        .build())
                .orElse(null);
    }

    public User fromDto(UserDto userDto) {
        return Optional.ofNullable(userDto).map(ud -> User.builder()
                        .id(ud.getId())
                        .birthDate(ud.getBirthDate())
                        .name(ud.getName())
                        .surname(ud.getSurname())
                        .email(ud.getEmail())
                        .login(ud.getLogin())
                        .password(ud.getPassword())
                        .balance(ud.getBalance())
                        .orders(orderRepository.getOrdersByUserId(ud.getId()))
                        .build())
                .orElse(null);
    }
}

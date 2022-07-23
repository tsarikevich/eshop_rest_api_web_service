package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.OrderDto;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderConverter {
    private final ProductConverter productConverter;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderConverter(ProductConverter productConverter, UserRepository userRepository, ProductRepository productRepository) {
        this.productConverter = productConverter;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public OrderDto toDto(Order order) {
        return Optional.ofNullable(order).map(o -> OrderDto.builder()
                        .id(o.getId())
                        .date(o.getDate())
                        .price(o.getPrice())
                        .userId(o.getUser().getId())
                        .products(o.getProducts().keySet().stream().collect(Collectors.toMap(
                                p->productConverter.toDto(p).toString(),
                                o.getProducts()::get)))
                        .build())
                .orElse(null);
    }

    public Order fromDto(OrderDto orderDto) {
        return Optional.ofNullable(orderDto).map(od -> Order.builder()
                        .id(od.getId())
                        .date(od.getDate())
                        .price(od.getPrice())
                        .user(userRepository.findUserById(od.getUserId()))
                        .products(productRepository.getProductsByOrderId(orderDto.getId()))
                        .build())
                .orElse(null);
    }
}

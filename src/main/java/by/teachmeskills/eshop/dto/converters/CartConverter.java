package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.CartDto;
import by.teachmeskills.eshop.entities.Cart;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CartConverter {
    private final ProductConverter productConverter;

    public CartConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    public CartDto toDto(Cart cart) {
        return Optional.ofNullable(cart).map(c -> CartDto.builder()
                .products(c.getProducts().keySet().stream().collect(Collectors.toMap(
                        productConverter::toDto,
                        c.getProducts()::get)))
                .totalCost(c.getTotalCost())
                .build()).orElse(null);
    }
}

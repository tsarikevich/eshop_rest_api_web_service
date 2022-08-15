package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.CartDto;
import by.teachmeskills.eshop.dto.converters.CartConverter;
import by.teachmeskills.eshop.entities.Cart;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log4j
public class CartService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CartConverter cartConverter;
    private final UserRepository userRepository;

    public CartService(ProductRepository productRepository, OrderRepository orderRepository, CartConverter cartConverter, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.cartConverter = cartConverter;
        this.userRepository = userRepository;
    }

    public CartDto addProductToCart(int productId, Cart cart) {
        try {
            Product product = productRepository.getProductById(productId);
            cart.addProduct(product);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return cartConverter.toDto(cart);
    }

    public CartDto deleteProductFromCart(int productId, Cart cart) {
        try {
            Product product = productRepository.getProductById(productId);
            cart.deleteProduct(product);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return cartConverter.toDto(cart);
    }

    public String checkout(int userId, Cart cart) {
        try {
            Order order = Order.builder()
                    .price(cart.getTotalPrice())
                    .date(LocalDateTime.now())
                    .user(userRepository.findById(userId).get())
                    .products(cart.getProducts())
                    .build();
            orderRepository.save(order);
            cart.clear();
        } catch (Exception e) {
            log.error(e.getMessage());
            return "An error occurred while ordering";
        }

        return "Products was successfully ordered";
    }
}

package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.CartDto;
import by.teachmeskills.eshop.dto.converters.CartConverter;
import by.teachmeskills.eshop.entities.Cart;
import by.teachmeskills.eshop.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static by.teachmeskills.eshop.utils.EshopConstants.PRODUCT_ID;
import static by.teachmeskills.eshop.utils.EshopConstants.USER_ID;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;
    private final Cart cart = new Cart();

    public CartController(CartService cartService, CartConverter cartConverter) {
        this.cartService = cartService;
        this.cartConverter = cartConverter;
    }

    @GetMapping
    public ResponseEntity<CartDto> gerCartData() {
        CartDto cartDto = cartConverter.toDto(cart);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CartDto> addProductToCart(@RequestParam(PRODUCT_ID) int id) {
        CartDto cartDtoWithNewProduct = cartService.addProductToCart(id, cart);
        if (Optional.ofNullable(cartDtoWithNewProduct).isPresent()) {
            return new ResponseEntity<>(cartDtoWithNewProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<CartDto> deleteProductFromCart(@RequestParam(PRODUCT_ID) int id) {
        CartDto cartDtoWithoutDeletedProduct = cartService.deleteProductFromCart(id, cart);
        if (Optional.ofNullable(cartDtoWithoutDeletedProduct).isPresent()) {
            return new ResponseEntity<>(cartDtoWithoutDeletedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestParam(USER_ID) int userId) {
        String message = cartService.checkout(userId, cart);
        if (message.equals("Products was successfully ordered")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}

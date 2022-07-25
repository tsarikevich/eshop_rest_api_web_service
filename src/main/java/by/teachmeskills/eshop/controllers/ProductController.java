package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.exceptions.UpdateException;
import by.teachmeskills.eshop.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;

@Tag(name = "product", description = "The Product API")
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductData(@Valid @Min(1) @PathVariable int id) {
        ProductDto productDto = productService.getProductData(id);
        if (Optional.ofNullable(productDto).isPresent()) {
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        if (Optional.ofNullable(createdProduct).isPresent()) {
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) throws UpdateException {
        ProductDto updatedProduct = productService.updateProduct(productDto);
        if (Optional.ofNullable(updatedProduct).isPresent()) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestBody ProductDto productDto) {
        String message = productService.deleteProduct(productDto);
        if (message.equals("Product was deleted")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}

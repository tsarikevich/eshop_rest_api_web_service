package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static by.teachmeskills.eshop.utils.EshopConstants.CATEGORY_ID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final ProductService productService;

    public CategoryController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> openCategoryProductPage(@RequestParam(CATEGORY_ID) int id) {
        return new ResponseEntity<>(productService.getCategoryProductsData(id), HttpStatus.OK);
    }
}

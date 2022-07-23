package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.repositories.ImageRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    private final CategoryRepository categoryRepository;
    private final ImageConverter imageConverter;
    private final ImageRepository imageRepository;

    public ProductConverter(CategoryRepository categoryRepository, ImageConverter imageConverter, ImageRepository imageRepository) {
        this.categoryRepository = categoryRepository;
        this.imageConverter = imageConverter;
        this.imageRepository = imageRepository;
    }

    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .price(product.getPrice())
                .images(product.getImages().stream().map(imageConverter::toDto).toList())
                .build();
    }

    public Product fromDto(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .images(imageRepository.getImagesByProductId(productDto.getId()))
                .category(categoryRepository.findById(productDto.getCategoryId()))
                .build();
    }
}

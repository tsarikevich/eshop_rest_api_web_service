package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.ImageDto;
import by.teachmeskills.eshop.entities.Image;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ImageConverter {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ImageConverter(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ImageDto toDto(Image image) {
        if (Optional.ofNullable(image.getProduct()).isPresent()) {
            return ImageDto.builder()
                    .id(image.getId())
                    .primaryFlag(image.isPrimaryFlag())
                    .imagePath(image.getImagePath())
                    .productId(image.getProduct().getId())
                    .categoryId(image.getCategoryImage().getId())
                    .build();
        } else {
            return ImageDto.builder()
                    .id(image.getId())
                    .primaryFlag(image.isPrimaryFlag())
                    .imagePath(image.getImagePath())
                    .categoryId(image.getCategoryImage().getId())
                    .build();
        }
    }

    public Image fromDto(ImageDto imageDto) {
        return Image.builder()
                .primaryFlag(imageDto.isPrimaryFlag())
                .imagePath(imageDto.getImagePath())
                .product(productRepository.getProductById(imageDto.getProductId()))
                .categoryImage(categoryRepository.findById(imageDto.getCategoryId()).get())
                .build();
    }
}

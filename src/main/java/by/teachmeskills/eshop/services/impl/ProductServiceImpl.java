package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.ImageDto;
import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.dto.converters.ImageConverter;
import by.teachmeskills.eshop.dto.converters.ProductConverter;
import by.teachmeskills.eshop.entities.Image;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.exceptions.UpdateException;
import by.teachmeskills.eshop.repositories.impl.ImageRepositoryImpl;
import by.teachmeskills.eshop.repositories.impl.ProductRepositoryImpl;
import by.teachmeskills.eshop.services.ProductService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryImpl productRepository;
    private final ImageRepositoryImpl imageRepository;
    private final ProductConverter productConverter;
    private final ImageConverter imageConverter;

    public ProductServiceImpl(ProductRepositoryImpl productRepository, ImageRepositoryImpl imageRepository, ProductConverter productConverter, ImageConverter imageConverter) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.productConverter = productConverter;
        this.imageConverter = imageConverter;
    }

    @Override
    public Product create(Product entity) {
        return productRepository.create(entity);
    }

    @Override
    public List<Product> read() {
        return productRepository.read();
    }

    @Override
    public Product update(Product entity) {
        return productRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        productRepository.delete(id);
    }

    @Override
    public ProductDto getProductData(int id) {
        Product product = productRepository.getProductById(id);
        ProductDto productDto = productConverter.toDto(product);
        List<Image> productImages = imageRepository.getImagesByProductId(id);
        List<ImageDto> productImagesDto = productImages.stream().map(imageConverter::toDto).collect(Collectors.toList());
        productDto.setImages(productImagesDto);
        return productDto;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        try {
            Product product = productConverter.fromDto(productDto);
            if (Optional.ofNullable(product.getName()).isEmpty() || checkProductExists(product)) {
                log.warn("Product doesn't exists in the DB or product name is null");
                return null;
            } else {
                Product createdProduct = create(product);
                return productConverter.toDto(createdProduct);
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) throws UpdateException {
        try {
            Product product = productConverter.fromDto(productDto);
            Product updatedProduct = productRepository.update(product);
            return productConverter.toDto(updatedProduct);
        } catch (Exception e) {
            throw new UpdateException("Product cannot be updated");
        }
    }

    @Override
    public String deleteProduct(ProductDto productDto) {
        try {
            delete(productDto.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
        return "Product was successfully deleted";
    }

    private boolean checkProductExists(Product product) {
        Product productFromDB = productRepository.getProductByName(product.getName());
        return Optional.ofNullable(productFromDB).isPresent();
    }

    @Override
    public List<ProductDto> getCategoryProductsData(int id) {
        List<Product> categoryProducts = productRepository.getProductsByCategoryId(id);
        return categoryProducts.stream().map(productConverter::toDto).toList();
    }

    @Override
    public List<ProductDto> findAllProductsByRequest(String request) {
        List<Product> products = productRepository.findProductsByRequest(request);
        return products.stream().map(productConverter::toDto).toList();
    }
}

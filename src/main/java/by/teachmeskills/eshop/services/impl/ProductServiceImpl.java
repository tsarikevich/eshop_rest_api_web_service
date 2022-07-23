package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.ImageDto;
import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.dto.converters.ImageConverter;
import by.teachmeskills.eshop.dto.converters.ProductConverter;
import by.teachmeskills.eshop.entities.Image;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.impl.ImageRepositoryImpl;
import by.teachmeskills.eshop.repositories.impl.ProductRepositoryImpl;
import by.teachmeskills.eshop.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepositoryImpl productRepository;
    private ImageRepositoryImpl imageRepository;
    private ProductConverter productConverter;
    private ImageConverter imageConverter;

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
            if (Optional.ofNullable(product.getName()).isEmpty() || isProductInBase(product)) {
                return null;
            } else {
                Product createdProduct = create(product);
                return productConverter.toDto(createdProduct);
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        try {
            Product product = productConverter.fromDto(productDto);
            Product updatedProduct = productRepository.update(product);
            ProductDto updatedProductDto = productConverter.toDto(updatedProduct);
            return updatedProductDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String deleteProduct(ProductDto productDto) {
        try {
            delete(productDto.getId());
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Product was successfully deleted";
    }

    private boolean isProductInBase(Product product) {
        Product productFromDB = productRepository.getProductFromDBByName(product);
        return Optional.ofNullable(productFromDB).isPresent();
    }

    @Override
    public List<ProductDto> getCategoryProductsData(int id) {
        List<Product> categoryProducts = productRepository.getProductsByCategoryId(id);
        List<ProductDto> productDtoList = categoryProducts.stream().map(productConverter::toDto).toList();
        return productDtoList;
    }

    @Override
    public List<ProductDto> findAllProductsByRequest(String request) {
        List<Product> products = productRepository.findProductsByRequest(request);
        List<ProductDto> productDtoList = products.stream().map(productConverter::toDto).toList();
        return productDtoList;
    }
}

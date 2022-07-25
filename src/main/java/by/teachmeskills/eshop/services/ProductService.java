package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.exceptions.UpdateException;

import java.util.List;

public interface ProductService extends BaseService<Product> {

    List<ProductDto> getCategoryProductsData(int id);

    List<ProductDto> findAllProductsByRequest(String request);

    ProductDto getProductData(int id);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto) throws UpdateException;

    String deleteProduct(ProductDto productDto);
}

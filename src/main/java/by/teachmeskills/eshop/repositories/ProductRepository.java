package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Product;

import java.util.List;
import java.util.Map;

public interface ProductRepository {
    Product getProductById(int productId);

    List<Product> getProductsByCategoryId(int categoryId);

    List<Product> findProductsByRequest(String request);

    Product getProductByName(String nameProduct);

    Map<Product, Integer> getProductsByOrderId(int id);
}

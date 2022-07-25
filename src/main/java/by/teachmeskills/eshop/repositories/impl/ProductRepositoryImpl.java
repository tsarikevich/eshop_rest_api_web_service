package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.ProductRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Product create(Product entity) {
        entityManager.persist(entity);
        return entity;
    }

    public List<Product> read() {
        return entityManager.createQuery("select p from Product p").getResultList();
    }

    public Product update(Product entity) {
        entityManager.merge(entity);
        return entity;
    }

    public void delete(int id) {
        entityManager.remove(entityManager.find(Product.class, id));
    }

    @Override
    public Product getProductById(int productId) {
        return entityManager.find(Product.class, productId);
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId) {
        Query query = entityManager.createQuery("select p from Product p where p.category.id=:categoryId");
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    @Override
    public List<Product> findProductsByRequest(String request) {
        String setRequest = "%" + request + "%";
        Query query = entityManager.createQuery("select p from Product p where p.name like :setRequest or p.description like: setRequest order by p.id desc");
        query.setParameter("setRequest", setRequest);
        return query.getResultList();
    }

    @Override
    public Product getProductByName(String nameProduct) {
        Query query = entityManager.createQuery("select p from Product p where p.name=:name");
        query.setParameter("name", nameProduct);
        List<Category> categoryList = query.getResultList();
        if (categoryList.size() != 0) {
            return (Product) query.getSingleResult();
        } else {
            return null;
        }
    }

    @Override
    public Map<Product, Integer> getProductsByOrderId(int id) {
        Order order = entityManager.find(Order.class, id);
        return order.getProducts();
    }
}


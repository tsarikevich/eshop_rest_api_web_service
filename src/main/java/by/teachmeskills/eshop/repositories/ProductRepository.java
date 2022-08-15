package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    Product getProductById(int id);

    List<Product> getProductByCategoryId(int id);

    Page<Product> findAllByCategoryId(int id, Pageable pageable);

    @Query("select p from Product p where p.name like ?1 or p.description like ?1 order by p.id desc")
    List<Product> findAllByNameOrDescriptionContaining(String request);
}

package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Image;
import by.teachmeskills.eshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query("select i from Image i where i.product.id is null")
    List<Image> getImagesByProductExists();

    List<Image> getImagesByProductId(int productId);

    @Query("select distinct i from Image i where i.product.id in (select key(o.products).id from Order o where o.user.id=?1) and i.primaryFlag = true")
    List<Image> getImagesByPrimaryFlagAndUserId(int userId);

    List<Image> getImagesByProductIn(List<Product> products);
}

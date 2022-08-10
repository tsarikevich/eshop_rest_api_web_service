package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findCategoryById(int id);
}

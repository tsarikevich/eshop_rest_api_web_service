package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Category;

public interface CategoryRepository {
    Category findById(int id);
    Category getCategoryFromDBByName(Category category);
}

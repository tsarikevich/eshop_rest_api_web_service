package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.exceptions.UpdateException;

import java.util.List;

public interface CategoryService extends BaseService<Category> {
    List<CategoryDto> getAllCategories();

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto) throws UpdateException;

    String deleteCategory(CategoryDto categoryDto);
}

package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.dto.ImageDto;
import by.teachmeskills.eshop.dto.converters.CategoryConverter;
import by.teachmeskills.eshop.dto.converters.ImageConverter;
import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Image;
import by.teachmeskills.eshop.exceptions.UpdateException;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.repositories.ImageRepository;
import by.teachmeskills.eshop.services.CategoryService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final CategoryConverter categoryConverter;
    private final ImageConverter imageConverter;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ImageRepository imageRepository, CategoryConverter categoryConverter, ImageConverter imageConverter) {
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.categoryConverter = categoryConverter;
        this.imageConverter = imageConverter;
    }


    @Override
    public Category create(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public List<Category> read() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Category entity) {
        Optional<Category> user = categoryRepository.findById(entity.getId());
        if (user.isPresent()) {
            return categoryRepository.save(entity);
        }
        log.error("Category doesn't exist");
        return null;
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = read();
        List<Image> categoriesImages = imageRepository.getImagesByProductExists();
        List<CategoryDto> categoryDto = categories.stream().map(categoryConverter::toDto).collect(Collectors.toList());
        List<ImageDto> imageDto = categoriesImages.stream().map(imageConverter::toDto).collect(Collectors.toList());
        for (CategoryDto cDto : categoryDto) {
            for (ImageDto iDto : imageDto) {
                if (cDto.getId() == iDto.getCategoryId()) {
                    cDto.setImage(iDto);
                }
            }
        }
        return categoryDto;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryConverter.fromDto(categoryDto);
        if (Optional.ofNullable(category.getName()).isEmpty() || checkCategoryExists(category)) {
            log.info("Category name is null OR category's already been in the DB");
            return null;
        } else {
            Category createdCategory = create(category);
            return categoryConverter.toDto(createdCategory);
        }
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) throws UpdateException {
        try {
            Category category = categoryConverter.fromDto(categoryDto);
            Category updatedCategory = categoryRepository.save(category);
            return categoryConverter.toDto(updatedCategory);
        } catch (Exception e) {
            throw new UpdateException("Category cannot be updated");
        }
    }

    @Override
    public String deleteCategory(CategoryDto categoryDto) {
        try {
            categoryRepository.deleteById(categoryDto.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
        return "Category was successfully deleted";
    }

    private boolean checkCategoryExists(Category category) {
        Optional<Category> categoryFromDB = categoryRepository.findById(category.getId());
        return categoryFromDB.isPresent();
    }
}

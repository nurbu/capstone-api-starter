package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Category;
import org.yearup.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        // get all categories
        return categoryRepository.findAll();
    }

    public Category getById(int categoryId) {
        // get category by id
        Category category = categoryRepository.findById(categoryId);
        if (category == null) return null;
        return category;
    }

    public Category create(Category category) {
        // create a new category
        category.setCategoryId(0);
        return categoryRepository.save(category);
    }

    public Category update(int categoryId, Category category) {
        // update category and return the updated category
        Category currentCategory = categoryRepository.findById(categoryId);
        if (currentCategory == null) {
            currentCategory.setName(category.getName());
            currentCategory.setDescription(category.getDescription());
            return categoryRepository.save(currentCategory);
        }
        return null;
    }

    public void delete(int categoryId) {
        // delete category
    }
}

package lcs.example.taskmanager.service;

import java.util.List;
import org.springframework.stereotype.Service;
import lcs.example.taskmanager.exceptions.DataConflict;
import lcs.example.taskmanager.exceptions.NotFound;
import lcs.example.taskmanager.model.Category;
import lcs.example.taskmanager.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    public Category findId(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new NotFound("Category with ID " + id + " not found."));
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = findId(id);

        if (!category.getProjects().isEmpty()) {
            throw new DataConflict(
                "Category with ID " + id + " has " + category.getProjects().size()
                + " project(s) linked to it. Remove or reassign them before deleting the category."
            );
        }

        categoryRepository.deleteById(id);
    }
}
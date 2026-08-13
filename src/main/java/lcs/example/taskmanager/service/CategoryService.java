package lcs.example.taskmanager.service;

import java.util.List;
import org.springframework.stereotype.Service;
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
        if (!categoryRepository.existsById(id)) {
            throw new NotFound("Category with ID " + id + " not found.");
        }
        categoryRepository.deleteById(id);
    }
}
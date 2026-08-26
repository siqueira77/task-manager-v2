package lcs.example.taskmanager.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lcs.example.taskmanager.dto.CategoryRequestDTO;
import lcs.example.taskmanager.dto.CategoryResponseDTO;
import lcs.example.taskmanager.exceptions.DataConflict;
import lcs.example.taskmanager.exceptions.NotFound;
import lcs.example.taskmanager.model.Category;
import lcs.example.taskmanager.model.User;
import lcs.example.taskmanager.repository.CategoryRepository;
import lcs.example.taskmanager.security.CurrentUserProvider;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CurrentUserProvider currentUserProvider;

    public CategoryService(CategoryRepository categoryRepository, CurrentUserProvider currentUserProvider) {
        this.categoryRepository = categoryRepository;
        this.currentUserProvider = currentUserProvider;
    }

    private CategoryResponseDTO convertToDTO(Category category) {
        return new CategoryResponseDTO(
            category.getId(),
            category.getName(),
            category.getProjects().size()
        );
    }

    public List<CategoryResponseDTO> listCategories() {
        User currentUser = currentUserProvider.getCurrentUser();
        return categoryRepository.findAllByOwner(currentUser)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public Category findId(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new NotFound("Category with ID " + id + " not found."));

        User currentUser = currentUserProvider.getCurrentUser();
        if (!category.getOwner().getId().equals(currentUser.getId())) {
            throw new NotFound("Category with ID " + id + " not found.");
        }

        return category;
    }

    public CategoryResponseDTO findCategory(Long id) {
        return convertToDTO(findId(id));
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO data) {
        Category category = new Category();
        category.setName(data.name());
        category.setOwner(currentUserProvider.getCurrentUser());

        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO data) {
        Category category = findId(id);
        category.setName(data.name());

        Category updatedCategory = categoryRepository.save(category);
        return convertToDTO(updatedCategory);
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

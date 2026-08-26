package lcs.example.taskmanager.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lcs.example.taskmanager.dto.CategoryRequestDTO;
import lcs.example.taskmanager.dto.CategoryResponseDTO;
import lcs.example.taskmanager.service.CategoryService;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponseDTO> listCategories() {
        return categoryService.listCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findCategory(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO data) {
        CategoryResponseDTO savedCategory = categoryService.createCategory(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO data) {
        CategoryResponseDTO updatedCategory = categoryService.updateCategory(id, data);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

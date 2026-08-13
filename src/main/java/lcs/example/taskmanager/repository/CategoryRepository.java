package lcs.example.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import lcs.example.taskmanager.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
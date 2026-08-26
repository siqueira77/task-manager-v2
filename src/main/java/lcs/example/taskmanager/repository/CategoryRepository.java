package lcs.example.taskmanager.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import lcs.example.taskmanager.model.Category;
import lcs.example.taskmanager.model.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOwner(User owner);
}

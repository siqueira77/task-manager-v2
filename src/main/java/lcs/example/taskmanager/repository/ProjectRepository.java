package lcs.example.taskmanager.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import lcs.example.taskmanager.model.Project;
import lcs.example.taskmanager.model.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByOwner(User owner);
}

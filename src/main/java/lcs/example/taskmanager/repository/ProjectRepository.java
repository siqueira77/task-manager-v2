package lcs.example.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import lcs.example.taskmanager.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
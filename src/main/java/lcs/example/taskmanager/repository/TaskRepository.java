package lcs.example.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lcs.example.taskmanager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByTitle(String tittle);
    
}

package lcs.example.taskmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lcs.example.taskmanager.dto.TaskRequestDTO;
import lcs.example.taskmanager.dto.TaskResponseDTO;
import lcs.example.taskmanager.model.Task;
import lcs.example.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }
    
    @GetMapping
    public List<TaskResponseDTO> listTasks(){ // <-- CORRIGIDO AQUI PARA TaskResponseDTO
        return taskService.listTasks();
    }

    @GetMapping("/{id}") 
    public ResponseEntity<Task> findTask(@PathVariable Long id) {
        Task task = taskService.findId(id);
        return ResponseEntity.ok(task);
    }
    
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskData) {
        TaskResponseDTO savedTask = taskService.createTask(taskData);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskData) {
        Task task = taskService.findId(id); 
        
        task.setTitle(taskData.getTitle());
        task.setDescription(taskData.getDescription());
        task.setCompleted(taskData.isCompleted());
        
        Task updatedTask = taskService.saveTask(task);
        return ResponseEntity.ok(updatedTask);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id); 
        return ResponseEntity.noContent().build();
    }
}
package lcs.example.taskmanager.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lcs.example.taskmanager.dto.TaskRequestDTO;
import lcs.example.taskmanager.dto.TaskResponseDTO;
import lcs.example.taskmanager.dto.TaskUpdateDTO;
import lcs.example.taskmanager.service.TaskService;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }
    
    @GetMapping
    public List<TaskResponseDTO> listTasks(){ 
        return taskService.listTasks();
    }
    
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskData) {
        TaskResponseDTO savedTask = taskService.createTask(taskData);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateDTO taskData) {
        TaskResponseDTO updatedTask = taskService.updateTask(id, taskData);
        return ResponseEntity.ok(updatedTask);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
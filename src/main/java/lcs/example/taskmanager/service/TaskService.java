package lcs.example.taskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lcs.example.taskmanager.exceptions.NotFound;
import lcs.example.taskmanager.model.Task;
import lcs.example.taskmanager.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService (TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<Task> listTasks(){
        return taskRepository.findAll();
    }

    public Task findId(Long id){
        return taskRepository.findById(id)
            .orElseThrow(()-> new NotFound("Task with ID "+id +" not found.") );
    }

    public Task saveTask (Task task){
        return taskRepository.save(task);
    }

    public void deleteTask (Long id){

        if (!taskRepository.existsById(id)) {
            throw new NotFound("Task with ID "+id +" not found.");
        }
        taskRepository.deleteById(id);
    }
}

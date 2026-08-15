package lcs.example.taskmanager.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import lcs.example.taskmanager.dto.TaskRequestDTO;
import lcs.example.taskmanager.dto.TaskResponseDTO;
import lcs.example.taskmanager.exceptions.NotFound;
import lcs.example.taskmanager.model.Project;
import lcs.example.taskmanager.model.Task;
import lcs.example.taskmanager.repository.ProjectRepository;
import lcs.example.taskmanager.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository; 

    // ÚNICO CONSTRUTOR: Agora ele injeta os dois repositórios
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository){
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    // Traduz de Entidade para DTO de Saída
    private TaskResponseDTO convertToDTO(Task task) {
        return new TaskResponseDTO(
            task.getId(),
            task.getTitle(),
            task.isCompleted(),
            task.getProject() != null ? task.getProject().getName() : "Sem projeto"
        );
    }

    // ÚNICO listTasks: Retornando a lista limpa com DTOs
    public List<TaskResponseDTO> listTasks(){
        return taskRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public TaskResponseDTO createTask(TaskRequestDTO data) {
        // 1. Busca o projeto no banco pelo ID que veio no DTO
        Project project = projectRepository.findById(data.projectId())
            .orElseThrow(() -> new NotFound("Project with ID " + data.projectId() + " not found."));

        // 2. Monta a nova tarefa (Entidade)
        Task task = new Task();
        task.setTitle(data.title());
        task.setCompleted(false); // Nova tarefa sempre começa falsa
        task.setProject(project); // Amarra a tarefa ao projeto

        // 3. Salva no banco e converte a resposta
        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }
    
    // --- MÉTODOS ANTIGOS MANTIDOS (Serão úteis para o Update e Delete) ---

    public Task findId(Long id){
        return taskRepository.findById(id)
            .orElseThrow(()-> new NotFound("Task with ID " + id + " not found."));
    }

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        if (!taskRepository.existsById(id)) {
            throw new NotFound("Task with ID " + id + " not found.");
        }
        taskRepository.deleteById(id);
    }
}
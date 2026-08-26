package lcs.example.taskmanager.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lcs.example.taskmanager.dto.TaskRequestDTO;
import lcs.example.taskmanager.dto.TaskResponseDTO;
import lcs.example.taskmanager.dto.TaskUpdateDTO;
import lcs.example.taskmanager.exceptions.NotFound;
import lcs.example.taskmanager.model.Project;
import lcs.example.taskmanager.model.Task;
import lcs.example.taskmanager.model.User;
import lcs.example.taskmanager.repository.ProjectRepository;
import lcs.example.taskmanager.repository.TaskRepository;
import lcs.example.taskmanager.security.CurrentUserProvider;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final CurrentUserProvider currentUserProvider;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository,
                        CurrentUserProvider currentUserProvider) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.currentUserProvider = currentUserProvider;
    }

    private TaskResponseDTO convertToDTO(Task task) {
        return new TaskResponseDTO(
            task.getId(),
            task.getTitle(),
            task.isCompleted(),
            task.getProject() != null ? task.getProject().getName() : "Sem projeto"
        );
    }

    public List<TaskResponseDTO> listTasks(){
        User currentUser = currentUserProvider.getCurrentUser();
        return taskRepository.findAllByOwner(currentUser)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public Task findId(Long id){
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new NotFound("Task with ID " + id + " not found."));

        User currentUser = currentUserProvider.getCurrentUser();
        if (!task.getOwner().getId().equals(currentUser.getId())) {
            throw new NotFound("Task with ID " + id + " not found.");
        }

        return task;
    }

    private Project findOwnedProject(Long projectId, User currentUser) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new NotFound("Project with ID " + projectId + " not found."));

        if (!project.getOwner().getId().equals(currentUser.getId())) {
            throw new NotFound("Project with ID " + projectId + " not found.");
        }
        return project;
    }

    public TaskResponseDTO createTask(TaskRequestDTO data) {
        User currentUser = currentUserProvider.getCurrentUser();
        Project project = findOwnedProject(data.projectId(), currentUser);

        Task task = new Task();
        task.setTitle(data.title());
        task.setCompleted(false);
        task.setProject(project);
        task.setOwner(currentUser);

        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }

    public TaskResponseDTO updateTask(Long id, TaskUpdateDTO data) {
        Task task = findId(id);
        Project project = findOwnedProject(data.projectId(), currentUserProvider.getCurrentUser());

        task.setTitle(data.title());
        task.setCompleted(data.completed());
        task.setProject(project);

        Task updatedTask = taskRepository.save(task);
        return convertToDTO(updatedTask);
    }

    public void deleteTask(Long id){
        Task task = findId(id);
        taskRepository.deleteById(task.getId());
    }
}

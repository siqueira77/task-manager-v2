package lcs.example.taskmanager.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lcs.example.taskmanager.dto.ProjectRequestDTO;
import lcs.example.taskmanager.dto.ProjectResponseDTO;
import lcs.example.taskmanager.exceptions.DataConflict;
import lcs.example.taskmanager.exceptions.NotFound;
import lcs.example.taskmanager.model.Category;
import lcs.example.taskmanager.model.Project;
import lcs.example.taskmanager.model.User;
import lcs.example.taskmanager.repository.CategoryRepository;
import lcs.example.taskmanager.repository.ProjectRepository;
import lcs.example.taskmanager.security.CurrentUserProvider;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CategoryRepository categoryRepository;
    private final CurrentUserProvider currentUserProvider;

    public ProjectService(ProjectRepository projectRepository, CategoryRepository categoryRepository,
                           CurrentUserProvider currentUserProvider) {
        this.projectRepository = projectRepository;
        this.categoryRepository = categoryRepository;
        this.currentUserProvider = currentUserProvider;
    }

    private ProjectResponseDTO convertToDTO(Project project) {
        return new ProjectResponseDTO(
            project.getId(),
            project.getName(),
            project.getCategory() != null ? project.getCategory().getName() : "Sem Categoria"
        );
    }

    public List<ProjectResponseDTO> listProjects() {
        User currentUser = currentUserProvider.getCurrentUser();
        return projectRepository.findAllByOwner(currentUser)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public Project findId(Long id) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new NotFound("Project with ID " + id + " not found."));

        User currentUser = currentUserProvider.getCurrentUser();
        if (!project.getOwner().getId().equals(currentUser.getId())) {
            throw new NotFound("Project with ID " + id + " not found.");
        }

        return project;
    }

    private Category findOwnedCategory(Long categoryId, User currentUser) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new NotFound("Category with ID " + categoryId + " not found."));

        if (!category.getOwner().getId().equals(currentUser.getId())) {
            throw new NotFound("Category with ID " + categoryId + " not found.");
        }
        return category;
    }

    public ProjectResponseDTO createProject(ProjectRequestDTO data) {
        User currentUser = currentUserProvider.getCurrentUser();
        Category category = findOwnedCategory(data.categoryId(), currentUser);

        Project project = new Project();
        project.setName(data.title());
        project.setCategory(category);
        project.setOwner(currentUser);

        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO data) {
        Project project = findId(id);
        Category category = findOwnedCategory(data.categoryId(), currentUserProvider.getCurrentUser());

        project.setName(data.title());
        project.setCategory(category);

        Project updatedProject = projectRepository.save(project);
        return convertToDTO(updatedProject);
    }

    public void deleteProject(Long id) {
        Project project = findId(id);
        if (!project.getTasks().isEmpty()) {
            throw new DataConflict(
                "Project with ID " + id + " has " + project.getTasks().size()
                + " task(s) linked to it. Remove or reassign them before deleting the project."
            );
        }
        projectRepository.deleteById(id);
    }
}

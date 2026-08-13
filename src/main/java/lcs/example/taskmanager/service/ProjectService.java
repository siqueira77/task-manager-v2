package lcs.example.taskmanager.service;

import java.util.List;
import org.springframework.stereotype.Service;
import lcs.example.taskmanager.exceptions.NotFound;
import lcs.example.taskmanager.model.Project;
import lcs.example.taskmanager.repository.ProjectRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> listProjects() {
        return projectRepository.findAll();
    }

    public Project findId(Long id) {
        return projectRepository.findById(id)
            .orElseThrow(() -> new NotFound("Project with ID " + id + " not found."));
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new NotFound("Project with ID " + id + " not found.");
        }
        projectRepository.deleteById(id);
    }
}
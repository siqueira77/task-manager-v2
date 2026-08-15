package lcs.example.taskmanager.service;

import java.util.List;
import org.springframework.stereotype.Service;
import lcs.example.taskmanager.exceptions.DataConflict;
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
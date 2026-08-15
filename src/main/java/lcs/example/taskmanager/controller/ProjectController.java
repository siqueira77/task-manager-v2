package lcs.example.taskmanager.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lcs.example.taskmanager.model.Project;
import lcs.example.taskmanager.service.ProjectService;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> listProjects() {
        return projectService.listProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> findProject(@PathVariable Long id) {
        Project project = projectService.findId(id);
        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project savedProject = projectService.saveProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectData) {
        Project project = projectService.findId(id);
        
        project.setName(projectData.getName());
        project.setCategory(projectData.getCategory()); 
        
        Project updatedProject = projectService.saveProject(project);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
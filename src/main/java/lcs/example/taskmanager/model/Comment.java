package lcs.example.taskmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getText() { 
        return text; 
    }
    public void setText(String text) { 
        this.text = text; 
    }

    public Project getProject() { 
        return project; 
    }
    public void setProject(Project project) { 
        this.project = project; 
    }
}
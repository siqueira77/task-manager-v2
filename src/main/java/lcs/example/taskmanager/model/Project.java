package lcs.example.taskmanager.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "project", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnore 
    private List<Task> tasks = new ArrayList<>();

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }

    public List<Task> getTasks() { 
        return tasks; 
    }
    public void setTasks(List<Task> tasks) { 
        this.tasks = tasks; 
    }

    public Category getCategory() { 
        return category; 
    }
    public void setCategory(Category category) { 
        this.category = category; 
    }
}
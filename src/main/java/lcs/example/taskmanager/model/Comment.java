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
    @JoinColumn(name = "tasks_id")
    private Task task;

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

    public Task getTask() { 
        return task; 
    }
    public void setTask(Task task) { 
        this.task = task; 
    }
}
package lcs.example.taskmanager.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lcs.example.taskmanager.model.Comment;
import lcs.example.taskmanager.service.CommentService;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> listComments() {
        return commentService.listComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> findComment(@PathVariable Long id) {
        Comment comment = commentService.findId(id);
        return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment savedComment = commentService.saveComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment commentData) {
        Comment comment = commentService.findId(id);
        
        comment.setText(commentData.getText());
        comment.setProject(commentData.getProject()); 
        
        Comment updatedComment = commentService.saveComment(comment);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
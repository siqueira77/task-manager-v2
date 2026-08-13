package lcs.example.taskmanager.service;

import java.util.List;
import org.springframework.stereotype.Service;
import lcs.example.taskmanager.exceptions.NotFound;
import lcs.example.taskmanager.model.Comment;
import lcs.example.taskmanager.repository.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> listComments() {
        return commentRepository.findAll();
    }

    public Comment findId(Long id) {
        return commentRepository.findById(id)
            .orElseThrow(() -> new NotFound("Comment with ID " + id + " not found."));
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new NotFound("Comment with ID " + id + " not found.");
        }
        commentRepository.deleteById(id);
    }
}
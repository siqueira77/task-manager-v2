package lcs.example.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import lcs.example.taskmanager.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.Comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByAuthor_SystemUserId(Long systemUserId);
}

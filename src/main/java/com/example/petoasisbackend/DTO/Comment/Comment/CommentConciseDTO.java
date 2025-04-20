package com.example.petoasisbackend.DTO.Comment.Comment;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUMinimumDTO;
import com.example.petoasisbackend.Model.Comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentConciseDTO implements ModelDTO<Comment> {
    private Long commentId;
    private GSUMinimumDTO author;
    private String content;
    private LocalDateTime postDate;

    public static CommentConciseDTO fromComment(Comment comment) {
        return new CommentConciseDTO(
                comment.getCommentId(),
                GSUMinimumDTO.fromGSU(comment.getAuthor()),
                comment.getContent(),
                comment.getPostDate()
        );
    }
}

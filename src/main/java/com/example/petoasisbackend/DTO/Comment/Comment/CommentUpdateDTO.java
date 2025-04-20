package com.example.petoasisbackend.DTO.Comment.Comment;

import com.example.petoasisbackend.Model.Comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateDTO {
    private Long commentId;
    private String content;
    private LocalDateTime modificationDate;

    public static CommentUpdateDTO fromComment(Comment comment) {
        return new CommentUpdateDTO(
                comment.getCommentId(),
                comment.getContent(),
                comment.getModificationDate()
        );
    }
}

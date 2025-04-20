package com.example.petoasisbackend.DTO.Comment.Comment;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentMinimumDTO implements ModelDTO<Comment> {
    private Long commentId;

    public static CommentMinimumDTO fromComment(Comment comment) {
        return new CommentMinimumDTO(
                comment.getCommentId()
        );
    }
}

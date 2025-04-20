package com.example.petoasisbackend.DTO.Comment.Comment;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUMinimumDTO;
import com.example.petoasisbackend.Model.Comment.Comment;
import com.example.petoasisbackend.Model.Enum.CommentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentMediumDTO implements ModelDTO<Comment> {
    private Long commentId;
    private GSUMinimumDTO author;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime modificationDate;
    private CommentType type;

    public static CommentMediumDTO fromComment(Comment comment) {
        return new CommentMediumDTO(
                comment.getCommentId(),
                GSUMinimumDTO.fromGSU(comment.getAuthor()),
                comment.getContent(),
                comment.getPostDate(),
                comment.getModificationDate(),
                comment.getType()
        );
    }
}

package com.example.petoasisbackend.DTO.Comment.Comment;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUVerboseDTO;
import com.example.petoasisbackend.Model.Comment.Comment;
import com.example.petoasisbackend.Model.Enum.CommentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentVerboseDTO implements ModelDTO<Comment> {
    private Long commentId;

    private GSUVerboseDTO author;

    private String content;

    private LocalDateTime postDate;

    private LocalDateTime modificationDate;

    private CommentType type;

    private Long parentId;

    public static CommentVerboseDTO fromComment(Comment comment) {
        return new CommentVerboseDTO(
                comment.getCommentId(),
                GSUVerboseDTO.fromGSU(comment.getAuthor()),
                comment.getContent(),
                comment.getPostDate(),
                comment.getModificationDate(),
                comment.getType(),
                comment.getParentId()
        );
    }
}

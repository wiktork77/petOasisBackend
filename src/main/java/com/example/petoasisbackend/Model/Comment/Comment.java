package com.example.petoasisbackend.Model.Comment;

import com.example.petoasisbackend.Model.Enum.CommentType;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Request.Comment.CommentUpdateRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "system_user_id")
    private GeneralSystemUser author;

    @Column(length = 2048, nullable = false)
    private String content;

    private LocalDateTime postDate;

    private LocalDateTime modificationDate;

    private CommentType type;

    private Long parentId;

    public Comment(GeneralSystemUser author, String content, LocalDateTime postDate, CommentType type) {
        this.author = author;
        this.content = content;
        this.postDate = postDate;
        this.type = type;
    }

    public void update(CommentUpdateRequest request) {
        this.content = request.getContent();
        this.modificationDate = LocalDateTime.now();
    }
}
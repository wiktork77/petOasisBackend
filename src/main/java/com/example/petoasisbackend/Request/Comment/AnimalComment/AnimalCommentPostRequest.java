package com.example.petoasisbackend.Request.Comment.AnimalComment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AnimalCommentPostRequest {
    @NotNull(message = "must not be null")
    private Long systemUserId;

    @NotNull(message = "must not be null")
    private Long animalId;

    @NotBlank(message = "must not be blank")
    private String content;
}

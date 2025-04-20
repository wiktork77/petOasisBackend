package com.example.petoasisbackend.Request.Animal;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnimalPictureChangeRequest {
    @NotBlank(message = "must not be blank")
    private String url;
}

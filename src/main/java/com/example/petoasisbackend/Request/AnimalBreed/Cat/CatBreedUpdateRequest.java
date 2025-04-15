package com.example.petoasisbackend.Request.AnimalBreed.Cat;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CatBreedUpdateRequest {
    @NotBlank(message = "must not be blank")
    private String breedName;
}

package com.example.petoasisbackend.Request.AnimalBreed.Dog;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DogBreedUpdateRequest {
    @NotBlank(message = "must not be blank")
    private String breedName;
}

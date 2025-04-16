package com.example.petoasisbackend.Request.Animal.Dog;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DogChangeBreedRequest {
    @NotBlank(message = "must not be blank")
    private String breedName;
}

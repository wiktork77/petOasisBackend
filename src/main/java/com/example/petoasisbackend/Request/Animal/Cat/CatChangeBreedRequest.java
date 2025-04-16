package com.example.petoasisbackend.Request.Animal.Cat;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CatChangeBreedRequest {
    @NotBlank(message = "must not be blank")
    private String breedName;
}

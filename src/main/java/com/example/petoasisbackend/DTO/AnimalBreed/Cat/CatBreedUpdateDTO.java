package com.example.petoasisbackend.DTO.AnimalBreed.Cat;

import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatBreedUpdateDTO {
    private Integer breedId;
    private String breedName;

    public static CatBreedUpdateDTO fromCatBreed(CatBreed breed) {
        return new CatBreedUpdateDTO(
                breed.getBreedId(),
                breed.getBreedName()
        );
    }
}

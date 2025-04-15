package com.example.petoasisbackend.DTO.AnimalBreed.Cat;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatBreedMinimumDTO implements ModelDTO<CatBreed> {
    private Integer breedId;

    public static CatBreedMinimumDTO fromCatBreed(CatBreed breed) {
        return new CatBreedMinimumDTO(
                breed.getBreedId()
        );
    }
}

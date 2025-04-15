package com.example.petoasisbackend.DTO.AnimalBreed.Cat;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatBreedNameDTO implements ModelDTO<CatBreed> {
    private String breedName;

    public static CatBreedNameDTO fromCatBreed(CatBreed breed) {
        return new CatBreedNameDTO(
                breed.getBreedName()
        );
    }
}

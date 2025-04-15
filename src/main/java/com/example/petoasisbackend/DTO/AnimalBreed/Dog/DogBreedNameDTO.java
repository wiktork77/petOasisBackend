package com.example.petoasisbackend.DTO.AnimalBreed.Dog;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DogBreedNameDTO implements ModelDTO<DogBreed> {
    private String breedName;

    public static DogBreedNameDTO fromDogBreed(DogBreed breed) {
        return new DogBreedNameDTO(
                breed.getBreedName()
        );
    }
}

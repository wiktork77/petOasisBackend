package com.example.petoasisbackend.DTO.AnimalBreed.Dog;

import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DogBreedUpdateDTO {
    private Integer breedId;
    private String breedName;

    public static DogBreedUpdateDTO fromDogBreed(DogBreed breed) {
        return new DogBreedUpdateDTO(
                breed.getBreedId(),
                breed.getBreedName()
        );
    }
}

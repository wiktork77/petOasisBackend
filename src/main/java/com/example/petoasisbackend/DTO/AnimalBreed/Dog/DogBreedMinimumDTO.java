package com.example.petoasisbackend.DTO.AnimalBreed.Dog;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DogBreedMinimumDTO implements ModelDTO<DogBreed> {
    private Integer breedId;

    public static DogBreedMinimumDTO fromDogBreed(DogBreed breed) {
        return new DogBreedMinimumDTO(
                breed.getBreedId()
        );
    }
}

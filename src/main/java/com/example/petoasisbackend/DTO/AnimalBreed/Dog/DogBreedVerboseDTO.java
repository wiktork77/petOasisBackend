package com.example.petoasisbackend.DTO.AnimalBreed.Dog;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DogBreedVerboseDTO implements ModelDTO<DogBreed> {
    private Integer breedId;
    private String breedName;

    public static DogBreedVerboseDTO fromDogBreed(DogBreed breed) {
        return new DogBreedVerboseDTO(
                breed.getBreedId(),
                breed.getBreedName()
        );
    }
}

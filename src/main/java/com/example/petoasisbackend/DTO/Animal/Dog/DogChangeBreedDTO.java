package com.example.petoasisbackend.DTO.Animal.Dog;

import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedNameDTO;
import com.example.petoasisbackend.Model.Animal.Dog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DogChangeBreedDTO {
    private Long dogId;
    private DogBreedNameDTO dogBreed;

    public static DogChangeBreedDTO fromDog(Dog dog) {
        return new DogChangeBreedDTO(
                dog.getDogId(),
                DogBreedNameDTO.fromDogBreed(dog.getDogBreed())
        );
    }
}

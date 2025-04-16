package com.example.petoasisbackend.DTO.Animal.Dog;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMediumDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedNameDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Model.Descriptor.AnimalBadge;
import com.example.petoasisbackend.Model.Descriptor.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DogMediumDTO implements ModelDTO<Dog> {
    private Long dogId;
    private Boolean isMuzzleRequired;
    private Integer barkingLevel;
    private DogBreedNameDTO dogBreed;

    private AnimalMediumDTO animal;

    public static DogMediumDTO fromDog(Dog dog) {
        return new DogMediumDTO(
                dog.getDogId(),
                dog.getIsMuzzleRequired(),
                dog.getBarkingLevel(),
                DogBreedNameDTO.fromDogBreed(dog.getDogBreed()),
                AnimalMediumDTO.fromAnimal(dog.getAnimal())
        );
    }
}

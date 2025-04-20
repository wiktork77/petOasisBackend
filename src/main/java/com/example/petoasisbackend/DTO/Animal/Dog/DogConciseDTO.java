package com.example.petoasisbackend.DTO.Animal.Dog;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalConciseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Dog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DogConciseDTO implements ModelDTO<Dog> {
    private Long dogId;
    private AnimalConciseDTO animal;

    public static DogConciseDTO fromDog(Dog dog) {
        return new DogConciseDTO(
                dog.getDogId(),
                AnimalConciseDTO.fromAnimal(dog.getAnimal())
        );
    }
}

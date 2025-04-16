package com.example.petoasisbackend.DTO.Animal.Dog;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DogVerboseDTO implements ModelDTO<Dog> {
    private Long dogId;
    private Boolean isMuzzleRequired;
    private Integer barkingLevel;
    private String favoriteToy;
    private DogBreed dogBreed;

    private AnimalVerboseDTO animal;

    public static DogVerboseDTO fromDog(Dog dog) {
        return new DogVerboseDTO(
                dog.getDogId(),
                dog.getIsMuzzleRequired(),
                dog.getBarkingLevel(),
                dog.getFavoriteToy(),
                dog.getDogBreed(),
                AnimalVerboseDTO.fromAnimal(dog.getAnimal())
        );
    }
}

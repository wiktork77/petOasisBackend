package com.example.petoasisbackend.DTO.Animal.Dog;

import com.example.petoasisbackend.Model.Animal.Dog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DogChangeBreedDTO {
    private Long dogId;
    private String breedName;

    public static DogChangeBreedDTO fromDog(Dog dog) {
        return new DogChangeBreedDTO(
                dog.getDogId(),
                dog.getDogBreed().getBreedName()
        );
    }
}

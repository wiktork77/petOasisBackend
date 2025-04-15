package com.example.petoasisbackend.DTO.Animal.Dog;

import com.example.petoasisbackend.Model.Animal.Dog;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DogUpdateDTO {
    private Long dogId;
    private Boolean isMuzzleRequired;
    private Integer barkingLevel;
    private String favoriteToy;


    public static DogUpdateDTO fromDog(Dog dog) {
        return new DogUpdateDTO(
                dog.getDogId(),
                dog.getIsMuzzleRequired(),
                dog.getBarkingLevel(),
                dog.getFavoriteToy()
        );
    }
}

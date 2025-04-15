package com.example.petoasisbackend.DTO.Animal.Dog;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Dog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DogMinimumDTO implements ModelDTO<Dog> {
    private Long dogId;

    public static DogMinimumDTO fromDog(Dog dog) {
        return new DogMinimumDTO(
                dog.getDogId()
        );
    }
}

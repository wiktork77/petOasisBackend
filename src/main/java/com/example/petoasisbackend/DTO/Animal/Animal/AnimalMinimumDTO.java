package com.example.petoasisbackend.DTO.Animal.Animal;

import com.example.petoasisbackend.Model.Animal.Animal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalMinimumDTO {
    private Long animalId;

    public static AnimalMinimumDTO fromAnimal(Animal animal) {
        return new AnimalMinimumDTO(
                animal.getAnimalId()
        );
    }
}

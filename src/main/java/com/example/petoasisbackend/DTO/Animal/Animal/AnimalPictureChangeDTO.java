package com.example.petoasisbackend.DTO.Animal.Animal;

import com.example.petoasisbackend.Model.Animal.Animal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalPictureChangeDTO {
    private Long animalId;
    private String pictureUrl;

    public static AnimalPictureChangeDTO fromAnimal(Animal animal) {
        return new AnimalPictureChangeDTO(
                animal.getAnimalId(),
                animal.getPictureURL()
        );
    }
}

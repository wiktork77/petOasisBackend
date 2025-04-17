package com.example.petoasisbackend.DTO.Animal.Animal;

import com.example.petoasisbackend.DTO.Status.AvailabilityStatus.AvailabilityStatusNameDTO;
import com.example.petoasisbackend.Model.Animal.Animal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalAvailabilityDTO {
    private Long animalId;
    private AvailabilityStatusNameDTO availabilityStatus;

    public static AnimalAvailabilityDTO fromAnimal(Animal animal) {
        return new AnimalAvailabilityDTO(
                animal.getAnimalId(),
                AvailabilityStatusNameDTO.fromAvailabilityStatus(animal.getAvailabilityStatus())
        );
    }
}

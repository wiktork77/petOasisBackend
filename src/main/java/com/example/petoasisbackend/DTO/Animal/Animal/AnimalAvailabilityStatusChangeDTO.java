package com.example.petoasisbackend.DTO.Animal.Animal;

import com.example.petoasisbackend.DTO.Status.AvailabilityStatus.AvailabilityStatusNameDTO;
import com.example.petoasisbackend.DTO.Status.HealthStatus.HealthStatusNameDTO;
import com.example.petoasisbackend.Model.Animal.Animal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalAvailabilityStatusChangeDTO {
    private Long animalId;
    private AvailabilityStatusNameDTO availabilityStatus;

    public static AnimalAvailabilityStatusChangeDTO fromAnimal(Animal animal) {
        return new AnimalAvailabilityStatusChangeDTO(
                animal.getAnimalId(),
                AvailabilityStatusNameDTO.fromAvailabilityStatus(animal.getAvailabilityStatus())
        );
    }
}

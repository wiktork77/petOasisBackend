package com.example.petoasisbackend.DTO.Animal.Animal;

import com.example.petoasisbackend.DTO.Status.HealthStatus.HealthStatusNameDTO;
import com.example.petoasisbackend.Model.Animal.Animal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalHealthStatusChangeDTO {
    private Long animalId;
    private HealthStatusNameDTO healthStatus;

    public static AnimalHealthStatusChangeDTO fromAnimal(Animal animal) {
        return new AnimalHealthStatusChangeDTO(
                animal.getAnimalId(),
                HealthStatusNameDTO.fromHealthStatus(animal.getHealthStatus())
        );
    }
}

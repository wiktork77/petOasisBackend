package com.example.petoasisbackend.DTO.Badge.AnimalBadge;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMinimumDTO;
import com.example.petoasisbackend.DTO.Badge.Badge.BadgeMinimumDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Badge.AnimalBadge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalBadgeMinimumDTO implements ModelDTO<AnimalBadge> {
    private AnimalMinimumDTO animal;
    private BadgeMinimumDTO badge;

    public static AnimalBadgeMinimumDTO fromAnimalBadge(AnimalBadge animalBadge) {
        return new AnimalBadgeMinimumDTO(
                AnimalMinimumDTO.fromAnimal(animalBadge.getAnimal()),
                BadgeMinimumDTO.fromBadge(animalBadge.getBadge())
        );
    }
}

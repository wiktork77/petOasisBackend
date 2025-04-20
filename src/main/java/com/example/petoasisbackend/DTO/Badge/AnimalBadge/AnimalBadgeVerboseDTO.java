package com.example.petoasisbackend.DTO.Badge.AnimalBadge;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMinimumDTO;
import com.example.petoasisbackend.DTO.Badge.Badge.BadgeVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Badge.AnimalBadge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalBadgeVerboseDTO implements ModelDTO<AnimalBadge> {
    private AnimalMinimumDTO animal;
    private BadgeVerboseDTO badge;

    public static AnimalBadgeVerboseDTO fromAnimalBadge(AnimalBadge animalBadge) {
        return new AnimalBadgeVerboseDTO(
                AnimalMinimumDTO.fromAnimal(animalBadge.getAnimal()),
                BadgeVerboseDTO.fromBadge(animalBadge.getBadge())
        );
    }
}

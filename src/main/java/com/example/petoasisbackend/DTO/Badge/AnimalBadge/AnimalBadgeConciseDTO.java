package com.example.petoasisbackend.DTO.Badge.AnimalBadge;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMinimumDTO;
import com.example.petoasisbackend.DTO.Badge.Badge.BadgeNameDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Badge.AnimalBadge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalBadgeConciseDTO implements ModelDTO<AnimalBadge> {
    private AnimalMinimumDTO animal;
    private BadgeNameDTO badge;

    public static AnimalBadgeConciseDTO fromAnimalBadge(AnimalBadge animalBadge) {
        return new AnimalBadgeConciseDTO(
                AnimalMinimumDTO.fromAnimal(animalBadge.getAnimal()),
                BadgeNameDTO.fromBadge(animalBadge.getBadge())
        );
    }
}

package com.example.petoasisbackend.DTO.Animal.Cat;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMediumDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Cat.CatBreedNameDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedNameDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.Descriptor.AnimalBadge;
import com.example.petoasisbackend.Model.Descriptor.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CatMediumDTO implements ModelDTO<Cat> {
    private Long catId;
    private Boolean isDeclawed;
    private Integer vocalizationLevel;
    private CatBreedNameDTO catBreed;

    private AnimalMediumDTO animal;
    public static CatMediumDTO fromCat(Cat cat) {
        return new CatMediumDTO(
                cat.getCatId(),
                cat.getIsDeclawed(),
                cat.getVocalizationLevel(),
                CatBreedNameDTO.fromCatBreed(cat.getCatBreed()),
                AnimalMediumDTO.fromAnimal(cat.getAnimal())
        );
    }
}

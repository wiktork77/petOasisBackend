package com.example.petoasisbackend.DTO.Animal.Cat;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalVerboseDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Cat.CatBreedVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CatVerboseDTO implements ModelDTO<Cat> {
    private Long catId;
    private Boolean isDeclawed;
    private Integer vocalizationLevel;
    private String favoriteTreat;
    private CatBreedVerboseDTO catBreed;

    private AnimalVerboseDTO animal;

    public static CatVerboseDTO fromCat(Cat cat) {
        return new CatVerboseDTO(
                cat.getCatId(),
                cat.getIsDeclawed(),
                cat.getVocalizationLevel(),
                cat.getFavoriteTreat(),
                CatBreedVerboseDTO.fromCatBreed(cat.getCatBreed()),
                AnimalVerboseDTO.fromAnimal(cat.getAnimal())
        );
    }
}

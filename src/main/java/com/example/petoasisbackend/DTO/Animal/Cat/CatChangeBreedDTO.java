package com.example.petoasisbackend.DTO.Animal.Cat;

import com.example.petoasisbackend.Model.Animal.Cat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatChangeBreedDTO {
    private Long catId;
    private String breedName;

    public static CatChangeBreedDTO fromCat(Cat cat) {
        return new CatChangeBreedDTO(
                cat.getCatId(),
                cat.getCatBreed().getBreedName()
        );
    }
}

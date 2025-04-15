package com.example.petoasisbackend.DTO.Animal.Cat;


import com.example.petoasisbackend.Model.Animal.Cat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatUpdateDTO {
    private Long catId;
    private Boolean isDeclawed;
    private Integer vocalizationLevel;
    private String favoriteTreat;

    public static CatUpdateDTO fromCat(Cat cat) {
        return new CatUpdateDTO(
                cat.getCatId(),
                cat.getIsDeclawed(),
                cat.getVocalizationLevel(),
                cat.getFavoriteTreat()
        );
    }
}

package com.example.petoasisbackend.DTO.Animal.Cat;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Cat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatMinimumDTO implements ModelDTO<Cat> {
    private Long catId;

    public static CatMinimumDTO fromCat(Cat cat) {
        return new CatMinimumDTO(
                cat.getCatId()
        );
    }
}

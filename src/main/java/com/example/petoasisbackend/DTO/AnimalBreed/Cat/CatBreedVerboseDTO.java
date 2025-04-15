package com.example.petoasisbackend.DTO.AnimalBreed.Cat;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatBreedVerboseDTO implements ModelDTO<CatBreed> {
    private Integer breedId;
    private String breedName;

    public static CatBreedVerboseDTO fromCatBreed(CatBreed breed) {
        return new CatBreedVerboseDTO(
                breed.getBreedId(),
                breed.getBreedName()
        );
    }
}

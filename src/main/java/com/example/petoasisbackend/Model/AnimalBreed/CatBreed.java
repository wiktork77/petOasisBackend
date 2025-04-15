package com.example.petoasisbackend.Model.AnimalBreed;


import com.example.petoasisbackend.Request.AnimalBreed.Cat.CatBreedAddRequest;
import com.example.petoasisbackend.Request.AnimalBreed.Cat.CatBreedUpdateRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CatBreed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer breedId;

    @Column(length = 48, unique = true, nullable = false)
    private String breedName;

    public CatBreed(String breedName) {
        this.breedName = breedName;
    }

    public static CatBreed fromCatBreedAddRequest(CatBreedAddRequest request) {
        return new CatBreed(
                request.getBreedName()
        );
    }

    public void update(CatBreedUpdateRequest request) {
        this.breedName = request.getBreedName();
    }
}

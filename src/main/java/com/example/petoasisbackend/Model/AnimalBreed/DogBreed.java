package com.example.petoasisbackend.Model.AnimalBreed;


import com.example.petoasisbackend.Request.AnimalBreed.Dog.DogBreedAddRequest;
import com.example.petoasisbackend.Request.AnimalBreed.Dog.DogBreedUpdateRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DogBreed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer breedId;

    @Column(length = 48, unique = true, nullable = false)
    private String breedName;

    public DogBreed(String breedName) {
        this.breedName = breedName;
    }

    public static DogBreed fromDogBreedAddRequest(DogBreedAddRequest request) {
        return new DogBreed(
                request.getBreedName()
        );
    }

    public void update(DogBreedUpdateRequest request) {
        this.breedName = request.getBreedName();
    }
}

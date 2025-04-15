package com.example.petoasisbackend.Model.Animal;


import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Request.Animal.Dog.DogAddRequest;
import com.example.petoasisbackend.Request.Animal.Dog.DogUpdateRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dogId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column(nullable = false)
    private Boolean isMuzzleRequired;

    @Column(nullable = false)
    private Integer barkingLevel;

    @Column(length = 48)
    private String favoriteToy;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private DogBreed dogBreed;

    public Dog(Animal animal, Boolean isMuzzleRequired, Integer barkingLevel, String favoriteToy, DogBreed dogBreed) {
        this.animal = animal;
        this.isMuzzleRequired = isMuzzleRequired;
        this.barkingLevel = barkingLevel;
        this.favoriteToy = favoriteToy;
        this.dogBreed = dogBreed;
    }

    public void update(DogUpdateRequest request) {
        this.isMuzzleRequired = request.getIsMuzzleRequired();
        this.barkingLevel = request.getBarkingLevel();
        this.favoriteToy = request.getFavoriteToy();
    }

    public static Dog fromDogAddRequest(DogAddRequest request) {
        return new Dog(
                null,
                request.getIsMuzzleRequired(),
                request.getBarkingLevel(),
                request.getFavoriteToy(),
                null
        );
    }
}

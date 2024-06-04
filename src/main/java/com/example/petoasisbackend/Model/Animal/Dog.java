package com.example.petoasisbackend.Model.Animal;


import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import jakarta.persistence.*;

@Entity
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dogId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column(nullable = false)
    private boolean isMuzzleRequired;
    @Column(nullable = false)
    private Byte barkingLevel;

    @Column(length = 48)
    private String favoriteToy;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private DogBreed dogBreed;
}

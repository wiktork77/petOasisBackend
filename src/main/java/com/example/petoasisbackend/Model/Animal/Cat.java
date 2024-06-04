package com.example.petoasisbackend.Model.Animal;


import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import jakarta.persistence.*;

@Entity
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    private Boolean isDeclawed;

    private Byte vocalizationLevel;

    @Column(length = 48, nullable = true)
    private String favoriteTreat;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private CatBreed catBreed;

}

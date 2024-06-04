package com.example.petoasisbackend.Model.Animal;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long animalId;

    @Column(length = 48)
    private String name;

    private LocalDate birthDate;

    private Float weight;

    private Float height;

    private Float length;

    private Byte age;

    private Boolean isNeutered;

    @Column(length = 1024)
    private String description;

    private Character gender;

    private Boolean enjoysPetting;

    private Float rating;

    @Column(length = 128)
    private String pictureURL;


    public Animal(Long animalId, String name, LocalDate birthDate, Float weight, Float height, Float length, Byte age, Boolean isNeutered, String description, Character gender, Boolean enjoysPetting, Float rating, String pictureURL) {
        this.animalId = animalId;
        this.name = name;
        this.birthDate = birthDate;
        this.weight = weight;
        this.height = height;
        this.length = length;
        this.age = age;
        this.isNeutered = isNeutered;
        this.description = description;
        this.gender = gender;
        this.enjoysPetting = enjoysPetting;
        this.rating = rating;
        this.pictureURL = pictureURL;
    }

    public Animal() {}
}

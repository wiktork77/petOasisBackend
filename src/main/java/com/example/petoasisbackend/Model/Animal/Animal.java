package com.example.petoasisbackend.Model.Animal;


import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long animalId;

    @Column(length = 48, nullable = false)
    private String name;

    private LocalDate birthDate;

    @Column(nullable = false)
    private Float weight;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Float length;

    private Byte age;

    private Boolean isNeutered;

    @Column(length = 1024, nullable = false)
    private String description;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Boolean enjoysPetting;

    private Float rating;

    @Column(length = 128)
    private String pictureURL;

    @ManyToOne
    @JoinColumn(name = "health_id")
    private HealthStatus healthStatus;

    @ManyToOne
    @JoinColumn(name = "availability_id")
    private AvailabilityStatus availabilityStatus;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(name, animal.name) && Objects.equals(birthDate, animal.birthDate) && Objects.equals(weight, animal.weight) && Objects.equals(height, animal.height) && Objects.equals(length, animal.length) && Objects.equals(age, animal.age) && Objects.equals(isNeutered, animal.isNeutered) && Objects.equals(description, animal.description) && Objects.equals(gender, animal.gender) && Objects.equals(enjoysPetting, animal.enjoysPetting) && Objects.equals(rating, animal.rating) && Objects.equals(pictureURL, animal.pictureURL) && Objects.equals(healthStatus, animal.healthStatus) && Objects.equals(availabilityStatus, animal.availabilityStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, weight, height, length, age, isNeutered, description, gender, enjoysPetting, rating, pictureURL, healthStatus, availabilityStatus);
    }

    public void inheritFromOtherAnimal(Animal other) {
        if (other.name != null) {
            this.name = other.name;
        }
        this.birthDate = other.birthDate;
        if (other.weight != null) {
            this.weight = other.weight;
        }
        if (other.height != null) {
            this.height = other.height;
        }
        if (other.length != null) {
            this.length = other.length;
        }
        this.age = other.age;
        this.isNeutered = other.isNeutered;
        if (other.description != null) {
            this.description = other.description;
        }
        if (other.gender != null) {
            this.gender = other.gender;
        }
        if (other.enjoysPetting != null) {
            this.enjoysPetting = other.enjoysPetting;
        }
        this.rating = other.rating;
        this.pictureURL = other.pictureURL;
        if (other.healthStatus != null) {
            this.healthStatus = other.healthStatus;
        }
        if (other.availabilityStatus != null) {
            this.availabilityStatus = other.availabilityStatus;
        }
    }
}

package com.example.petoasisbackend.Model.Animal;


import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import com.example.petoasisbackend.Model.Descriptor.AnimalBadge;
import com.example.petoasisbackend.Model.Descriptor.AnimalComment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @Column(length = 1, nullable = false)
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


    @OneToMany(mappedBy = "animal", cascade = CascadeType.REMOVE)
    private Set<AnimalBadge> animalBadges;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.REMOVE)
    private Set<AnimalComment> animalComment;


    private String type;

    private Long parentId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(animalId, animal.animalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId);
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

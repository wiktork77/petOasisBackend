package com.example.petoasisbackend.Model.Animal;


import com.example.petoasisbackend.Model.Descriptor.Gender;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import com.example.petoasisbackend.Model.Descriptor.AnimalBadge;
import com.example.petoasisbackend.Model.Descriptor.AnimalComment;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Request.Animal.Dog.DogAddRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
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

    private Integer age;

    private Boolean isNeutered;

    @Column(length = 1024, nullable = false)
    private String description;

    @Column(length = 1, nullable = false)
    private Gender gender;

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

    private AnimalType type;

    private Long parentId;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter home;


    public Animal(String name, LocalDate birthDate, Float weight, Float height, Float length, Integer age, Boolean isNeutered, String description, Gender gender, Boolean enjoysPetting, Float rating, String pictureURL, HealthStatus healthStatus, AvailabilityStatus availabilityStatus, Set<AnimalBadge> animalBadges, Set<AnimalComment> animalComment, AnimalType type, Long parentId, Shelter home) {
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
        this.healthStatus = healthStatus;
        this.availabilityStatus = availabilityStatus;
        this.animalBadges = animalBadges;
        this.animalComment = animalComment;
        this.type = type;
        this.parentId = parentId;
        this.home = home;
    }

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

    public static Animal fromDogAddRequest(DogAddRequest request) {
        return new Animal(
                request.getName(),
                request.getBirthDate(),
                request.getWeight(),
                request.getHeight(),
                request.getLength(),
                Period.between(request.getBirthDate(), LocalDate.now()).getYears(),
                request.getIsNeutered(),
                request.getDescription(),
                request.getGender(),
                request.getEnjoysPetting(),
                null,
                request.getPictureUrl(),
                request.getHealthStatus(),
                request.getAvailabilityStatus(),
                Collections.emptySet(),
                Collections.emptySet(),
                AnimalType.DOG,
                null,
                null
        );
    }
}

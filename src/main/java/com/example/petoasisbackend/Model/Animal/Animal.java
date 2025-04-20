package com.example.petoasisbackend.Model.Animal;


import com.example.petoasisbackend.Model.Comment.AnimalComment;
import com.example.petoasisbackend.Model.Enum.AnimalType;
import com.example.petoasisbackend.Model.Enum.Gender;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import com.example.petoasisbackend.Model.Badge.AnimalBadge;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Request.Animal.AnimalAddRequest;
import com.example.petoasisbackend.Request.Animal.AnimalUpdateRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collections;
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

    @Column(nullable = false)
    private Float weight;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Float length;

    private LocalDate dateOfBirth;

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
    @JsonIgnoreProperties("animals")
    private Shelter home;


    public Animal(String name, Float weight, Float height, Float length, LocalDate dateOfBirth, Boolean isNeutered, String description, Gender gender, Boolean enjoysPetting, Float rating, String pictureURL, HealthStatus healthStatus, AvailabilityStatus availabilityStatus, Set<AnimalBadge> animalBadges, Set<AnimalComment> animalComment, AnimalType type, Long parentId, Shelter home) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.length = length;
        this.dateOfBirth = dateOfBirth;
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

    public void update(AnimalUpdateRequest request) {
        this.name = request.getName();
        this.weight = request.getWeight();
        this.height = request.getHeight();
        this.length = request.getLength();
        this.dateOfBirth = request.getDateOfBirth();
        this.isNeutered = request.getIsNeutered();
        this.gender = request.getGender();
        this.enjoysPetting = request.getEnjoysPetting();
        this.description = request.getDescription();
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

    public static Animal fromAnimalAddRequest(AnimalAddRequest request) {
        return new Animal(
                request.getName(),
                request.getWeight(),
                request.getHeight(),
                request.getLength(),
                request.getDateOfBirth(),
                request.getIsNeutered(),
                request.getDescription(),
                request.getGender(),
                request.getEnjoysPetting(),
                null,
                request.getPictureUrl(),
                null,
                null,
                Collections.emptySet(),
                Collections.emptySet(),
                null,
                null,
                null
        );
    }
}

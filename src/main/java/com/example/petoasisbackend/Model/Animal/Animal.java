package com.example.petoasisbackend.Model.Animal;


import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
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


    @ManyToOne
    @JoinColumn(name = "health_id")
    private HealthStatus healthStatus;

    @ManyToOne
    @JoinColumn(name = "availability_id")
    private AvailabilityStatus availabilityStatus;

    public Animal() {}
}

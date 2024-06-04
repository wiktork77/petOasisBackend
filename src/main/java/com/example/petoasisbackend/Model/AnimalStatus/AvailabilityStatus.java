package com.example.petoasisbackend.Model.AnimalStatus;


import jakarta.persistence.*;

@Entity
public class AvailabilityStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer availabilityId;

    @Column(length = 64, unique = true)
    private String availability;
}

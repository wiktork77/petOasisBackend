package com.example.petoasisbackend.Model.AnimalStatus;


import jakarta.persistence.*;

@Entity
public class HealthStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer healthId;

    @Column(length = 64, unique = true)
    private String healthStatus;
}

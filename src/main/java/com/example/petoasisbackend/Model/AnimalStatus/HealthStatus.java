package com.example.petoasisbackend.Model.AnimalStatus;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
public class HealthStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer healthId;

    @Column(length = 64, unique = true, nullable = false)
    private String healthStatus;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthStatus that = (HealthStatus) o;
        return Objects.equals(healthStatus, that.healthStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(healthStatus);
    }
}

package com.example.petoasisbackend.Model.AnimalStatus;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class AvailabilityStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer availabilityId;

    @Column(length = 64, unique = true, nullable = false)
    private String availability;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailabilityStatus that = (AvailabilityStatus) o;
        return Objects.equals(availability, that.availability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(availability);
    }

    public AvailabilityStatus(String availability) {
        this.availability = availability;
    }
}

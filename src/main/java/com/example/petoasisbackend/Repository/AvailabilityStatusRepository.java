package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityStatusRepository extends JpaRepository<AvailabilityStatus, Integer> {
}

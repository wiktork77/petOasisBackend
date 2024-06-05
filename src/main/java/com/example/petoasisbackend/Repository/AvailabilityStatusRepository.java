package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityStatusRepository extends JpaRepository<AvailabilityStatus, Integer> {
    boolean existsByAvailability(String availability);
    AvailabilityStatus findByAvailability(String availability);
}

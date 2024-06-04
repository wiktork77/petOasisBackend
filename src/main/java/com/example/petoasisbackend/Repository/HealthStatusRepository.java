package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthStatusRepository extends JpaRepository<HealthStatus, Integer> {
}

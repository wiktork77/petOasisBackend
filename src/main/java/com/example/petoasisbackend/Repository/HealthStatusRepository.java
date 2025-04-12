package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.Status.HealthStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthStatusRepository extends JpaRepository<HealthStatus, Integer> {
    boolean existsByHealthStatus(String healthStatus);
    HealthStatus findByHealthStatus(String healthStatus);
}

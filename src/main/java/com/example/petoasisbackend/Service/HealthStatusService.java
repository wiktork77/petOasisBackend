package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import com.example.petoasisbackend.Repository.HealthStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthStatusService {

    @Autowired
    private HealthStatusRepository healthStatusRepository;

    public List<HealthStatus> getHealthStatuses() {
        return healthStatusRepository.findAll();
    }

    public HealthStatus addHealthStatus(HealthStatus status) {
        if (healthStatusRepository.existsByHealthStatus(status.getHealthStatus())) {
            throw new IllegalArgumentException("Health status must be unique");
        }
        healthStatusRepository.save(status);
        return status;
    }

    public HealthStatus removeHealthStatus(String statusName) {
        if (!healthStatusRepository.existsByHealthStatus(statusName)) {
            throw new IllegalArgumentException("Health status doesnt exist");
        }
        HealthStatus status = healthStatusRepository.findByHealthStatus(statusName);
        healthStatusRepository.deleteById(status.getHealthId());
        return status;
    }

    public HealthStatus updateAvailabilityStatus(String name, String newName) {
        if (!healthStatusRepository.existsByHealthStatus(name)) {
            throw new IllegalArgumentException("Health status doesnt exist");
        }
        if (healthStatusRepository.existsByHealthStatus(newName)) {
            throw new IllegalArgumentException("New health status name already exists in the database");
        }
        HealthStatus status = healthStatusRepository.findByHealthStatus(name);
        status.setHealthStatus(newName);
        healthStatusRepository.save(status);
        return status;
    }
}

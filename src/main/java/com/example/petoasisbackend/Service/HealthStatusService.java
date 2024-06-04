package com.example.petoasisbackend.Service;


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
        healthStatusRepository.save(status);
        return status;
    }

    public HealthStatus removeHealthStatus(HealthStatus status) {
        healthStatusRepository.delete(status);
        return status;
    }
}

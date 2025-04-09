package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.HealthStatus.HealthStatusMinimumDTO;
import com.example.petoasisbackend.DTO.HealthStatus.HealthStatusNameDTO;
import com.example.petoasisbackend.DataInitializers.HealthStatusInitializer;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusAlreadyExistsException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusCannotBeModifiedException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusDoesntExistException;
import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import com.example.petoasisbackend.Repository.HealthStatusRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HealthStatusService {

    @Autowired
    private HealthStatusRepository healthStatusRepository;

    public Object getHealthStatuses(DataDetailLevel level) {
        List<HealthStatus> statuses = healthStatusRepository.findAll();
        switch (level) {
            case VERBOSE -> {return statuses;}
            case MINIMUM -> {
                return statuses.stream().map(HealthStatusMinimumDTO::fromHealthStatus).collect(Collectors.toList());
            }
            default -> {
                return statuses.stream().map(HealthStatusNameDTO::fromHealthStatus).collect(Collectors.toList());
            }
        }
    }

    public HealthStatus addHealthStatus(HealthStatus status) throws HealthStatusAlreadyExistsException {
        if (healthStatusRepository.existsByHealthStatus(status.getHealthStatus())) {
            throw new HealthStatusAlreadyExistsException("Cannot add health status '" + status.getHealthStatus() + "' because it already exists");
        }
        healthStatusRepository.save(status);
        return status;
    }

    public HealthStatus removeHealthStatus(Integer id) throws HealthStatusDoesntExistException, HealthStatusCannotBeModifiedException {
        if (!healthStatusRepository.existsById(id)) {
            throw new HealthStatusDoesntExistException(
                    "Cannot delete health status with id '" + id + "' because it doesn't exist"
            );
        }

        HealthStatus status = healthStatusRepository.getReferenceById(id);

        if (HealthStatusInitializer.coreStatuses.contains(status.getHealthStatus())) {
            throw new HealthStatusCannotBeModifiedException(
                    "Cannot modify core status"
            );
        }

        healthStatusRepository.delete(status);
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

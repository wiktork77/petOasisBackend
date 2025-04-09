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

    public Object getHealthStatusById(Integer id, DataDetailLevel level) throws HealthStatusDoesntExistException {
        if (!healthStatusRepository.existsById(id)) {
            throw new HealthStatusDoesntExistException(
                    "Cannot get health status with id '" + id + "' because it doesn't exist"
            );
        }
        HealthStatus status = healthStatusRepository.findById(id).get();
        switch (level) {
            case VERBOSE -> {return status;}
            case MINIMUM -> {
                return HealthStatusMinimumDTO.fromHealthStatus(status);
            }
            default -> {
                return HealthStatusNameDTO.fromHealthStatus(status);
            }
        }
    }

    public HealthStatus getHealthStatusByName(String string) throws HealthStatusDoesntExistException {
        if (!healthStatusRepository.existsByHealthStatus(string)) {
            throw new HealthStatusDoesntExistException("Cannot get status with name '" + string + "' because it doesn't exist");
        }
        HealthStatus status = healthStatusRepository.findByHealthStatus(string);
        return status;
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
                    "Cannot delete core status"
            );
        }

        healthStatusRepository.delete(status);
        return status;
    }

    public HealthStatus updateHealthStatus(Integer id, HealthStatusNameDTO statusNameDTO) throws HealthStatusDoesntExistException, HealthStatusAlreadyExistsException {
        if (!healthStatusRepository.existsById(id)) {
            throw new HealthStatusDoesntExistException("Cannot update health status with id '" + "' because it doesn't exist");
        }
        if (healthStatusRepository.existsByHealthStatus(statusNameDTO.getHealthStatus())) {
            throw new HealthStatusAlreadyExistsException("Cannot update health status with id '" + "' to '" + statusNameDTO.getHealthStatus() + "' because '" + statusNameDTO.getHealthStatus() + "' already exists");
        }

        HealthStatus status = healthStatusRepository.findById(id).get();
        status.setHealthStatus(statusNameDTO.getHealthStatus());
        healthStatusRepository.save(status);
        return status;
    }
}

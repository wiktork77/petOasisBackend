package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.Descriptor.HealthStatus.HealthStatusMinimumDTO;
import com.example.petoasisbackend.DTO.Descriptor.HealthStatus.HealthStatusNameDTO;
import com.example.petoasisbackend.DataInitializers.HealthStatusInitializer;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusAlreadyExistsException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusCannotBeModifiedException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusDoesntExistException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusInvalidRequestException;
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
        return dataDetailTransformShelter(status, level);
    }

    public HealthStatus getHealthStatusByName(String string) throws HealthStatusDoesntExistException {
        if (!healthStatusRepository.existsByHealthStatus(string)) {
            throw new HealthStatusDoesntExistException("Cannot get status with name '" + string + "' because it doesn't exist");
        }
        HealthStatus status = healthStatusRepository.findByHealthStatus(string);
        return status;
    }

    public HealthStatus addHealthStatus(HealthStatusNameDTO status) throws HealthStatusAlreadyExistsException, HealthStatusInvalidRequestException {
        if (healthStatusRepository.existsByHealthStatus(status.getHealthStatus())) {
            throw new HealthStatusAlreadyExistsException("Cannot add health status '" + status.getHealthStatus() + "' because it already exists");
        }
        if (status.getHealthStatus() == null || status.getHealthStatus().trim().isEmpty()) {
            throw new HealthStatusInvalidRequestException("Cannot add health status because the request is not valid");
        }
        HealthStatus newStatus = new HealthStatus(status.getHealthStatus());
        healthStatusRepository.save(newStatus);
        return newStatus;
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

    public HealthStatus updateHealthStatus(Integer id, HealthStatusNameDTO statusNameDTO) throws HealthStatusDoesntExistException, HealthStatusAlreadyExistsException, HealthStatusInvalidRequestException {
        if (!healthStatusRepository.existsById(id)) {
            throw new HealthStatusDoesntExistException("Cannot update health status with id '" + id + "' because it doesn't exist");
        }
        if (healthStatusRepository.existsByHealthStatus(statusNameDTO.getHealthStatus())) {
            throw new HealthStatusAlreadyExistsException("Cannot update health status with id '" + id + "' to '" + statusNameDTO.getHealthStatus() + "' because '" + statusNameDTO.getHealthStatus() + "' already exists");
        }
        if (statusNameDTO.getHealthStatus() == null || statusNameDTO.getHealthStatus().trim().isEmpty()) {
            throw new HealthStatusInvalidRequestException("Cannot update health status with id '" + id + "' because the request is invalid");
        }

        HealthStatus status = healthStatusRepository.findById(id).get();
        status.setHealthStatus(statusNameDTO.getHealthStatus());
        healthStatusRepository.save(status);
        return status;
    }

    private Object dataDetailTransformShelter(HealthStatus status, DataDetailLevel level) {
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
}

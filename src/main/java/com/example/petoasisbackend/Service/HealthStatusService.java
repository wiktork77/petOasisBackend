package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.Descriptor.HealthStatus.HealthStatusMinimumDTO;
import com.example.petoasisbackend.DTO.Descriptor.HealthStatus.HealthStatusNameDTO;
import com.example.petoasisbackend.DTO.Descriptor.HealthStatus.HealthStatusVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DataInitializer.HealthStatusInitializer;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusCannotBeModifiedException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusAlreadyExistsException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusCannotBeModifiedException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusDoesntExistException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusInvalidRequestException;
import com.example.petoasisbackend.Mapper.HealthStatusMapper;
import com.example.petoasisbackend.Model.Status.HealthStatus;
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
    @Autowired
    private HealthStatusMapper healthStatusMapper;


    public List<ModelDTO<HealthStatus>> getHealthStatuses(DataDetailLevel level) {
        List<HealthStatus> statuses = healthStatusRepository.findAll();
        var mapper = healthStatusMapper.getMapper(level);

        return statuses.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<HealthStatus> getHealthStatusById(Integer id, DataDetailLevel level) throws HealthStatusDoesntExistException {
        if (!healthStatusRepository.existsById(id)) {
            throw new HealthStatusDoesntExistException(
                    "Cannot get health status with id '" + id + "' because it doesn't exist"
            );
        }

        HealthStatus status = healthStatusRepository.findById(id).get();
        var mapper = healthStatusMapper.getMapper(level);

        return mapper.apply(status);
    }

    public HealthStatusVerboseDTO getHealthStatusByName(String string) throws HealthStatusDoesntExistException {
        if (!healthStatusRepository.existsByHealthStatus(string)) {
            throw new HealthStatusDoesntExistException("Cannot get status with name '" + string + "' because it doesn't exist");
        }

        HealthStatus status = healthStatusRepository.findByHealthStatus(string);
        return HealthStatusVerboseDTO.fromHealthStatus(status);
    }

    public HealthStatus getHealthStatusReferenceByName(String name) throws HealthStatusDoesntExistException {
        if (!healthStatusRepository.existsByHealthStatus(name)) {
            throw new HealthStatusDoesntExistException("Cannot get status with name '" + name + "' because it doesn't exist");
        }

        HealthStatus status = healthStatusRepository.findByHealthStatus(name);
        return status;
    }


    public HealthStatusMinimumDTO addHealthStatus(HealthStatusNameDTO status) throws HealthStatusAlreadyExistsException, HealthStatusInvalidRequestException {
        if (healthStatusRepository.existsByHealthStatus(status.getHealthStatus())) {
            throw new HealthStatusAlreadyExistsException("Cannot add health status '" + status.getHealthStatus() + "' because it already exists");
        }

        if (status.getHealthStatus() == null || status.getHealthStatus().trim().isEmpty()) {
            throw new HealthStatusInvalidRequestException("Cannot add health status because the request is not valid");
        }

        HealthStatus newStatus = new HealthStatus(status.getHealthStatus());
        HealthStatus savedStatus = healthStatusRepository.save(newStatus);

        return HealthStatusMinimumDTO.fromHealthStatus(savedStatus);
    }

    public void removeHealthStatus(Integer id) throws HealthStatusDoesntExistException, HealthStatusCannotBeModifiedException {
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
    }

    public HealthStatusVerboseDTO updateHealthStatus(Integer id, HealthStatusNameDTO statusNameDTO) throws HealthStatusDoesntExistException, HealthStatusAlreadyExistsException, HealthStatusInvalidRequestException, AvailabilityStatusCannotBeModifiedException {
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

        if (HealthStatusInitializer.coreStatuses.contains(status.getHealthStatus())) {
            throw new AvailabilityStatusCannotBeModifiedException(
                    "Cannot modify core status"
            );
        }

        status.setHealthStatus(statusNameDTO.getHealthStatus());
        healthStatusRepository.save(status);

        return HealthStatusVerboseDTO.fromHealthStatus(status);
    }
}

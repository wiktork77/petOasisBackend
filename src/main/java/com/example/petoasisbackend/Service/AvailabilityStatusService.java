package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.AvailabilityStatus.AvailabilityStatusMinimumDTO;
import com.example.petoasisbackend.DTO.AvailabilityStatus.AvailabilityStatusNameDTO;
import com.example.petoasisbackend.DTO.HealthStatus.HealthStatusMinimumDTO;
import com.example.petoasisbackend.DTO.HealthStatus.HealthStatusNameDTO;
import com.example.petoasisbackend.DataInitializers.AvailabilityStatusInitializer;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusAlreadyExistsException;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusCannotBeModifiedException;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusDoesntExistException;
import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import com.example.petoasisbackend.Repository.AvailabilityStatusRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityStatusService {
    @Autowired
    private AvailabilityStatusRepository availabilityStatusRepository;

    public Object getAvailabilityStatuses(DataDetailLevel level) {
        List<AvailabilityStatus> statuses = availabilityStatusRepository.findAll();

        switch (level) {
            case VERBOSE -> {
                return statuses;
            }
            case MINIMUM -> {
                return statuses.stream().map(AvailabilityStatusMinimumDTO::fromAvailabilityStatus).collect(Collectors.toList());
            }
            default -> {
                return statuses.stream().map(AvailabilityStatusNameDTO::fromAvailabilityStatus).collect(Collectors.toList());
            }
        }
    }

    public Object getAvailabilityStatusById(Integer id, DataDetailLevel level) throws AvailabilityStatusDoesntExistException {
        if (!availabilityStatusRepository.existsById(id)) {
            throw new AvailabilityStatusDoesntExistException(
                    "Cannot get availability status with id '" + id + "' because it doesn't exist"
            );
        }

        AvailabilityStatus status = availabilityStatusRepository.findById(id).get();

        switch (level) {
            case VERBOSE -> {
                return status;
            }
            case MINIMUM -> {
                return AvailabilityStatusMinimumDTO.fromAvailabilityStatus(status);
            }
            default -> {
                return AvailabilityStatusNameDTO.fromAvailabilityStatus(status);
            }
        }
    }

    public AvailabilityStatus getAvailabilityStatusByName(String name) throws AvailabilityStatusDoesntExistException {
        if (!availabilityStatusRepository.existsByAvailability(name)) {
            throw new AvailabilityStatusDoesntExistException(
                    "Cannot get availability status with name '" + name + "' because it doesn't exist"
            );
        }
        return availabilityStatusRepository.findByAvailability(name);
    }

    public AvailabilityStatusMinimumDTO addAvailabilityStatus(AvailabilityStatusNameDTO nameDTO) throws AvailabilityStatusAlreadyExistsException {
        if (availabilityStatusRepository.existsByAvailability(nameDTO.getAvailability())) {
            throw new AvailabilityStatusAlreadyExistsException(
                    "Cannot add availability status with name '" + nameDTO.getAvailability() + "' because it already exists"
            );
        }
        AvailabilityStatus status = new AvailabilityStatus(nameDTO.getAvailability());
        availabilityStatusRepository.save(status);
        return AvailabilityStatusMinimumDTO.fromAvailabilityStatus(status);
    }

    public AvailabilityStatus removeAvailabilityStatus(Integer id) throws AvailabilityStatusDoesntExistException, AvailabilityStatusCannotBeModifiedException {
        if (!availabilityStatusRepository.existsById(id)) {
            throw new AvailabilityStatusDoesntExistException(
                    "Cannot delete availability status with id '" + id + "' because it doesnt exist"
            );
        }
        AvailabilityStatus status = availabilityStatusRepository.findById(id).get();

        if (AvailabilityStatusInitializer.coreStatuses.contains(status.getAvailability())) {
            throw new AvailabilityStatusCannotBeModifiedException(
                    "Cannot delete core status"
            );
        }

        availabilityStatusRepository.delete(status);

        return status;
    }

    public AvailabilityStatus updateAvailabilityStatus(Integer id, AvailabilityStatusNameDTO statusNameDTO) throws AvailabilityStatusDoesntExistException, AvailabilityStatusAlreadyExistsException, AvailabilityStatusCannotBeModifiedException {
        if (!availabilityStatusRepository.existsById(id)) {
            throw new AvailabilityStatusDoesntExistException(
                    "Cannot update availability status with id '" + id + "' because it doesn't exist"
            );
        }
        if (availabilityStatusRepository.existsByAvailability(statusNameDTO.getAvailability())) {
            throw new AvailabilityStatusAlreadyExistsException(
                    "Cannot update availability status with id '" + id + "' to '"
                            + statusNameDTO.getAvailability() + "' because '" + statusNameDTO.getAvailability()
                            +  "' already exists"
            );
        }
        AvailabilityStatus status = availabilityStatusRepository.findById(id).get();

        if (AvailabilityStatusInitializer.coreStatuses.contains(status.getAvailability())) {
            throw new AvailabilityStatusCannotBeModifiedException("Cannot modify core status");
        }

        status.setAvailability(statusNameDTO.getAvailability());

        availabilityStatusRepository.save(status);

        return status;
    }
}


//    public AvailabilityStatus removeAvailabilityStatus(Integer id) {
//        if (!availabilityStatusRepository.existsById(id)) {
//            throw new IllegalArgumentException("Availability status doesnt exist");
//        }
//        AvailabilityStatus status = availabilityStatusRepository.getReferenceById(id);
//        availabilityStatusRepository.deleteById(id);
//        return status;
//    }
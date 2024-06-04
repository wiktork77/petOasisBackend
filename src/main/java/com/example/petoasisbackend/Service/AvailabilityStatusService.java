package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import com.example.petoasisbackend.Repository.AvailabilityStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityStatusService {
    @Autowired
    private AvailabilityStatusRepository availabilityStatusRepository;

    public List<AvailabilityStatus> getAvailabilityStatuses() {
        return availabilityStatusRepository.findAll();
    }

    public AvailabilityStatus addAvailabilityStatus(AvailabilityStatus status) {
        if (availabilityStatusRepository.existsByAvailability(status.getAvailability())) {
            throw new IllegalArgumentException("Availability status must be unique");
        }
        availabilityStatusRepository.save(status);
        return status;
    }

    public AvailabilityStatus removeAvailabilityStatus(String statusName) {
        if (!availabilityStatusRepository.existsByAvailability(statusName)) {
            throw new IllegalArgumentException("Availability status doesnt exist");
        }
        AvailabilityStatus status = availabilityStatusRepository.findByAvailability(statusName);
        availabilityStatusRepository.deleteById(status.getAvailabilityId());
        return status;
    }

    public AvailabilityStatus updateAvailabilityStatus(String name, String newName) {
        if (!availabilityStatusRepository.existsByAvailability(name)) {
            throw new IllegalArgumentException("Availability status doesnt exist");
        }
        if (availabilityStatusRepository.existsByAvailability(newName)) {
            throw new IllegalArgumentException("New availability status name already exists in the database");
        }
        AvailabilityStatus status = availabilityStatusRepository.findByAvailability(name);
        status.setAvailability(newName);
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
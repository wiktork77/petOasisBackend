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
        availabilityStatusRepository.save(status);
        return status;
    }

    public AvailabilityStatus removeAvailabilityStatus(AvailabilityStatus status) {
        availabilityStatusRepository.delete(status);
        return status;
    }
}

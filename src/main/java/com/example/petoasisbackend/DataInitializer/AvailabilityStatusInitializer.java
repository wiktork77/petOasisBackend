package com.example.petoasisbackend.DataInitializer;

import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import com.example.petoasisbackend.Repository.AvailabilityStatusRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
@Order(1)
public class AvailabilityStatusInitializer implements ApplicationRunner {
    private AvailabilityStatusRepository availabilityStatusRepository;
    public final static List<String> coreStatuses = Arrays.asList(
            "Available",
            "Unavailable",
            "On a walk"
    );
    public AvailabilityStatusInitializer(AvailabilityStatusRepository availabilityStatusRepository) {
        this.availabilityStatusRepository = availabilityStatusRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (String status : coreStatuses) {
            if (!availabilityStatusRepository.existsByAvailability(status)) {
                availabilityStatusRepository.save(new AvailabilityStatus(status));
            }
        }
    }
}

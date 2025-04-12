package com.example.petoasisbackend.DataInitializer;

import com.example.petoasisbackend.Model.Status.HealthStatus;
import com.example.petoasisbackend.Repository.HealthStatusRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(2)
public class HealthStatusInitializer implements ApplicationRunner {
    private HealthStatusRepository healthStatusRepository;
    public final static List<String> coreStatuses = Arrays.asList(
            "Healthy",
            "Under observation",
            "Sick",
            "Critical"
    );

    public HealthStatusInitializer(HealthStatusRepository healthStatusRepository) {
        this.healthStatusRepository = healthStatusRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (String status : coreStatuses) {
            if (!healthStatusRepository.existsByHealthStatus(status)) {
                healthStatusRepository.save(new HealthStatus(status));
            }
        }
    }
}


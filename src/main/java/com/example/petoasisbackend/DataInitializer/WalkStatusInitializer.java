package com.example.petoasisbackend.DataInitializer;

import com.example.petoasisbackend.Model.Status.WalkStatus;
import com.example.petoasisbackend.Repository.WalkStatusRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(3)
public class WalkStatusInitializer implements ApplicationRunner {
    public final static List<String> coreStatuses = Arrays.asList("Pending", "In progress", "Finished", "Cancelled");
    private final WalkStatusRepository walkStatusRepository;

    public WalkStatusInitializer(WalkStatusRepository walkStatusRepository) {
        this.walkStatusRepository = walkStatusRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (String status : coreStatuses) {
            if (!walkStatusRepository.existsByStatus(status)) {
                walkStatusRepository.save(new WalkStatus(status));
            }
        }
    }
}

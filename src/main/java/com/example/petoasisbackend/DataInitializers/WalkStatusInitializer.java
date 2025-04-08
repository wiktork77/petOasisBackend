package com.example.petoasisbackend.DataInitializers;

import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import com.example.petoasisbackend.Repository.WalkStatusRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
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

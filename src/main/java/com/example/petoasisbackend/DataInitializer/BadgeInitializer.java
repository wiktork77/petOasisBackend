package com.example.petoasisbackend.DataInitializer;

import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Model.Descriptor.Badge;
import com.example.petoasisbackend.Repository.BadgeRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BadgeInitializer implements ApplicationRunner {
    private BadgeRepository badgeRepository;

    public BadgeInitializer(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    private final List<String> baseBadges = Arrays.asList(
            "Happy",
            "Excited",
            "Energetic",
            "Relaxed"
    );

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (String b: baseBadges) {
            if (!badgeRepository.existsByBadgeName(b)) {
                Badge badge = new Badge(b);
                badgeRepository.save(badge);
            }
        }
    }
}

package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Descriptor.AnimalBadge;
import com.example.petoasisbackend.Model.Descriptor.AnimalBadgeId;
import com.example.petoasisbackend.Model.Descriptor.Badge;
import com.example.petoasisbackend.Repository.AnimalBadgeRepository;
import com.example.petoasisbackend.Repository.AnimalRepository;
import com.example.petoasisbackend.Repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeService {
    @Autowired
    private BadgeRepository badgeRepository;
    @Autowired
    private AnimalBadgeRepository animalBadgeRepository;
    @Autowired
    private AnimalRepository animalRepository;

    public List<Badge> getBadges() {
        return badgeRepository.findAll();
    }

    public List<AnimalBadge> getAssignedBadges() {
        return animalBadgeRepository.findAll();
    }

    public AnimalBadge assignBadge(Long animalId, String badgeName) {
        if (!animalRepository.existsById(animalId)) {
            throw new IllegalArgumentException("Animal doesn't exist!");
        }
        if (!badgeRepository.existsByBadgeName(badgeName)) {
            throw new IllegalArgumentException("Badge doesn't exist!");
        }
        Animal animal = animalRepository.getReferenceById(animalId);
        Badge badge = badgeRepository.getBadgeByBadgeName(badgeName);
        if (animalBadgeRepository.existsByAnimalAndBadge(animal, badge)) {
            throw new IllegalArgumentException("Badge already assigned.");
        }
        AnimalBadge animalBadge = new AnimalBadge();
        animalBadge.setAnimal(animal);
        animalBadge.setBadge(badge);

        return animalBadgeRepository.save(animalBadge);
    }

    public AnimalBadge cancelBadge(Long animalId, String badgeName) {
        if (!animalRepository.existsById(animalId)) {
            throw new IllegalArgumentException("Animal doesn't exist!");
        }
        if (!badgeRepository.existsByBadgeName(badgeName)) {
            throw new IllegalArgumentException("Badge doesn't exist!");
        }
        Animal animal = animalRepository.getReferenceById(animalId);
        Badge badge = badgeRepository.getBadgeByBadgeName(badgeName);
        if (!animalBadgeRepository.existsByAnimalAndBadge(animal, badge)) {
            throw new IllegalArgumentException("Badge not assigned, cannot cancel.");
        }
        AnimalBadge animalBadge = animalBadgeRepository.findById(new AnimalBadgeId(animalId, badge.getBadgeId())).get();
        animalBadgeRepository.delete(animalBadge);
        return animalBadge;
    }


    public Badge addBadge(Badge badge) {
        if (badgeRepository.existsByBadgeName(badge.getBadgeName())) {
            throw new IllegalArgumentException(badge.getBadgeName() + " badge already exists!");
        }
        badgeRepository.save(badge);
        return badge;
    }

    public Badge deleteBadge(String badgeName) {
        if (!badgeRepository.existsByBadgeName(badgeName)) {
            throw new IllegalArgumentException(badgeName + " badge doesn't exist!");
        }
        Badge badge = badgeRepository.getBadgeByBadgeName(badgeName);
        badgeRepository.delete(badge);
        return badge;
    }

    public Badge updateBadgeName(String name, String newName) {
        if (!badgeRepository.existsByBadgeName(name)) {
            throw new IllegalArgumentException("Badge doesnt exist");
        }
        if (badgeRepository.existsByBadgeName(newName)) {
            throw new IllegalArgumentException("New badge name already exists");
        }
        Badge badge = badgeRepository.getBadgeByBadgeName(name);
        badge.setBadgeName(newName);
        badgeRepository.save(badge);
        return badge;
    }


}

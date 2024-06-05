package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.Model.Descriptor.Badge;
import com.example.petoasisbackend.Repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeService {
    @Autowired
    private BadgeRepository badgeRepository;

    public List<Badge> getBadges() {
        return badgeRepository.findAll();
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

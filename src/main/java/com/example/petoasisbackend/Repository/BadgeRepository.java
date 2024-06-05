package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.Descriptor.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BadgeRepository extends JpaRepository<Badge, Integer> {
    boolean existsByBadgeName(String badgeName);
    Badge getBadgeByBadgeName(String badgeName);
}

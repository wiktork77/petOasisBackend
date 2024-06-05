package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Descriptor.AnimalBadge;
import com.example.petoasisbackend.Model.Descriptor.AnimalBadgeId;
import com.example.petoasisbackend.Model.Descriptor.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalBadgeRepository extends JpaRepository<AnimalBadge, AnimalBadgeId> {
    boolean existsByAnimalAndBadge(Animal animal, Badge badge);
}

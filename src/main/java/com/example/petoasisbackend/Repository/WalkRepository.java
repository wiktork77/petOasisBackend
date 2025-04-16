package com.example.petoasisbackend.Repository;


import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Users.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalkRepository extends JpaRepository<Walk, Long> {
    Walk getWalkByCaretaker(Person caretaker);
    Walk getWalkByPupil(Animal pupil);

    List<Walk> getWalksByCaretaker_PersonId(Long personId);

    List<Walk> getWalksByPupil_AnimalId(Long animalId);
}

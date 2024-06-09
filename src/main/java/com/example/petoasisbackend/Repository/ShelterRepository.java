package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    boolean existsByName(String name);
    Shelter getShelterByName(String name);
}

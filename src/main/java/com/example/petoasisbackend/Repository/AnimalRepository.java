package com.example.petoasisbackend.Repository;


import com.example.petoasisbackend.Model.Animal.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}

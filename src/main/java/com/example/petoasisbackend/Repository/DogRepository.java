package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.Animal.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
}

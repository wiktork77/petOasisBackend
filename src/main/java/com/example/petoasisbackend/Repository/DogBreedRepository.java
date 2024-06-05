package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogBreedRepository extends JpaRepository<DogBreed, Integer> {
    boolean existsByBreedName(String breedName);
    DogBreed findDogBreedByBreedName(String breedName);
}

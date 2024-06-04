package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CatBreedRepository extends JpaRepository<CatBreed, Integer> {
    CatBreed findCatBreedByBreedName(String breedName);
}

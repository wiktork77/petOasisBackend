package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Repository.CatBreedRepository;
import com.example.petoasisbackend.Repository.DogBreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BreedService {
    @Autowired
    private CatBreedRepository catBreedRepository;
    @Autowired
    private DogBreedRepository dogBreedRepository;


    public List<CatBreed> getCatBreeds() {
        return catBreedRepository.findAll();
    }
    public List<DogBreed> getDogBreeds() {
        return dogBreedRepository.findAll();
    }

    public CatBreed addCatBreed(CatBreed breed) {
        if (catBreedRepository.existsByBreedName(breed.getBreedName())) {
            throw new IllegalArgumentException(breed.getBreedName() + " cat breed already exists!");
        }
        catBreedRepository.save(breed);
        return breed;
    }

    public DogBreed addDogBreed(DogBreed breed) {
        if (dogBreedRepository.existsByBreedName(breed.getBreedName())) {
            throw new IllegalArgumentException(breed.getBreedName() + " dog breed already exists!");
        }
        dogBreedRepository.save(breed);
        return breed;
    }

    public CatBreed removeCatBreed(String breedName) {
        if (!catBreedRepository.existsByBreedName(breedName)) {
            throw new IllegalArgumentException(breedName + " cat breed doesn't exist!");
        }
        CatBreed breed = catBreedRepository.findCatBreedByBreedName(breedName);
        catBreedRepository.delete(breed);
        return breed;
    }

    public DogBreed removeDogBreed(String breedName) {
        if (!dogBreedRepository.existsByBreedName(breedName)) {
            throw new IllegalArgumentException(breedName + " dog breed doesn't exist!");
        }
        DogBreed breed = dogBreedRepository.findDogBreedByBreedName(breedName);
        dogBreedRepository.delete(breed);
        return breed;
    }

    public CatBreed updateCatBreed(String name, String newName) {
        if (!catBreedRepository.existsByBreedName(name)) {
            throw new IllegalArgumentException(name + " cat breed doesn't exist!");
        }
        if (catBreedRepository.existsByBreedName(newName)) {
            throw new IllegalArgumentException(newName + " cat breed already exists, cannot update " + name + "!");
        }
        CatBreed breed = catBreedRepository.findCatBreedByBreedName(name);
        breed.setBreedName(newName);
        catBreedRepository.save(breed);
        return breed;
    }

    public DogBreed updateDogBreed(String name, String newName) {
        if (!dogBreedRepository.existsByBreedName(name)) {
            throw new IllegalArgumentException(name + " dog breed doesn't exist, cannot update null!");
        }
        if (dogBreedRepository.existsByBreedName(newName)) {
            throw new IllegalArgumentException(newName + " dog breed already exists, cannot update " + name + "!");
        }
        DogBreed breed = dogBreedRepository.findDogBreedByBreedName(name);
        breed.setBreedName(newName);
        dogBreedRepository.save(breed);
        return breed;
    }


}

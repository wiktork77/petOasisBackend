package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Repository.CatBreedRepository;
import com.example.petoasisbackend.Repository.DogBreedRepository;

import java.util.List;

public class BreedService {
    private CatBreedRepository catBreedRepository;
    private DogBreedRepository dogBreedRepository;


    public List<CatBreed> getCatBreeds() {
        return catBreedRepository.findAll();
    }
    public List<DogBreed> getDogBreed() {
        return dogBreedRepository.findAll();
    }

    public CatBreed addCatBreed() {
        return null;
    }


}

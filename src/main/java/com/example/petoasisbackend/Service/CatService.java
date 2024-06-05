package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Repository.CatBreedRepository;
import com.example.petoasisbackend.Repository.CatRepository;
import com.example.petoasisbackend.Repository.DogBreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatService {
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private CatBreedRepository catBreedRepository;

    public List<Cat> getCats() {
        return catRepository.findAll();
    }

    public Cat add(Animal animal, Cat cat) {
        CatBreed breed = cat.getCatBreed();
        if (!catBreedRepository.existsById(breed.getBreedId())) {
            throw new IllegalArgumentException(breed.getBreedName() + " breed doesnt exist");
        }
        cat.setAnimal(animal);
        catRepository.save(cat);
        return cat;
    }

    public Cat remove(Long id) {
        if (!catRepository.existsById(id)) {
            throw new IllegalArgumentException("Cat with id " + id  + " doesnt exist");
        }
        Cat cat = catRepository.getReferenceById(id);
        catRepository.delete(cat);
        return cat;
    }

    public Cat updateCat(Long id, Cat other) {
        if (!catRepository.existsById(id)) {
            throw new IllegalArgumentException("Cat with id " + id + " doesn't exist.");
        }
        Cat cat = catRepository.getReferenceById(id);
        cat.inheritFromOtherCat(other);
        catRepository.save(cat);
        return cat;
    }


}

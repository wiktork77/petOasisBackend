package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Repository.DogBreedRepository;
import com.example.petoasisbackend.Repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogService {
    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private DogBreedRepository dogBreedRepository;

    public List<Dog> getDogs() {
        return dogRepository.findAll();
    }

    public Dog add(Animal animal, Dog dog) {
        DogBreed breed = dog.getDogBreed();
        if (!dogBreedRepository.existsById(breed.getBreedId())) {
            throw new IllegalArgumentException(breed.getBreedName() + " breed doesnt exist");
        }
        dog.setAnimal(animal);
        dogRepository.save(dog);
        return dog;
    }

    public Dog remove(Long id) {
        if (!dogRepository.existsById(id)) {
            throw new IllegalArgumentException("Dog with id " + id  + " doesnt exist");
        }
        Dog dog = dogRepository.getReferenceById(id);
        dogRepository.delete(dog);
        return dog;
    }

    public Dog updateDog(Long id, Dog other) {
        if (!dogRepository.existsById(id)) {
            throw new IllegalArgumentException("Dog with id " + id + " doesn't exist.");
        }
        Dog dog = dogRepository.getReferenceById(id);
        dog.inheritFromOtherDog(other);
        dogRepository.save(dog);
        return dog;
    }

}

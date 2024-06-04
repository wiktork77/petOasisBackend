package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;

    public List<Animal> getAnimals() {
        return animalRepository.findAll();
    }

    public void insertTestAnimal() {
        animalRepository.save(new Animal());
    }
}

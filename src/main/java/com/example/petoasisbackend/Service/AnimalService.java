package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import com.example.petoasisbackend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private AvailabilityStatusRepository availabilityStatusRepository;
    @Autowired
    private HealthStatusRepository healthStatusRepository;
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private DogRepository dogRepository;

    public Animal getAnimal(Long id) throws AnimalDoesntExistException {
        if (!animalRepository.existsById(id)) {
            throw new AnimalDoesntExistException("Cannot get animal with id '" + id + "'" + " because it doesn't exist");
        }
        return animalRepository.findById(id).get();
    }


    public Animal addAnimal(Animal animal) {
        AvailabilityStatus availabilityStatus = animal.getAvailabilityStatus();
        HealthStatus healthStatus = animal.getHealthStatus();
        if (availabilityStatus != null && !availabilityStatusRepository.existsById(availabilityStatus.getAvailabilityId())) {
            throw new IllegalArgumentException("Availability status doesnt exist");
        }
        if (healthStatus != null && !healthStatusRepository.existsById(healthStatus.getHealthId())) {
            throw new IllegalArgumentException("Health status doesnt exist");
        }
        animalRepository.save(animal);
        return animal;
    }

    public Animal removeAnimal(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new IllegalArgumentException("Animal with id " + id + " doesn't exist.");
        }
        Animal animal = animalRepository.findById(id).get();
        animalRepository.delete(animal);
        return animal;
    }


}

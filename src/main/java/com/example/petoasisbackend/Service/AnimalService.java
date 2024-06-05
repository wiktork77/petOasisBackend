package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import com.example.petoasisbackend.Repository.AnimalRepository;
import com.example.petoasisbackend.Repository.AvailabilityStatusRepository;
import com.example.petoasisbackend.Repository.HealthStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private AvailabilityStatusRepository availabilityStatusRepository;
    @Autowired
    private HealthStatusRepository healthStatusRepository;

    public List<Animal> getAnimals() {
        return animalRepository.findAll();
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

    public Animal updateAnimal(Long id, Animal other) {
        if (!animalRepository.existsById(id)) {
            throw new IllegalArgumentException("Animal with id " + id + " doesn't exist.");
        }
        Animal animal = animalRepository.getReferenceById(id);
        animal.inheritFromOtherAnimal(other);
        animalRepository.save(animal);
        return animal;
    }

}

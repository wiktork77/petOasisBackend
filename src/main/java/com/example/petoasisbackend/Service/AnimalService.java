package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalAvailabilityStatusChangeDTO;
import com.example.petoasisbackend.DTO.Animal.Animal.AnimalHealthStatusChangeDTO;
import com.example.petoasisbackend.DTO.Animal.Animal.AnimalPictureChangeDTO;
import com.example.petoasisbackend.DTO.Animal.Animal.AnimalUpdateDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusDoesntExistException;
import com.example.petoasisbackend.Mapper.Animal.AnimalMapper;
import com.example.petoasisbackend.Mapper.Animal.CatMapper;
import com.example.petoasisbackend.Mapper.Animal.DogMapper;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import com.example.petoasisbackend.Repository.*;
import com.example.petoasisbackend.Request.Animal.AnimalUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.Status.AvailabilityStatus.AvailabilityStatusUpdateRequest;
import com.example.petoasisbackend.Request.Status.HealthStatus.HealthStatusUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private AnimalMapper animalMapper;

    @Autowired
    private DogMapper dogMapper;

    @Autowired
    private CatMapper catMapper;
    @Autowired
    private HealthStatusRepository healthStatusRepository;
    @Autowired
    private AvailabilityStatusRepository availabilityStatusRepository;

    public List<ModelDTO<Animal>> get(DataDetailLevel level) {
        List<Animal> animals = animalRepository.findAll();
        var mapper = animalMapper.getMapper(level);

        return animals.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<Animal> getById(Long id, DataDetailLevel level) throws AnimalDoesntExistException {
        if (!animalRepository.existsById(id)) {
            throw new AnimalDoesntExistException("Cannot get animal with id '" + id + "' because it doesn't exist");
        }

        Animal animal = animalRepository.findById(id).get();
        var mapper = animalMapper.getMapper(level);

        return mapper.apply(animal);
    }

    public List<Object> getMixed(DataDetailLevel level) {
        List<Animal> animals = animalRepository.findAll();
        var dMapper = dogMapper.getMapper(level);
        var cMapper = catMapper.getMapper(level);

        List<Object> mixedAnimals = new LinkedList<>();

        for (Animal a : animals) {
            switch (a.getType()) {
                case DOG -> {
                    Dog dog = dogRepository.findById(a.getParentId()).get();
                    mixedAnimals.add(dMapper.apply(dog));
                }
                case CAT -> {
                    Cat cat = catRepository.findById(a.getParentId()).get();
                    mixedAnimals.add(cMapper.apply(cat));
                }
            }
        }
        return mixedAnimals;
    }

    public AnimalPictureChangeDTO changePicture(Long id, String url) throws AnimalDoesntExistException {
        if (!animalRepository.existsById(id)) {
            throw new AnimalDoesntExistException("Cannot change picture of animal with id '" + id + "' because it doesn't exist");
        }

        Animal animal = animalRepository.findById(id).get();
        animal.setPictureURL(url);

        animalRepository.save(animal);

        return AnimalPictureChangeDTO.fromAnimal(animal);
    }

    public AnimalHealthStatusChangeDTO changeHealthStatus(Long id, HealthStatusUpdateRequest request) throws AnimalDoesntExistException, HealthStatusDoesntExistException {
        if (!animalRepository.existsById(id)) {
            throw new AnimalDoesntExistException("Cannot change health status of animal with id '" + id + "' because it doesn't exist");
        }

        if (healthStatusRepository.existsByHealthStatus(request.getHealthStatus())) {
            throw new HealthStatusDoesntExistException(
                    "Cannot change health status of animal with id '" + id
                            + "' to '" + request.getHealthStatus() + "' because the status doesn't exist"
            );
        }

        Animal animal = animalRepository.findById(id).get();

        HealthStatus status = healthStatusRepository.findByHealthStatus(request.getHealthStatus());

        animal.setHealthStatus(status);
        animalRepository.save(animal);

        return AnimalHealthStatusChangeDTO.fromAnimal(animal);
    }

    public AnimalAvailabilityStatusChangeDTO changeAvailabilityStatus(Long id, AvailabilityStatusUpdateRequest request) throws AnimalDoesntExistException, HealthStatusDoesntExistException {
        if (!animalRepository.existsById(id)) {
            throw new AnimalDoesntExistException("Cannot change availability status of animal with id '" + id + "' because it doesn't exist");
        }

        if (availabilityStatusRepository.existsByAvailability(request.getAvailability())) {
            throw new HealthStatusDoesntExistException(
                    "Cannot change health status of animal with id '" + id
                            + "' to '" + request.getAvailability() + "' because the status doesn't exist"
            );
        }

        Animal animal = animalRepository.findById(id).get();

        AvailabilityStatus status = availabilityStatusRepository.findByAvailability(request.getAvailability());

        animal.setAvailabilityStatus(status);
        animalRepository.save(animal);

        return AnimalAvailabilityStatusChangeDTO.fromAnimal(animal);
    }


    public AnimalUpdateDTO update(Long id, AnimalUpdateRequest request) throws AnimalDoesntExistException {
        if (!animalRepository.existsById(id)) {
            throw new AnimalDoesntExistException("Cannot update the animal with id '" + id + "' because it doesn't exist");
        }

        Animal animal = animalRepository.findById(id).get();
        animal.update(request);
        animalRepository.save(animal);

        return AnimalUpdateDTO.fromAnimal(animal);
    }




}

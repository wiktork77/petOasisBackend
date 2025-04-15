package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.Animal.Dog.DogMinimumDTO;
import com.example.petoasisbackend.DTO.Animal.Dog.DogUpdateDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusDoesntExistException;
import com.example.petoasisbackend.Exception.Breed.Dog.DogBreedDoesntExist;
import com.example.petoasisbackend.Exception.Dog.DogDoesntExistException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Mapper.Animal.DogMapper;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.AnimalType;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Repository.*;
import com.example.petoasisbackend.Request.Animal.Dog.DogAddRequest;
import com.example.petoasisbackend.Request.Animal.Dog.DogUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DogService {
    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private DogBreedRepository dogBreedRepository;
    @Autowired
    private HealthStatusRepository healthStatusRepository;
    @Autowired
    private AvailabilityStatusRepository availabilityStatusRepository;
    @Autowired
    private ShelterRepository shelterRepository;
    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private DogMapper dogMapper;

    public List<ModelDTO<Dog>> getDogs(DataDetailLevel level) {
        List<Dog> dogs = dogRepository.findAll();
        var mapper = dogMapper.getMapper(level);

        return dogs.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<Dog> getDogById(Long id, DataDetailLevel level) throws DogDoesntExistException {
        if (!dogRepository.existsById(id)) {
            throw new DogDoesntExistException("Cannot get dog with id '" + id + "' because it doesn't exist");
        }

        Dog dog = dogRepository.findById(id).get();
        var mapper = dogMapper.getMapper(level);

        return mapper.apply(dog);
    }

    @Transactional
    public DogMinimumDTO addDog(DogAddRequest request) throws DogBreedDoesntExist, AvailabilityStatusDoesntExistException, HealthStatusDoesntExistException, ShelterDoesntExistException {
        if (!shelterRepository.existsByShelterId(request.getShelterId())) {
            throw new ShelterDoesntExistException("Cannot add a dog because shelter with id '" + request.getShelterId() + "' doesn't exist");
        }

        if (!dogBreedRepository.existsById(request.getDogBreedId())) {
            throw new DogBreedDoesntExist("Cannot add a dog because dog breed with id '" + request.getDogBreedId() + "' doesn't exist");
        }

        if (!healthStatusRepository.existsById(request.getHealthStatusId())) {
            throw new HealthStatusDoesntExistException("Cannot add a dog because health status with id '" + request.getHealthStatusId() + "' doesn't exist");
        }

        if (!availabilityStatusRepository.existsById(request.getAvailabilityStatusId())) {
            throw new AvailabilityStatusDoesntExistException("Cannot add a dog because availability status with id '" + request.getAvailabilityStatusId() + "' doesn't exist");
        }

        Shelter shelter = shelterRepository.findById(request.getShelterId()).get();
        DogBreed breed = dogBreedRepository.findById(request.getDogBreedId()).get();
        HealthStatus healthStatus = healthStatusRepository.findById(request.getHealthStatusId()).get();
        AvailabilityStatus availabilityStatus = availabilityStatusRepository.findById(request.getAvailabilityStatusId()).get();

        Animal animal = Animal.fromAnimalAddRequest(request);
        animal.setHome(shelter);
        animal.setHealthStatus(healthStatus);
        animal.setAvailabilityStatus(availabilityStatus);
        animal.setType(AnimalType.DOG);

        Dog dog = Dog.fromDogAddRequest(request);
        dog.setDogBreed(breed);

        Dog savedDog = dogRepository.save(dog);

        animal.setParentId(savedDog.getDogId());

        Animal savedAnimal = animalRepository.save(animal);

        savedDog.setAnimal(savedAnimal);

        dogRepository.save(savedDog);

        return DogMinimumDTO.fromDog(savedDog);
    }

    public DogUpdateDTO update(Long id, DogUpdateRequest request) throws DogDoesntExistException {
        if (!dogRepository.existsById(id)) {
            throw new DogDoesntExistException("Cannot update dog with id '" + id + "' because it doesn't exist");
        }

        Dog dog = dogRepository.findById(id).get();
        dog.update(request);

        Dog savedDog = dogRepository.save(dog);

        return DogUpdateDTO.fromDog(savedDog);
    }

    public void delete(Long id) throws DogDoesntExistException {
        if (!dogRepository.existsById(id)) {
            throw new DogDoesntExistException("Cannot delete dog with id '" + id + "' because it doesn't exist");
        }

        dogRepository.deleteById(id);
    }



}

package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.Animal.Cat.CatMinimumDTO;
import com.example.petoasisbackend.DTO.Animal.Cat.CatChangeBreedDTO;
import com.example.petoasisbackend.DTO.Animal.Cat.CatUpdateDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusDoesntExistException;
import com.example.petoasisbackend.Exception.Breed.Cat.CatBreedDoesntExist;
import com.example.petoasisbackend.Exception.Breed.Dog.DogBreedDoesntExist;
import com.example.petoasisbackend.Exception.Cat.CatDoesntExistException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Mapper.Animal.CatMapper;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.AnimalType;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Repository.*;
import com.example.petoasisbackend.Request.Animal.Cat.CatAddRequest;
import com.example.petoasisbackend.Request.Animal.Cat.CatChangeBreedRequest;
import com.example.petoasisbackend.Request.Animal.Cat.CatUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatService {
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private CatBreedRepository catBreedRepository;
    @Autowired
    private HealthStatusRepository healthStatusRepository;
    @Autowired
    private AvailabilityStatusRepository availabilityStatusRepository;
    @Autowired
    private ShelterRepository shelterRepository;
    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private CatMapper catMapper;

    public List<ModelDTO<Cat>> get(DataDetailLevel level) {
        List<Cat> cats = catRepository.findAll();
        var mapper = catMapper.getMapper(level);

        return cats.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<Cat> getById(Long id, DataDetailLevel level) throws CatDoesntExistException {
        if (!catRepository.existsById(id)) {
            throw new CatDoesntExistException("Cannot get cat with id '" + id + "' because it doesn't exist");
        }

        Cat cat = catRepository.findById(id).get();
        var mapper = catMapper.getMapper(level);

        return mapper.apply(cat);
    }

    @Transactional
    public CatMinimumDTO add(CatAddRequest request) throws ShelterDoesntExistException, HealthStatusDoesntExistException, AvailabilityStatusDoesntExistException, DogBreedDoesntExist {
        if (!shelterRepository.existsByShelterId(request.getShelterId())) {
            throw new ShelterDoesntExistException("Cannot add a cat because shelter with id '" + request.getShelterId() + "' doesn't exist");
        }

        if (!catBreedRepository.existsById(request.getCatBreedId())) {
            throw new DogBreedDoesntExist("Cannot add a cat because cat breed with id '" + request.getCatBreedId() + "' doesn't exist");
        }

        if (!healthStatusRepository.existsById(request.getHealthStatusId())) {
            throw new HealthStatusDoesntExistException("Cannot add a cat because health status with id '" + request.getHealthStatusId() + "' doesn't exist");
        }

        if (!availabilityStatusRepository.existsById(request.getAvailabilityStatusId())) {
            throw new AvailabilityStatusDoesntExistException("Cannot add a dog because availability status with id '" + request.getAvailabilityStatusId() + "' doesn't exist");
        }

        Shelter shelter = shelterRepository.findById(request.getShelterId()).get();
        CatBreed breed = catBreedRepository.findById(request.getCatBreedId()).get();
        HealthStatus healthStatus = healthStatusRepository.findById(request.getHealthStatusId()).get();
        AvailabilityStatus availabilityStatus = availabilityStatusRepository.findById(request.getAvailabilityStatusId()).get();

        Animal animal = Animal.fromAnimalAddRequest(request);
        animal.setHome(shelter);
        animal.setHealthStatus(healthStatus);
        animal.setAvailabilityStatus(availabilityStatus);
        animal.setType(AnimalType.CAT);

        Cat cat = Cat.fromCatAddRequest(request);
        cat.setCatBreed(breed);

        Cat savedCat = catRepository.save(cat);

        animal.setParentId(savedCat.getCatId());

        Animal savedAnimal = animalRepository.save(animal);

        savedCat.setAnimal(savedAnimal);

        catRepository.save(savedCat);

        return CatMinimumDTO.fromCat(savedCat);

    }

    public CatUpdateDTO update(Long id, CatUpdateRequest request) throws CatDoesntExistException {
        if (!catRepository.existsById(id)) {
            throw new CatDoesntExistException("Cannot update cat with id '" + id + "' because it doesn't exist");
        }

        Cat cat = catRepository.findById(id).get();
        cat.update(request);

        Cat savedCat = catRepository.save(cat);

        return CatUpdateDTO.fromCat(savedCat);
    }

    public CatChangeBreedDTO changeBreed(Long id, CatChangeBreedRequest request) throws CatDoesntExistException, CatBreedDoesntExist {
        if (!catRepository.existsById(id)) {
            throw new CatDoesntExistException("Cannot change breed of cat with id '" + id + "' because it doesn't exist");
        }

        if (!catBreedRepository.existsByBreedName(request.getBreedName())) {
            throw new CatBreedDoesntExist("Cannot change breed of cat with id '" + id + "' to '" + request.getBreedName() + "' because it doesn't exist");
        }

        Cat cat = catRepository.findById(id).get();

        CatBreed breed = catBreedRepository.findByBreedName(request.getBreedName());

        cat.setCatBreed(breed);
        Cat savedCat = catRepository.save(cat);

        return CatChangeBreedDTO.fromCat(savedCat);
    }

    public void delete(Long id) throws CatDoesntExistException {
        if (!catRepository.existsById(id)) {
            throw new CatDoesntExistException("Cannot delete cat with id '" + id + "' because it doesn't exist");
        }

        catRepository.deleteById(id);
    }

}

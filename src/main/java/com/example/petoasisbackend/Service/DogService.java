package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.Animal.Dog.DogMinimumDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Dog.DogDoesntExistException;
import com.example.petoasisbackend.Mapper.Animal.DogMapper;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Repository.AnimalRepository;
import com.example.petoasisbackend.Repository.DogBreedRepository;
import com.example.petoasisbackend.Repository.DogRepository;
import com.example.petoasisbackend.Request.Animal.Dog.DogAddRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DogService {
    @Autowired
    private DogRepository dogRepository;
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

//    @Transactional
//    public DogMinimumDTO addDog(DogAddRequest request) {
//
//    }



}

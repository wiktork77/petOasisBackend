package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Mapper.Animal.CatMapper;
import com.example.petoasisbackend.Mapper.Animal.DogMapper;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import com.example.petoasisbackend.Repository.*;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private DogMapper dogMapper;

    @Autowired
    private CatMapper catMapper;

//    public List<Animal> get(DataDetailLevel level) {
//        List<Animal> animals = animalRepository.findAll();
//    }

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
}

package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.Animal.Cat.CatMediumDTO;
import com.example.petoasisbackend.DTO.Animal.Dog.DogMediumDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterMinimumDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterUpdateDTO;
import com.example.petoasisbackend.Exception.GSU.UserAlreadyExistsException;
import com.example.petoasisbackend.Exception.Shelter.ShelterAlreadyExistsException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Mapper.User.ShelterMapper;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Model.Enum.AccountType;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Repository.CatRepository;
import com.example.petoasisbackend.Repository.DogRepository;
import com.example.petoasisbackend.Repository.ShelterRepository;
import com.example.petoasisbackend.Repository.SystemUserRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.User.Shelter.ShelterAddRequest;
import com.example.petoasisbackend.Request.User.Shelter.ShelterUpdateRequest;
import com.example.petoasisbackend.Tools.Credentials.Encoder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShelterService {
    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private ShelterMapper shelterMapper;
    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private CatRepository catRepository;

    public List<ModelDTO<Shelter>> getShelters(DataDetailLevel level) {
        List<Shelter> shelters = shelterRepository.findAll();
        var mapper = shelterMapper.getMapper(level);

        return shelters.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<Shelter> getShelterById(Long id, DataDetailLevel level) throws ShelterDoesntExistException {
        if (!shelterRepository.existsByShelterId(id)) {
            throw new ShelterDoesntExistException(
                    "Cannot get shelter with id '" + id + "' because it doesn't exist"
            );
        }

        Shelter shelter = shelterRepository.findById(id).get();
        var mapper = shelterMapper.getMapper(level);

        return mapper.apply(shelter);
    }

    public ModelDTO<Shelter> getShelterByName(String name, DataDetailLevel level) throws ShelterDoesntExistException {
        if (!shelterRepository.existsByName(name)) {
            throw new ShelterDoesntExistException("Cannot get shelter with name '" + name + "' because it doesnt exist doesnt exist!");
        }

        Shelter shelter = shelterRepository.getShelterByName(name);
        var mapper = shelterMapper.getMapper(level);

        return mapper.apply(shelter);
    }

    @Transactional
    public ShelterMinimumDTO addShelter(ShelterAddRequest request) throws ShelterAlreadyExistsException, UserAlreadyExistsException {
        if (shelterRepository.existsByName(request.getName())) {
            throw new ShelterAlreadyExistsException(
                    "Cannot add shelter because shelter with '" + request.getName() + "' name already exists"
            );
        }
        if (systemUserRepository.existsByLogin(request.getLogin())) {
            throw new UserAlreadyExistsException(
                    "Cannot add user because given login already exists"
            );
        }

        Shelter shelter = Shelter.fromShelterAddRequest(request);
        Shelter savedShelter = shelterRepository.save(shelter);

        GeneralSystemUser gsu = GeneralSystemUser.fromUserAddRequest(request);
        gsu.setType(AccountType.SHELTER);
        gsu.setParentId(savedShelter.getShelterId());

        Encoder encoder = new Encoder();
        gsu.setPassword(encoder.encodePassword(request.getPassword()));

        GeneralSystemUser savedGsu = systemUserRepository.save(gsu);

        savedShelter.setGeneralSystemUser(savedGsu);
        shelterRepository.save(savedShelter);

        return ShelterMinimumDTO.fromShelter(shelter);
    }

    @Transactional
    public ShelterUpdateDTO updateShelter(Long shelterId, ShelterUpdateRequest request) throws ShelterAlreadyExistsException, ShelterDoesntExistException {
        if (shelterRepository.existsByName(request.getName())) {
            throw new ShelterAlreadyExistsException("Cannot update shelter with id '" + shelterId + "' because shelter with name '" + request.getName() + "' already exists");
        }

        if (!shelterRepository.existsByShelterId(shelterId)) {
            throw new ShelterDoesntExistException("Cannot update shelter with id '" + shelterId + "' because it doesn't exist");
        }

        Shelter shelter = shelterRepository.findById(shelterId).get();
        shelter.update(request);

        Shelter savedShelter = shelterRepository.save(shelter);

        return ShelterUpdateDTO.fromShelter(savedShelter);
    }

    @Transactional
    public void deleteShelterById(Long shelterId) throws ShelterDoesntExistException {
        if (!shelterRepository.existsByShelterId(shelterId)) {
            throw new ShelterDoesntExistException("Cannot delete shelter with id '" + shelterId + "' because it doesn't exist");
        }
        shelterRepository.deleteById(shelterId);
    }

    public List<Object> getAnimals(Long shelterId) throws ShelterDoesntExistException {
        if (!shelterRepository.existsByShelterId(shelterId)) {
            throw new ShelterDoesntExistException("Cannot get shelter animals because shelter with id '" + shelterId + "' doesn't exist");
        }
        Shelter shelter = shelterRepository.getReferenceById(shelterId);
        Set<Animal> animals = shelter.getAnimals();

        List<Object> walkables = new LinkedList<>();
        for (Animal a : animals) {
            switch (a.getType()) {
                case DOG -> {
                    Dog dog = dogRepository.findById(a.getParentId()).get();
                    walkables.add(DogMediumDTO.fromDog(dog));
                }
                case CAT -> {
                    Cat cat = catRepository.findById(a.getParentId()).get();
                    walkables.add(CatMediumDTO.fromCat(cat));
                }
            }
        }

        return walkables;
    }

}

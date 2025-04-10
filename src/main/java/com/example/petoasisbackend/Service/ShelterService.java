package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Repository.ShelterRepository;
import com.example.petoasisbackend.Repository.SystemUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelterService {
    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private ShelterRepository shelterRepository;

    public List<Shelter> getShelters() {
        return shelterRepository.findAll();
    }

    public Shelter getShelterById(Long id) throws ShelterDoesntExistException {
        if (!shelterRepository.existsByShelterId(id)) {
            throw new ShelterDoesntExistException("Shelter with id '" + id + "' doesnt exist!");
        }
        return shelterRepository.findById(id).get();
    }

    public Shelter getShelterByName(String name) throws ShelterDoesntExistException {
        if (!shelterRepository.existsByName(name)) {
            throw new ShelterDoesntExistException("Shelter with name '" + name + "' doesnt exist!");
        }
        return shelterRepository.getShelterByName(name);
    }
}

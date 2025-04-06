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

    public Shelter getShelterByLogin(String login) throws ShelterDoesntExistException {
        if (!systemUserRepository.existsByLogin(login)) {
            throw new ShelterDoesntExistException("Shelter with login '" + login + "' doesnt exist!");
        }
        GeneralSystemUser gsu = systemUserRepository.getGeneralSystemUserByLogin(login);
        return shelterRepository.getReferenceById(gsu.getParentId());
    }

    public Shelter getShelterByName(String name) throws ShelterDoesntExistException {
        if (!shelterRepository.existsByName(name)) {
            throw new ShelterDoesntExistException("Shelter with name '" + name + "' doesnt exist!");
        }
        return shelterRepository.getShelterByName(name);
    }

    @Transactional
    public Shelter addShelter(GeneralSystemUser generalSystemUser, Shelter shelter) {
        if (shelterRepository.existsByName(shelter.getName())) {
            throw new IllegalArgumentException("Shelter with name " + shelter.getName() + " already exists!");
        }
        if (systemUserRepository.existsByLogin(generalSystemUser.getLogin())) {
            throw new IllegalArgumentException("Shelter with login " + shelter.getGeneralSystemUser().getLogin() + " already exists!");
        }
        GeneralSystemUser savedGSU = addGSU(generalSystemUser);
        shelter.setGeneralSystemUser(savedGSU);
        Shelter shelter1 =  shelterRepository.save(shelter);
        savedGSU.setType("Shelter");
        savedGSU.setParentId(shelter1.getShelterId());
        return shelter1;
    }

    public Shelter deleteShelter(String login) {
        if (!systemUserRepository.existsByLogin(login)) {
            throw new IllegalArgumentException("Shelter with login " + login + " doesn't exist!");
        }
        GeneralSystemUser gsu = systemUserRepository.getGeneralSystemUserByLogin(login);
        Shelter shelter = shelterRepository.getReferenceById(gsu.getParentId());
        shelterRepository.delete(shelter);
        return shelter;
    }

    public Shelter updateShelter(String login, Shelter other) {
        if (!systemUserRepository.existsByLogin(login)) {
            throw new IllegalArgumentException("Shelter with login " + login + " doesn't exist!");
        }
        if (systemUserRepository.existsByLogin(other.getGeneralSystemUser().getLogin())) {
            throw new IllegalArgumentException("Shelter with login " + other.getGeneralSystemUser().getLogin() + " already exists!");
        }
        if (shelterRepository.existsByName(other.getName())) {
            throw new IllegalArgumentException("Shelter with name " + other.getName() + " already exists!");
        }
        GeneralSystemUser gsu = systemUserRepository.getGeneralSystemUserByLogin(login);
        Shelter originalShelter = shelterRepository.getReferenceById(gsu.getParentId());
        originalShelter.inheritFromOtherShelter(other);

        shelterRepository.save(originalShelter);
        return originalShelter;
    }


    private GeneralSystemUser addGSU(GeneralSystemUser generalSystemUser) {
        return systemUserRepository.save(generalSystemUser);
    }
}

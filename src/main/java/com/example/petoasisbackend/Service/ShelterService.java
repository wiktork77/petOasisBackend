package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.User.Shelter.ShelterConciseDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterMediumDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterMinimumDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterRegisterDTO;
import com.example.petoasisbackend.Exception.Shelter.ShelterAlreadyExistsException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterInvalidRequestException;
import com.example.petoasisbackend.Model.Users.AccountType;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Repository.ShelterRepository;
import com.example.petoasisbackend.Repository.SystemUserRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ShelterService {
    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private ShelterRepository shelterRepository;

    public Object getShelters(DataDetailLevel level) {
        List<Shelter> shelters = shelterRepository.findAll();

        switch (level) {
            case VERBOSE -> {
                return shelters;
            }
            case MEDIUM -> {
                return shelters.stream().map(ShelterMediumDTO::fromShelter).collect(Collectors.toList());
            }
            case CONCISE -> {
                return shelters.stream().map(ShelterConciseDTO::fromShelter).collect(Collectors.toList());
            }
            case MINIMUM -> {
                return shelters.stream().map(ShelterMinimumDTO::fromShelter).collect(Collectors.toList());
            }
            default -> {
                return List.of();
            }
        }
    }

    public Object getShelterById(Long id, DataDetailLevel level) throws ShelterDoesntExistException {
        if (!shelterRepository.existsByShelterId(id)) {
            throw new ShelterDoesntExistException(
                    "Cannot get shelter with id '" + id + "' because it doesn't exist"
            );
        }
        Shelter shelter = shelterRepository.findById(id).get();
        return dataDetailTransformShelter(shelter, level);
    }

    public Object getShelterByName(String name, DataDetailLevel level) throws ShelterDoesntExistException {
        if (!shelterRepository.existsByName(name)) {
            throw new ShelterDoesntExistException("Cannot get shelter with name '" + name + "' because it doesnt exist doesnt exist!");
        }
        Shelter shelter = shelterRepository.getShelterByName(name);

        return dataDetailTransformShelter(shelter, level);
    }

    public ShelterMinimumDTO addShelter(ShelterRegisterDTO shelterRegisterDTO) throws ShelterAlreadyExistsException, ShelterInvalidRequestException {
        if (shelterRepository.existsByName(shelterRegisterDTO.getName())) {
            throw new ShelterAlreadyExistsException(
                    "Cannot add shelter because shelter with '" + shelterRegisterDTO.getName()
                            + "' name already exists"
            );
        }
        if (systemUserRepository.existsByLogin(shelterRegisterDTO.getLogin())) {
            throw new ShelterAlreadyExistsException(
                    "Cannot add shelter because given login already exists"
            );
        }

        List<String> attributesToValidate = List.of(
                shelterRegisterDTO.getLogin(),
                shelterRegisterDTO.getPassword(),
                shelterRegisterDTO.getPhoneNumber(),
                shelterRegisterDTO.getName(),
                shelterRegisterDTO.getAddress(),
                shelterRegisterDTO.getEmail()
        );
        for (String attribute : attributesToValidate) {
            if (attribute == null || attribute.trim().isEmpty()) {
                throw new ShelterInvalidRequestException("Cannot add shelter because the request is invalid");
            }
        }

        Shelter shelter = new Shelter(
                shelterRegisterDTO.getName(),
                shelterRegisterDTO.getAddress(),
                shelterRegisterDTO.getWebsite(),
                shelterRegisterDTO.getEmail(),
                null
        );
        Shelter savedShelter = shelterRepository.save(shelter);

        GeneralSystemUser generalSystemUser = new GeneralSystemUser(
                shelterRegisterDTO.getLogin(),
                shelterRegisterDTO.getPassword(),
                false,
                shelterRegisterDTO.getPhoneNumber(),
                shelterRegisterDTO.getPictureUrl(),
                AccountType.SHELTER,
                savedShelter.getShelterId()
        );
        GeneralSystemUser savedGsu = systemUserRepository.save(generalSystemUser);

        savedShelter.setGeneralSystemUser(savedGsu);
        shelterRepository.save(savedShelter);

        return ShelterMinimumDTO.fromShelter(shelter);
    }

    private Object dataDetailTransformShelter(Shelter shelter, DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return shelter;
            }
            case MEDIUM -> {
                return ShelterMediumDTO.fromShelter(shelter);
            }
            case CONCISE -> {
                return ShelterConciseDTO.fromShelter(shelter);
            }
            case MINIMUM -> {
                return ShelterMinimumDTO.fromShelter(shelter);
            }
            default -> {
                return null;
            }
        }
    }
}

package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterMinimumDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterUpdateDTO;
import com.example.petoasisbackend.Exception.GSU.UserAlreadyExistsException;
import com.example.petoasisbackend.Exception.Shelter.ShelterAlreadyExistsException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Mapper.ShelterMapper;
import com.example.petoasisbackend.Model.Users.AccountType;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Repository.ShelterRepository;
import com.example.petoasisbackend.Repository.SystemUserRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.Shelter.ShelterAddRequest;
import com.example.petoasisbackend.Request.Shelter.ShelterUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShelterService {
    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private ShelterMapper shelterMapper;

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

        GeneralSystemUser generalSystemUser = GeneralSystemUser.fromShelterAddRequest(request);
        generalSystemUser.setParentId(savedShelter.getShelterId());
        GeneralSystemUser savedGsu = systemUserRepository.save(generalSystemUser);

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

}

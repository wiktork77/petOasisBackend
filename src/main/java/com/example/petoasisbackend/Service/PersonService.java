package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonMinimumDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonUpdateDTO;
import com.example.petoasisbackend.Exception.GSU.UserAlreadyExistsException;
import com.example.petoasisbackend.Exception.Person.PersonDoesntExistException;
import com.example.petoasisbackend.Mapper.User.PersonMapper;
import com.example.petoasisbackend.Model.Enum.AccountType;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Repository.PersonRepository;
import com.example.petoasisbackend.Repository.SystemUserRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.User.Person.PersonAddRequest;
import com.example.petoasisbackend.Request.User.Person.PersonUpdateRequest;
import com.example.petoasisbackend.Tools.Credentials.Encoder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private PersonMapper personMapper;

    public List<ModelDTO<Person>> getPeople(DataDetailLevel level) {
        List<Person> people = personRepository.findAll();
        var mapper = personMapper.getMapper(level);

        return people.stream().map(mapper).collect(Collectors.toList());
    }

    public ModelDTO<Person> getPersonById(Long id, DataDetailLevel level) throws PersonDoesntExistException {
        if (!personRepository.existsById(id)) {
            throw new PersonDoesntExistException("Cannot get person with id '" + id + "' because it doesn't exist");
        }

        Person person = personRepository.findById(id).get();
        var mapper = personMapper.getMapper(level);

        return mapper.apply(person);
    }

    @Transactional
    public PersonMinimumDTO addPerson(PersonAddRequest request) throws UserAlreadyExistsException {
        if (systemUserRepository.existsByLogin(request.getLogin())) {
            throw new UserAlreadyExistsException(
                    "Cannot add user because given login already exists"
            );
        }

        Person person = Person.fromPersonAddRequest(request);
        Person savedPerson = personRepository.save(person);

        GeneralSystemUser gsu = GeneralSystemUser.fromUserAddRequest(request);
        gsu.setType(AccountType.PERSON);
        gsu.setParentId(savedPerson.getPersonId());

        Encoder encoder = new Encoder();
        gsu.setPassword(encoder.encodePassword(request.getPassword()));

        GeneralSystemUser savedGsu = systemUserRepository.save(gsu);

        savedPerson.setGeneralSystemUser(savedGsu);
        personRepository.save(savedPerson);

        return PersonMinimumDTO.fromPerson(savedPerson);
    }

    public void deletePerson(Long personId) throws PersonDoesntExistException {
        if (!personRepository.existsById(personId)) {
            throw new PersonDoesntExistException("Cannot delete person with id '" + personId + "' because it doesn't exist");
        }
        personRepository.deleteById(personId);
    }

    @Transactional
    public PersonUpdateDTO updatePerson(Long personId, PersonUpdateRequest request) throws PersonDoesntExistException {
        if (!personRepository.existsById(personId)) {
            throw new PersonDoesntExistException("Cannot update person with id '" + personId + "' because it doesn't exist");
        }

        Person person = personRepository.findById(personId).get();
        person.update(request);

        Person savedPerson = personRepository.save(person);

        return PersonUpdateDTO.fromPerson(savedPerson);
    }
}

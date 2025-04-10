package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.Exception.Person.PersonAlreadyExistsException;
import com.example.petoasisbackend.Exception.Person.PersonDoesntExistException;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Repository.PersonRepository;
import com.example.petoasisbackend.Repository.SystemUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SystemUserRepository systemUserRepository;

    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) throws PersonDoesntExistException {
        if (!systemUserRepository.existsById(id)) {
            throw new PersonDoesntExistException("User with id " + id + " doesn't exist!");
        }
        return personRepository.findById(id).get();
    }

//    public Person getPersonByLogin(String login) throws PersonDoesntExistException {
//        if (!systemUserRepository.existsByLogin(login)) {
//            throw new PersonDoesntExistException("User with login " + login + " doesn't exist!");
//        }
//        GeneralSystemUser gsu = systemUserRepository.getGeneralSystemUserByLogin(login);
//        return personRepository.getReferenceById(gsu.getParentId());
//    }

//    @Transactional
//    public Person addPerson(GeneralSystemUser generalSystemUser, Person person) throws PersonAlreadyExistsException {
//        if (systemUserRepository.existsByLogin(generalSystemUser.getLogin())) {
//            throw new PersonAlreadyExistsException("User with login '" + generalSystemUser.getLogin() + "' already exists!");
//        }
//        GeneralSystemUser savedGSU = addGSU(generalSystemUser);
//        person.setGeneralSystemUser(savedGSU);
//        Person person1 = personRepository.save(person);
//        savedGSU.setType("Person");
//        savedGSU.setParentId(person1.getPersonId());
//        return person1;
//    }

//    public Person deletePerson(String login) throws PersonDoesntExistException {
//        if (!systemUserRepository.existsByLogin(login)) {
//            throw new PersonDoesntExistException("User with login " + login + " doesn't exist!");
//        }
//        GeneralSystemUser gsu = systemUserRepository.getGeneralSystemUserByLogin(login);
//        Person person = personRepository.getReferenceById(gsu.getParentId());
//        personRepository.delete(person);
//        return person;
//    }

//    public Person updatePerson(String login, Person other) throws PersonDoesntExistException, PersonAlreadyExistsException {
//        if (!systemUserRepository.existsByLogin(login)) {
//            throw new PersonDoesntExistException("User with login " + login + " doesn't exist!");
//        }
//        if (systemUserRepository.existsByLogin(other.getGeneralSystemUser().getLogin())) {
//            throw new PersonAlreadyExistsException("User with login " + login + " already exists!");
//        }
//        GeneralSystemUser gsu = systemUserRepository.getGeneralSystemUserByLogin(login);
//        Person originalPerson = personRepository.getReferenceById(gsu.getParentId());
//        originalPerson.inheritFromOtherPerson(other);
//        personRepository.save(originalPerson);
//        return originalPerson;
//    }


    private GeneralSystemUser addGSU(GeneralSystemUser generalSystemUser) {
        return systemUserRepository.save(generalSystemUser);
    }

}

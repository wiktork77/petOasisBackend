package com.example.petoasisbackend.Service;

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

    public Person getPerson(String login) {
        if (!systemUserRepository.existsByLogin(login)) {
            throw new IllegalArgumentException("User with login " + login + " doesn't exist!");
        }
        GeneralSystemUser gsu = systemUserRepository.getGeneralSystemUserByLogin(login);
        return personRepository.getReferenceById(gsu.getParentId());
    }

    @Transactional
    public Person addPerson(GeneralSystemUser generalSystemUser, Person person) {
        if (systemUserRepository.existsByLogin(generalSystemUser.getLogin())) {
            throw new IllegalArgumentException("User with login " + generalSystemUser.getLogin() + " already exists!");
        }
        GeneralSystemUser savedGSU = addGSU(generalSystemUser);
        person.setGeneralSystemUser(savedGSU);
        Person person1 = personRepository.save(person);
        savedGSU.setType("Person");
        savedGSU.setParentId(person1.getPersonId());
        return person1;
    }

    public Person deletePerson(String login) {
        if (!systemUserRepository.existsByLogin(login)) {
            throw new IllegalArgumentException("User with login " + login + " doesn't exist!");
        }
        GeneralSystemUser gsu = systemUserRepository.getGeneralSystemUserByLogin(login);
        Person person = personRepository.getReferenceById(gsu.getParentId());
        personRepository.delete(person);
        return person;
    }

    public Person updatePerson(String login, Person other) {
        if (!systemUserRepository.existsByLogin(login)) {
            throw new IllegalArgumentException("User with login " + login + " doesn't exist!");
        }
        if (systemUserRepository.existsByLogin(other.getGeneralSystemUser().getLogin())) {
            throw new IllegalArgumentException("User with login " + login + " already exists!");
        }
        GeneralSystemUser gsu = systemUserRepository.getGeneralSystemUserByLogin(login);
        Person originalPerson = personRepository.getReferenceById(gsu.getParentId());
        originalPerson.inheritFromOtherPerson(other);
        personRepository.save(originalPerson);
        return originalPerson;
    }


    private GeneralSystemUser addGSU(GeneralSystemUser generalSystemUser) {
        return systemUserRepository.save(generalSystemUser);
    }

}

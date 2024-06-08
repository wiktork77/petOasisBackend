package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Repository.PersonRepository;
import com.example.petoasisbackend.Repository.SystemUserRepository;
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
        if (!personRepository.existsByLogin(login)) {
            throw new IllegalArgumentException("User with login " + login + " doesn't exist!");
        }
        return personRepository.getPersonByLogin(login);
    }

    public Person addPerson(Person person) {
        if (personRepository.existsByLogin(person.getLogin())) {
            throw new IllegalArgumentException("User with login " + person.getLogin() + " already exists!");
        }
        return personRepository.save(person);
    }

}

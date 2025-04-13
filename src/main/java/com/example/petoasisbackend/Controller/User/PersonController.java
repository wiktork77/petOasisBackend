package com.example.petoasisbackend.Controller.User;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonMinimumDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonUpdateDTO;
import com.example.petoasisbackend.Exception.GSU.UserAlreadyExistsException;
import com.example.petoasisbackend.Exception.Person.PersonDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterAlreadyExistsException;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.GSUPersonRequest;
import com.example.petoasisbackend.Request.Person.PersonAddRequest;
import com.example.petoasisbackend.Request.Person.PersonUpdateRequest;
import com.example.petoasisbackend.Service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam DataDetailLevel level) {
        List<ModelDTO<Person>> people = personService.getPeople(level);
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<Person> person = personService.getPersonById(id, level);
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (PersonDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody @Valid PersonAddRequest request) {
        try {
            PersonMinimumDTO response = personService.addPerson(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ShelterAlreadyExistsException | UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid PersonUpdateRequest request) {
        try {
            PersonUpdateDTO response = personService.updatePerson(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (PersonDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            personService.deletePerson(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Cannot delete person because it is still referenced", HttpStatus.BAD_REQUEST);
        } catch (PersonDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}


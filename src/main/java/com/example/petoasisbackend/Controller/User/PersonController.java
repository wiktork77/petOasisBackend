package com.example.petoasisbackend.Controller.User;

import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Request.GSUPersonRequest;
import com.example.petoasisbackend.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/getAll")
    public List<Person> getAll() {
        return personService.getPersons();
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody GSUPersonRequest request) {
        try {
            GeneralSystemUser gsu = request.getGeneralSystemUser();
            Person person = request.getPerson();
//            personService.addPerson(gsu, person);
            return new ResponseEntity<>(person.getGeneralSystemUser().getLogin() + " added successfully", HttpStatus.OK);
        } catch (Exception e) { // TODO
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{login}")
    public ResponseEntity<String> delete(@PathVariable String login) {
        try {
//            personService.deletePerson(login);
            return new ResponseEntity<>(login + " added successfully", HttpStatus.OK);
        } catch (Exception e) { // TODO
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{login}")
    public ResponseEntity<String> update(@PathVariable String login, @RequestBody Person other) {
        try {
//            personService.updatePerson(login, other);
            return new ResponseEntity<>(login + " updated successfully", HttpStatus.OK);
        } catch (Exception e) { // TODO
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}



//try {
//
//} catch (IllegalArgumentException e) {
//
//}

package com.example.petoasisbackend.Controller.Users;

import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/add")
    public ResponseEntity<String> add(@RequestBody Person person) {
        try {
            personService.addPerson(person);
            return new ResponseEntity<>(person.getLogin() + " added successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}



//try {
//
//} catch (IllegalArgumentException e) {
//
//}

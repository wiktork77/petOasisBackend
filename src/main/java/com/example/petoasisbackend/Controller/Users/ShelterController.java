package com.example.petoasisbackend.Controller.Users;

import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Request.GSUPersonRequest;
import com.example.petoasisbackend.Request.GSUShelterRequest;
import com.example.petoasisbackend.Service.ShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelter")
public class ShelterController {
    @Autowired
    private ShelterService shelterService;


    @GetMapping("/getAll")
    public List<Shelter> getAll() {
        return shelterService.getShelters();
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody GSUShelterRequest request) {
        try {
            GeneralSystemUser gsu = request.getGeneralSystemUser();
            Shelter shelter = request.getShelter();
            shelterService.addShelter(gsu, shelter);
            return new ResponseEntity<>(shelter.getName() + " added successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{login}")
    public ResponseEntity<String> delete(@PathVariable String login) {
        try {
            Shelter shelter = shelterService.deleteShelter(login);
            return new ResponseEntity<>(shelter.getName() + " deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{login}")
    public ResponseEntity<String> update(@PathVariable String login, @RequestBody Shelter other) {
        try {
            shelterService.updateShelter(login, other);
            return new ResponseEntity<>(login + " updated successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

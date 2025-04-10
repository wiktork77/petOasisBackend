package com.example.petoasisbackend.Controller.Users;

import com.example.petoasisbackend.DTO.User.Shelter.ShelterRegisterDTO;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Request.DataDetailLevel;
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


    @GetMapping("/")
    public List<Shelter> getAll(@RequestParam DataDetailLevel level) {
        return shelterService.getShelters();
    }

    @GetMapping("/{id}")
    public Shelter getById(@PathVariable Integer id, @RequestParam DataDetailLevel level) {
        return null;
    }

    @GetMapping("/name/{name}")
    public Shelter getByName(@PathVariable String name, @RequestParam DataDetailLevel level) {
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody ShelterRegisterDTO shelterRegisterDTO) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return null;
    }

}

package com.example.petoasisbackend.Controller.Animal;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

//    @GetMapping("/")
//    public ResponseEntity<Object> get(@RequestParam DataDetailLevel level) {
//        List<Object> animals = animalService.get(level);
//        return new ResponseEntity<>(animals, HttpStatus.OK);
//    }

}

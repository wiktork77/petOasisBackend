package com.example.petoasisbackend.Controller;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private AnimalService animalService;


    @GetMapping("/getAll")
    public List<Animal> getAll() {
        return animalService.getAnimals();
    }

}

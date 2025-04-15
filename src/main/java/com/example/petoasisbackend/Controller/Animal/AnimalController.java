package com.example.petoasisbackend.Controller.Animal;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private AnimalService animalService;



}

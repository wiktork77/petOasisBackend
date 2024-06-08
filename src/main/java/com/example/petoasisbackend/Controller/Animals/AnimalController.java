package com.example.petoasisbackend.Controller.Animals;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Searchable;
import com.example.petoasisbackend.Service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @GetMapping("/getAll")
    public List<Searchable> getAll() {
        return animalService.getAnimals();
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Animal animal) {
        try {
            animalService.addAnimal(animal);
            return new ResponseEntity<>(animal.getName() + " successfully added!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            animalService.removeAnimal(id);
            return new ResponseEntity<>("Animal with id " + id + " successfully deleted!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Animal other) {
        try {
            animalService.updateAnimal(id, other);
            return new ResponseEntity<>("Animal with id " + id + " successfully updated!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

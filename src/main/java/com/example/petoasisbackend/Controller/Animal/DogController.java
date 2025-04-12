package com.example.petoasisbackend.Controller.Animal;

import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Request.AnimalDogRequest;
import com.example.petoasisbackend.Service.AnimalService;
import com.example.petoasisbackend.Service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dogs")
public class DogController {
    @Autowired
    private DogService dogService;
    @Autowired
    private AnimalService animalService;

    @GetMapping("/getAll")
    public List<Dog> getAll() {
        return dogService.getDogs();
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody AnimalDogRequest request) {
        try {
            Dog dog = request.getDog();
            Animal corelatedAnimal = animalService.addAnimal(request.getAnimal());
            dogService.add(corelatedAnimal, dog);
            return new ResponseEntity<>(corelatedAnimal.getName() + " successfully added!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            Dog dog = dogService.remove(id);
            return new ResponseEntity<>(dog.getAnimal().getName() + " successfully deleted!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Dog other) {
        try {
            Dog dog = dogService.updateDog(id, other);
            return new ResponseEntity<>(dog.getAnimal().getName() + " successfully updated!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

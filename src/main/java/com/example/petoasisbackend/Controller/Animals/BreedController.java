package com.example.petoasisbackend.Controller.Animals;


import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/breeds")
public class BreedController {
    @Autowired
    private BreedService breedService;

    @GetMapping("/dog/getAll")
    public List<DogBreed> getAllDogBreeds() {
        return breedService.getDogBreeds();
    }

    @PostMapping("/dog/add")
    public ResponseEntity<String> addDogBreed(@RequestBody DogBreed breed) {
        try {
            breedService.addDogBreed(breed);
            return new ResponseEntity<>(breed.getBreedName() + " dog breed added successfully!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/dog/delete/{name}")
    public ResponseEntity<String> removeDogBreed(@PathVariable String name) {
        try {
            DogBreed breed = breedService.removeDogBreed(name);
            return new ResponseEntity<>(name + " dog breed deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Foreign key violation - there are dogs with this breed!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/dog/update/{name}/{newName}")
    public ResponseEntity<String> updateDogBreed(@PathVariable String name, @PathVariable String newName) {
        try {
            DogBreed breed = breedService.updateDogBreed(name, newName);
            return new ResponseEntity<>(name + " dog breed updated to " + newName, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/cat/getAll")
    public List<CatBreed> getAllCatBreeds() {
        return breedService.getCatBreeds();
    }

    @PostMapping("/cat/add")
    public ResponseEntity<String> addCatBreed(@RequestBody CatBreed breed) {
        try {
            breedService.addCatBreed(breed);
            return new ResponseEntity<>(breed.getBreedName() + " cat breed added successfully!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/cat/delete/{name}")
    public ResponseEntity<String> removeCatBreed(@PathVariable String name) {
        try {
            CatBreed breed = breedService.removeCatBreed(name);
            return new ResponseEntity<>(name + " cat breed deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Foreign key violation - there are cats with this breed!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cat/update/{name}/{newName}")
    public ResponseEntity<String> updateCatBreed(@PathVariable String name, @PathVariable String newName) {
        try {
            CatBreed breed = breedService.updateCatBreed(name, newName);
            return new ResponseEntity<>(name + " cat breed updated to " + newName, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

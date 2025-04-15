package com.example.petoasisbackend.Controller.Animal;

import com.example.petoasisbackend.Exception.Dog.DogDoesntExistException;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Request.Animal.Dog.DogAddRequest;
import com.example.petoasisbackend.Request.Animal.Dog.DogUpdateRequest;
import com.example.petoasisbackend.Request.AnimalDogRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.AnimalService;
import com.example.petoasisbackend.Service.DogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/dog")
public class DogController {
    @Autowired
    private DogService dogService;

    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam DataDetailLevel level) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody @Valid DogAddRequest request) {
        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> add(@PathVariable Long id, @RequestBody @Valid DogUpdateRequest request) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id) {
        return null;
    }
}

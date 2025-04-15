package com.example.petoasisbackend.Controller.Animal;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.AnimalType;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Request.Animal.Cat.CatAddRequest;
import com.example.petoasisbackend.Request.Animal.Cat.CatChangeBreedRequest;
import com.example.petoasisbackend.Request.Animal.Cat.CatUpdateRequest;
import com.example.petoasisbackend.Request.Animal.Dog.DogChangeBreedRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.AnimalService;
import com.example.petoasisbackend.Service.CatService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cat")
public class CatController {
    @Autowired
    private CatService catService;


    @Operation(summary = "Get all cats with given detail level")
    @GetMapping("/")
    public ResponseEntity<List<ModelDTO<Cat>>> get(@RequestParam DataDetailLevel level) {
        return null;
    }


    @Operation(summary = "Get a cat with given id and given detail level")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        return null;
    }

    @Operation(summary = "Add a new cat")
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody @Valid CatAddRequest request) {
        return null;
    }

    @Operation(summary = "Update an existing cat")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid CatUpdateRequest request) {
        return null;
    }

    @PatchMapping("/{id}/breed")
    public ResponseEntity<Object> updateBreed(@PathVariable Long id, @RequestBody @Valid CatChangeBreedRequest request) {
        return null;
    }
    @Operation(summary = "Delete an existing cat")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return null;
    }

}

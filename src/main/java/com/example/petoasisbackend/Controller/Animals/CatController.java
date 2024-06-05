package com.example.petoasisbackend.Controller.Animals;

import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Request.AnimalCatRequest;
import com.example.petoasisbackend.Request.AnimalDogRequest;
import com.example.petoasisbackend.Service.AnimalService;
import com.example.petoasisbackend.Service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatController {
    @Autowired
    private CatService catService;
    @Autowired
    private AnimalService animalService;

    @GetMapping("/getAll")
    public List<Cat> getAll() {
        return catService.getCats();
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody AnimalCatRequest request) {
        try {
            Cat cat = request.getCat();
            Animal corelatedAnimal = animalService.addAnimal(request.getAnimal());
            catService.add(corelatedAnimal, cat);
            return new ResponseEntity<>(corelatedAnimal.getName() + " successfully added!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            Cat cat = catService.remove(id);
            return new ResponseEntity<>(cat.getAnimal().getName() + " successfully deleted!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Cat other) {
        try {
            Cat cat = catService.updateCat(id, other);
            return new ResponseEntity<>(cat.getAnimal().getName() + " successfully updated!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}

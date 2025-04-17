package com.example.petoasisbackend.Controller.Descriptor;


import com.example.petoasisbackend.Request.Badge.BadgeAddRequest;
import com.example.petoasisbackend.Request.Badge.BadgeAnimalRequest;
import com.example.petoasisbackend.Request.Badge.BadgeUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/badges")
public class BadgeController {
    @Autowired
    private BadgeService badgeService;


    @GetMapping
    public ResponseEntity<Object> get(@RequestParam DataDetailLevel level) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id, @RequestParam DataDetailLevel level) {
        return null;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getByName(@PathVariable String name, @RequestParam DataDetailLevel level) {
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody BadgeAddRequest request) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody BadgeUpdateRequest request) {
        return null;
    }

    @PostMapping("/assign/animal")
    public ResponseEntity<Object> assignToAnimal(@RequestBody BadgeAnimalRequest request) {
        return null;
    }

    @DeleteMapping("/detach/animal")
    public ResponseEntity<Object> detachFromAnimal(@RequestBody BadgeAnimalRequest request) {
        return null;
    }

//    @GetMapping("/getAll")
//    public List<Badge> getAll() {
//        return badgeService.getBadges();
//    }
//
//    @GetMapping("/getAllAssigned")
//    public List<AnimalBadge> getAllAssigned() {
//        return badgeService.getAssignedBadges();
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<String> add(@RequestBody Badge badge) {
//        try {
//            badgeService.addBadge(badge);
//            return new ResponseEntity<>(badge.getBadgeName() + " badge added successfully", HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/assign/{animalId}/{badgeName}")
//    public ResponseEntity<String> assignBadge(@PathVariable Long animalId, @PathVariable String badgeName) {
//        try {
//            badgeService.assignBadge(animalId, badgeName);
//            return new ResponseEntity<>("Successfully assigned " + badgeName + " to animal with id " + animalId, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/cancel/{animalId}/{badgeName}")
//    public ResponseEntity<String> cancelBadge(@PathVariable Long animalId, @PathVariable String badgeName) {
//        try {
//            badgeService.cancelBadge(animalId, badgeName);
//            return new ResponseEntity<>("Successfully cancelled " + badgeName + " from animal with id " + animalId, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @DeleteMapping("/delete/{name}")
//    public ResponseEntity<String> delete(@PathVariable String name) {
//        try {
//            badgeService.deleteBadge(name);
//            return new ResponseEntity<>(name + " badge deleted successfully", HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping("/update/{name}/{newName}")
//    public ResponseEntity<String> update(@PathVariable String name, @PathVariable String newName) {
//        try {
//            Badge badge = badgeService.updateBadgeName(name, newName);
//            return new ResponseEntity<>(name + " badge updated to " + newName, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

}

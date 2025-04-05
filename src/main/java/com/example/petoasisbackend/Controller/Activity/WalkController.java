package com.example.petoasisbackend.Controller.Activity;


import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import com.example.petoasisbackend.Service.WalkService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/walk")
public class WalkController {
    @Autowired
    private WalkService walkService;


    @Operation(summary = "Get ALL walk statuses")
    @GetMapping("/status/getAll")
    public List<WalkStatus> getAllStatuses() {
        return walkService.getWalkStatuses();
    }


    @Operation(summary = "Get ALL walks, irrespective of status")
    @GetMapping("/getAll")
    public List<Walk> getAllWalks() {return walkService.getWalks();}

    @Operation(summary = "Add a new status that a walk can have")
    @PostMapping("/status/add")
    public ResponseEntity<String> addStatus(@RequestBody WalkStatus walkStatus) {
        try {
            walkService.addWalkStatus(walkStatus);
            return new ResponseEntity<>(walkStatus.getStatus() + " walk status successfully added!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Add a new walk")
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Walk walk) {
        try {
            walkService.addWalk(walk);
            return new ResponseEntity<>(" walk successfully added!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Remove status with given name.")
    @DeleteMapping("/status/delete/{name}")
    public ResponseEntity<String> removeStatus(@PathVariable String name) {
        try {
            walkService.deleteWalkStatus(name);
            return new ResponseEntity<>(name + " walk status successfully deleted!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete walk with given id.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeWalk(@PathVariable Long id) {
        try {
            walkService.removeWalk(id);
            return new ResponseEntity<>("walk successfully deleted!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update the name of existing walk status.")
    @PutMapping("/update/status/name/{name}/{newName}")
    public ResponseEntity<String> updateStatusName(@PathVariable String name, @PathVariable String newName) {
        try {
            walkService.updateWalkStatus(name, newName);
            return new ResponseEntity<>(name + " walk status successfully updated to " + newName + "!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Update the status of a walk, eg. in progress, cancelled, finished etc.")
    @PutMapping("/update/status/{walkId}/{status}")
    public ResponseEntity<String> updateStatus(@PathVariable Long walkId, @PathVariable String status) {
        try {
            walkService.updateWalkStatus(walkId, status);
            return new ResponseEntity<>("walk status successfully updated to " + status + "!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

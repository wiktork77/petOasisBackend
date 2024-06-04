package com.example.petoasisbackend.Controller.Statuses;


import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Service.AvailabilityStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status/availability")
public class AvailabilityStatusController {
    @Autowired
    private AvailabilityStatusService availabilityStatusService;

    @GetMapping("/getAll")
    public List<AvailabilityStatus> getAll() {
        return availabilityStatusService.getAvailabilityStatuses();
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody AvailabilityStatus status) {
        try {
            availabilityStatusService.addAvailabilityStatus(status);
            return new ResponseEntity<>(status.getAvailability() + " availability status added successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> delete(@PathVariable String name) {
        try {
            AvailabilityStatus status = availabilityStatusService.removeAvailabilityStatus(name);
            return new ResponseEntity<>(name + " availability status deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Foreign key violation - there are animals with this availability status", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{name}/{newName}")
    public ResponseEntity<String> update(@PathVariable String name, @PathVariable String newName) {
        try {
            AvailabilityStatus status = availabilityStatusService.updateAvailabilityStatus(name, newName);
            return new ResponseEntity<>(name + " availability status updated to " + newName, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

package com.example.petoasisbackend.Controller.Statuses;


import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import com.example.petoasisbackend.Service.HealthStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status/health")
public class HealthStatusController {
    @Autowired
    private HealthStatusService healthStatusService;

    @GetMapping("/getAll")
    public List<HealthStatus> getAll() {
        return healthStatusService.getHealthStatuses();
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody HealthStatus status) {
        try {
            healthStatusService.addHealthStatus(status);
            return new ResponseEntity<>(status.getHealthStatus() + " health status added successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> delete(@PathVariable String name) {
        try {
            HealthStatus status = healthStatusService.removeHealthStatus(name);
            return new ResponseEntity<>(name + " health status deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{name}/{newName}")
    public ResponseEntity<String> update(@PathVariable String name, @PathVariable String newName) {
        try {
            HealthStatus status = healthStatusService.updateAvailabilityStatus(name, newName);
            return new ResponseEntity<>(name + " health status updated to " + newName, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}

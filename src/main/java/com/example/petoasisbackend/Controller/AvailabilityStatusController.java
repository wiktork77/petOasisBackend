package com.example.petoasisbackend.Controller;


import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Service.AvailabilityStatusService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<HttpStatus> add(@RequestBody AvailabilityStatus status) {
        availabilityStatusService.addAvailabilityStatus(status);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}

package com.example.petoasisbackend.Controller.Statuses;


import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.AvailabilityStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all availability statuses with given detail level")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned list of all availability statuses", content = @Content(
               mediaType = "application/json",
               schema = @Schema(implementation = AvailabilityStatus.class)
            )),
            @ApiResponse(responseCode = "404", description = "Status not found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam DataDetailLevel level) {
        // TODO: adjust the service method to use level
        Object statuses = availabilityStatusService.getAvailabilityStatuses();
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    @Operation(summary = "Get availability status with given id and detail level")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned availability status", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityStatus.class)
            )),
            @ApiResponse(responseCode = "404", description = "Status not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        // TODO: implement getting by id
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Operation(summary = "Get availability status with given name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned availability status", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityStatus.class)
            )),
            @ApiResponse(responseCode = "404", description = "Status not found", content = @Content)
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getByName(@RequestParam String name) {
        // TODO: implement getting by name
        return new ResponseEntity<>("", HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody AvailabilityStatus status) {
        // Todo: rework this method to satisfy new scheme
        try {
            availabilityStatusService.addAvailabilityStatus(status);
            return new ResponseEntity<>(status.getAvailability() + " availability status added successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> delete(@PathVariable String name) {
        // Todo: rework this method to satisfy new scheme
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
        // Todo: rework this method to satisfy new scheme
        try {
            AvailabilityStatus status = availabilityStatusService.updateAvailabilityStatus(name, newName);
            return new ResponseEntity<>(name + " availability status updated to " + newName, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

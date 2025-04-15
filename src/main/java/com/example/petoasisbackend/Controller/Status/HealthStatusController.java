package com.example.petoasisbackend.Controller.Status;


import com.example.petoasisbackend.DTO.Status.HealthStatus.HealthStatusMinimumDTO;
import com.example.petoasisbackend.DTO.Status.HealthStatus.HealthStatusVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusCannotBeModifiedException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusAlreadyExistsException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusCannotBeModifiedException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusDoesntExistException;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.Status.HealthStatus.HealthStatusAddRequest;
import com.example.petoasisbackend.Request.Status.HealthStatus.HealthStatusUpdateRequest;
import com.example.petoasisbackend.Service.HealthStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status/health")
public class HealthStatusController {
    @Autowired
    private HealthStatusService healthStatusService;


    @Operation(summary = "Get all health statuses with given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned list of all health statuses", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = HealthStatus.class))
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/")
    public ResponseEntity<Object> getAll(DataDetailLevel level) {
        List<ModelDTO<HealthStatus>> statuses = healthStatusService.getHealthStatuses(level);
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }


    @Operation(summary = "Get health status with given id and given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned health status", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = HealthStatus.class))
                    )),
                    @ApiResponse(responseCode = "404", description = "Status not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get health status with id '5' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id, DataDetailLevel level) {
        try {
            ModelDTO<HealthStatus> status = healthStatusService.getHealthStatusById(id, level);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (HealthStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Get health status with given name")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned health status", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = HealthStatus.class))
                    )),
                    @ApiResponse(responseCode = "404", description = "Status not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get health status with name 'super healthy' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getByName(@PathVariable String name) {
        try {
            HealthStatusVerboseDTO status = healthStatusService.getHealthStatusByName(name);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (HealthStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new health status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully created a new status", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HealthStatusMinimumDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{ \"healthStatus\": \"must not be blank\" }"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Status with given name already exists", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot add health status 'not sick' because it already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody @Valid HealthStatusAddRequest request) {
        try {
            HealthStatusMinimumDTO newStatus = healthStatusService.addHealthStatus(request);
            return new ResponseEntity<>(newStatus, HttpStatus.CREATED);
        } catch (HealthStatusAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Delete an existing health status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Status successfully deleted", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "400", description = "Status couldn't be deleted because it would violate data integrity", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete health status referenced by an animal"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "403", description = "Couldn't delete status because it's predefined, core status", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete core status"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Status not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete health status with id '64' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            healthStatusService.removeHealthStatus(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Cannot delete health status because it is still referenced by an animal", HttpStatus.BAD_REQUEST);
        } catch (HealthStatusCannotBeModifiedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (HealthStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Update an existing health status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated a health status", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HealthStatus.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{ \"status\": \"must not be blank\" }"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "403", description = "Cannot modify core status", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot modify core status"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Status not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update health status with id '72' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Status with given name already exists", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update health status with id '4' to 'low healthy' because 'low healthy' already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody @Valid HealthStatusUpdateRequest request) {
        try {
            HealthStatusVerboseDTO status = healthStatusService.updateHealthStatus(id, request);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (AvailabilityStatusCannotBeModifiedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (HealthStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (HealthStatusAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


}

package com.example.petoasisbackend.Controller.Statuses;


import com.example.petoasisbackend.DTO.HealthStatus.HealthStatusMinimumDTO;
import com.example.petoasisbackend.DTO.HealthStatus.HealthStatusNameDTO;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusAlreadyExistsException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusCannotBeModifiedException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusDoesntExistException;
import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.HealthStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("/get")
    public Object getAll(DataDetailLevel level) {
        return healthStatusService.getHealthStatuses(level);
    }


    @Operation(summary = "Add a new health status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully created a new status", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HealthStatusMinimumDTO.class)
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
    public ResponseEntity<Object> add(@RequestBody HealthStatusNameDTO status) {
        try {
            HealthStatus newStatus = new HealthStatus(status.getHealthStatus());
            healthStatusService.addHealthStatus(newStatus);
            return new ResponseEntity<>(HealthStatusMinimumDTO.fromHealthStatus(newStatus), HttpStatus.CREATED);
        } catch (HealthStatusAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Delete an existing health status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "400", description = "", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete health status because it is still referenced by an animal"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "403", description = "", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = ""
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = ""
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "", content = @Content),
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            healthStatusService.removeHealthStatus(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Cannot delete health status because it is still referenced by an animal", HttpStatus.BAD_REQUEST);
        }
        catch (HealthStatusCannotBeModifiedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (HealthStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    // TODO: na nowo
    @Operation(summary = "Update an existing health status")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody HealthStatusNameDTO statusNameDTO) {
        try {
            // TODO:
            System.out.println("toto");
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}

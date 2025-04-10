package com.example.petoasisbackend.Controller.Statuses;


import com.example.petoasisbackend.DTO.Descriptor.AvailabilityStatus.AvailabilityStatusMinimumDTO;
import com.example.petoasisbackend.DTO.Descriptor.AvailabilityStatus.AvailabilityStatusNameDTO;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusAlreadyExistsException;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusCannotBeModifiedException;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusDoesntExistException;
import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.AvailabilityStatusService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/status/availability")
public class AvailabilityStatusController {
    @Autowired
    private AvailabilityStatusService availabilityStatusService;

    @Operation(summary = "Get all availability statuses with given detail level")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned list of all availability statuses with given detail level", content = @Content(
               mediaType = "application/json",
               schema = @Schema(implementation = AvailabilityStatus.class)
            )),
            @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam DataDetailLevel level) {
        Object statuses = availabilityStatusService.getAvailabilityStatuses(level);
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    @Operation(summary = "Get availability status with given id and detail level")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned availability status", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityStatus.class)
            )),
            @ApiResponse(responseCode = "404", description = "Status not found", content = @Content(
                    mediaType = "text/plain",
                    examples = {
                            @ExampleObject(
                                    value = "Cannot get availability status with id '91' because it doesn't exist"
                            )
                    }
            )),
            @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id, @RequestParam DataDetailLevel level) {
        try {
            Object status = availabilityStatusService.getAvailabilityStatusById(id, level);
            return new ResponseEntity<>(status, HttpStatus.OK);
        }  catch (AvailabilityStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get availability status with given name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned availability status", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityStatus.class)
            )),
            @ApiResponse(responseCode = "404", description = "Status not found", content = @Content(
                    examples = {
                            @ExampleObject(
                                    value = "Cannot get availability status with name 'ntava' because it doesn't exist"
                            )
                    }
            )),
            @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getByName(@RequestParam String name) {
        try {
            AvailabilityStatus status = availabilityStatusService.getAvailabilityStatusByName(name);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (AvailabilityStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Add add a new availability status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully added a new status", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AvailabilityStatusMinimumDTO.class)
                    )),
                    @ApiResponse(responseCode = "409", description = "Couldn't add a new status, name conflict", content = @Content(
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot add availability status with name 'mystatus' because it already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody AvailabilityStatusNameDTO nameDTO) {
        try {
            AvailabilityStatusMinimumDTO response = availabilityStatusService.addAvailabilityStatus(nameDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AvailabilityStatusAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Delete existing availability status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted",content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "400", description = "Cannot delete availability status referenced by an animal",content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete health status because it is still referenced by an animal"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "403", description = "Cannot modify core status", content = @Content(
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
                                            value = "Cannot delete availability status with id '122' because it doesnt exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            availabilityStatusService.removeAvailabilityStatus(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Cannot delete availability status because it is still referenced by an animal", HttpStatus.BAD_REQUEST);
        } catch (AvailabilityStatusCannotBeModifiedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (AvailabilityStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Update existing availability status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AvailabilityStatus.class)
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
                                            value = "Cannot update availability status with id '221' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update availability status with id '221' to 'abb' because '"
                                                    +  "abb' already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete availability status with id '122' because it doesnt exist"
                                    )
                            }
                    ))
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody AvailabilityStatusNameDTO statusNameDTO) {
        try {
            AvailabilityStatus status = availabilityStatusService.updateAvailabilityStatus(id, statusNameDTO);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (AvailabilityStatusCannotBeModifiedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (AvailabilityStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (AvailabilityStatusAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}

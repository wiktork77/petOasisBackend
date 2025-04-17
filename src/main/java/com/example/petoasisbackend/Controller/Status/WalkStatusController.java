package com.example.petoasisbackend.Controller.Status;

import com.example.petoasisbackend.DTO.Status.WalkStatus.WalkStatusMinimumDTO;
import com.example.petoasisbackend.DTO.Status.WalkStatus.WalkStatusVerboseDTO;
import com.example.petoasisbackend.Exception.WalkStatus.*;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.Status.WalkStatus.WalkStatusAddRequest;
import com.example.petoasisbackend.Request.Status.WalkStatus.WalkStatusUpdateRequest;
import com.example.petoasisbackend.Service.WalkStatusService;
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

@RestController
@RequestMapping("/statuses/walk")
public class WalkStatusController {

    @Autowired
    private WalkStatusService walkStatusService;


    @Operation(summary = "Get all walk statuses with given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned list of all walk statuses",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = WalkStatus.class))
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<Object> getAllStatuses(DataDetailLevel level) {
        Object statuses = walkStatusService.getWalkStatuses(level);
        return ResponseEntity.ok(statuses);
    }


    @Operation(summary = "Get a walk status with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned walk status with given id",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = WalkStatus.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Couldn't find walk status with given id", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get status because walk status with id '11' doesnt exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getWalkStatus(@PathVariable Integer id, @RequestParam DataDetailLevel level) {
        try {
            Object status = walkStatusService.getWalkStatusById(id, level);
            return ResponseEntity.ok(status);
        } catch (WalkStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



    @Operation(summary = "Get a walk status with given name")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned walk status with given id",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = WalkStatus.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Couldn't find walk status with given name", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get status because walk status with name 'effoc' doesnt exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getWalkStatusName(@PathVariable String name) {
        try {
            Object status = walkStatusService.getWalkStatusByName(name);
            return ResponseEntity.ok(status);
        } catch (WalkStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



    @Operation(summary = "Add a new status that a walk can have")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully created a new status", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WalkStatusMinimumDTO.class)
                    )),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid or incomplete add request",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"status\": \"must not be blank\" }"
                                            ),
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "409", description = "Status with given name already exists", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot add new status because 'xyz' walk status already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Object> addStatus(@RequestBody @Valid WalkStatusAddRequest request) {
        try {
            WalkStatusMinimumDTO status = walkStatusService.addWalkStatus(request);
            return new ResponseEntity<>(status, HttpStatus.CREATED);
        } catch (WalkStatusAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Remove a status with given name")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "400", description = "Cannot delete walk status referenced by walk", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete walk status because it is still referenced by a walk"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "403", description = "Tried to delete core status that's necessary for the system", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete core walk status"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Status with given id not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete status because walk status with id '223' doesnt exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> removeStatus(@PathVariable Integer id) {
        try {
            walkStatusService.deleteWalkStatus(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Cannot delete walk status because it is still referenced by a walk", HttpStatus.BAD_REQUEST);
        } catch (WalkStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (WalkStatusCannotBeModifiedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


    @Operation(summary = "Update existing walk status.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WalkStatus.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{ \"status\": \"must not be blank\" }"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "403", description = "Tried to modify core status that's necessary for the system", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot modify core walk status"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Status name not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update walk status with id '15' because it doesnt exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Status name cannot be updated, conflict detected.",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    value = "Cannot update walk status with id 'id' to 'xuz' because 'xuz' already exists"
                                            )
                                    }
                            )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateStatusName(@PathVariable Integer id, @RequestBody @Valid WalkStatusUpdateRequest request) {
        try {
            System.out.println("hello");
            WalkStatusVerboseDTO status = walkStatusService.updateWalkStatusName(id, request);
            return ResponseEntity.ok(status);
        } catch (WalkStatusUpdateCollisionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (WalkStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (WalkStatusCannotBeModifiedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}

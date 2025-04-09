package com.example.petoasisbackend.Controller.Statuses;

import com.example.petoasisbackend.DTO.Descriptior.WalkStatus.WalkStatusNameDTO;
import com.example.petoasisbackend.DTO.Descriptior.WalkStatus.WalkStatusMinimumDTO;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusAlreadyExistsException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusCannotBeModifiedException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusDoesntExistException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusUpdateCollisionException;
import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.WalkStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status/walk")
public class WalkStatusController {

    @Autowired
    private WalkStatusService walkStatusService;


    @Operation(summary = "Get all walk statuses")
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
    @GetMapping("/getAll")
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
    @GetMapping("/get/{id}")
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
    @GetMapping("/get/by-name/{name}")
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
    public ResponseEntity<Object> addStatus(@RequestBody WalkStatusNameDTO walkStatus) {
        try {
            WalkStatus status = walkStatusService.addWalkStatus(walkStatus);
            WalkStatusMinimumDTO displayMinimumDTO = WalkStatusMinimumDTO.fromWalkStatus(status);
            return new ResponseEntity<>(displayMinimumDTO, HttpStatus.CREATED);
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
    public ResponseEntity<Object> updateStatusName(@PathVariable Integer id, @RequestBody WalkStatusNameDTO updated) {
        try {
            System.out.println("hello");
            WalkStatus status = walkStatusService.updateWalkStatusName(id, updated);
            return ResponseEntity.ok(status);
            // return new ResponseEntity<>("'" + name + "' walk status successfully updated to '" + newName + "'", HttpStatus.OK);
        } catch (WalkStatusUpdateCollisionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (WalkStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (WalkStatusCannotBeModifiedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}

package com.example.petoasisbackend.Controller.Statuses;

import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusAlreadyExistsException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusDoesntExistException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusUpdateCollisionException;
import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
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

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/status/walk")
public class WalkStatusController {

    @Autowired
    private WalkStatusService walkStatusService;


    @Operation(summary = "Get ALL walk statuses")
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
    public List<WalkStatus> getAllStatuses() {
        return walkStatusService.getWalkStatuses();
    }


    @Operation(summary = "Add a new status that a walk can have")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully created a new status", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WalkStatus.class)
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
    public ResponseEntity<Object> addStatus(@RequestBody WalkStatus walkStatus) {
        try {
            WalkStatus status = walkStatusService.addWalkStatus(walkStatus);
            return new ResponseEntity<>(status, HttpStatus.CREATED);
        } catch (WalkStatusAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Remove status with given name.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "404", description = "Status not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete status because 'statusName' walk status doesnt exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
            }
    )
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Object> removeStatus(@PathVariable String name) {
        try {
            walkStatusService.deleteWalkStatus(name);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (WalkStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Update the name of existing walk status.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WalkStatus.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Status name not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update walk status 'xyz' because it doesnt exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Status name cannot be updated, conflict detected.",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    value = "Cannot update walk status 'xyz' to 'In progress' because 'In progress' already exists"
                                            )
                                    }
                            )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @PutMapping("/update/{name}/{newName}")
    public ResponseEntity<Object> updateStatusName(@PathVariable String name, @PathVariable String newName) {
        try {
            WalkStatus status = walkStatusService.updateWalkStatusName(name, newName);
            return ResponseEntity.ok(status);
            // return new ResponseEntity<>("'" + name + "' walk status successfully updated to '" + newName + "'", HttpStatus.OK);
        } catch (WalkStatusUpdateCollisionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (WalkStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.example.petoasisbackend.Controller.Activity;


import com.example.petoasisbackend.DTO.Activity.Walk.WalkMinimumDTO;
import com.example.petoasisbackend.DTO.Activity.Walk.WalkChangeStatusDTO;
import com.example.petoasisbackend.DTO.Activity.Walk.WalkVerboseDTO;
import com.example.petoasisbackend.DTO.Activity.Walk.WalkWithTimeDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Exception.Animal.AnimalNotAvailableException;
import com.example.petoasisbackend.Exception.Person.PersonDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Exception.Walk.*;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusDoesntExistException;
import com.example.petoasisbackend.Mapper.Activity.Walk.WalkFilterType;
import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Request.Activity.Walk.WalkTimeUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.Activity.Walk.WalkAddRequest;
import com.example.petoasisbackend.Request.Status.WalkStatus.WalkStatusUpdateRequest;
import com.example.petoasisbackend.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/walks")
public class WalkController {
    @Autowired
    private WalkService walkService;

    @Operation(summary = "Get all walks with given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned list of all walks",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Walk.class))
                    )
                    ),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<Object> getAllWalks(@RequestParam DataDetailLevel level) {
        List<ModelDTO<Walk>> walks = walkService.get(level);
        return new ResponseEntity<>(walks, HttpStatus.OK);
    }


    @Operation(summary = "Get a walk with given id and detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned walk",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = WalkVerboseDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Walk with given id not found",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    value = "Cannot get walk with id '2' because it doesn't exist"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getWalk(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<Walk> walk = walkService.getById(id, level);
            return new ResponseEntity<>(walk, HttpStatus.OK);
        } catch (WalkDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all walks of an animal with given id, data detail level and filter type")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned animal's walks",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = WalkVerboseDTO.class))
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Animal with given id not found",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    value = "Cannot get walks of animal with id '82' because it doesn't exist"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/animal/{id}")
    public ResponseEntity<Object> getAnimalWalks(@PathVariable Long id, @RequestParam DataDetailLevel level, @RequestParam WalkFilterType filterType) {
        try {
            List<ModelDTO<Walk>> walks = walkService.getByAnimal(id, level, filterType);
            return new ResponseEntity<>(walks, HttpStatus.OK);
        } catch (AnimalDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



    @Operation(summary = "Get all walks of a person with given id, data detail level and filter type")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned person's walks",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = WalkVerboseDTO.class))
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Person with given id not found",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    value = "Cannot get walks of person with id '82' because it doesn't exist"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/person/{id}")
    public ResponseEntity<Object> getPersonWalks(@PathVariable Long id, @RequestParam DataDetailLevel level, @RequestParam WalkFilterType filterType) {
        try {
            List<ModelDTO<Walk>> walks = walkService.getByPerson(id, level, filterType);
            return new ResponseEntity<>(walks, HttpStatus.OK);
        } catch (PersonDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Add a new walk")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully created", content = @Content(
                            mediaType = "application/json"
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid request data",
                                            value = "{\n" +
                                                    "  \"animalId\": \"must not be null\",\n" +
                                                    "  \"personId\": \"must not be null\",\n" +
                                                    "  \"shelterId\": \"must not be null\",\n" +
                                                    "  \"startTime\": \"must not be null\",\n" +
                                                    "  \"endTime\": \"must not be null\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid start time value",
                                            value = "{\n" +
                                                    "  \"startTime\": \"must be in future\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid time interval",
                                            value = "{\n" +
                                                    "  \"general\": \"endTime must be after startTime\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid date format",
                                            value = "{\n" +
                                                    "  \"startTime\": \"must be in zulu time format\",\n" +
                                                    "  \"endTime\": \"must be in zulu time format\"\n" +
                                                    "}"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "403", description = "Animal not available", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot start the walk because the animal is not available"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "One of key components not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "Animal not found",
                                            value = "Cannot get animal with id '42' because it doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Shelter not found",
                                            value = "Cannot get person with id '112' because it doesn't exist!"
                                    ),
                                    @ExampleObject(
                                            name = "Person not found",
                                            value = "Cannot get shelter with id '19' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Walk time conflicts with other walk", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "Animal busy during given period",
                                            value = "Cannot add walk because the animal has another activity that intersects with given time period"
                                    ),
                                    @ExampleObject(
                                            name = "Person busy during given period",
                                            value = "Cannot add walk because the person has another activity that intersects with given time period"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid WalkAddRequest request) {
        try {
            WalkMinimumDTO response = walkService.add(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AnimalNotAvailableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (AnimalDoesntExistException | ShelterDoesntExistException | PersonDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (WalkTimeIntersectException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Delete a walk with given id.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "403", description = "Couldn't delete the walk, it doesn't have correct walk status to be deleted. Only walk with statuses 'Finished' and 'Cancelled' can be deleted.", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete walk with id '2'. Can only delete walks with statuses 'Finished' and 'Cancelled'. Consider changing the status first"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Walk with given id doesn't exist.", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete walk with id '52' because it doesn't exist."
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeWalk(@PathVariable Long id) {
        try {
            walkService.deleteWalk(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (WalkCannotBeDeletedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }  catch (WalkDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(description = "This can be also used to start or finish a walk. \n Starting a walk changes associated animal's availability status and sets actualStartTime attribute in the walk to now. Finishing the walk sets associtated animal's availability status to Available and sets actualEndTime in the walk.", summary = "Update the status of a walk, eg. in progress, cancelled, finished etc." )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WalkChangeStatusDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{ \"status\": \"must not be blank\" }"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "403", description = "Invalid walk status change", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Status change request not allowed. Possible core changes: Pending->In progress or Cancelled, In Progress -> Finished"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Status or walk not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "Status doesn't exist",
                                            value = "Cannot update walk status of walk with id '21' because status 'xxx' doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Walk doesn't exist",
                                            value = "Cannot update walk status of walk with id '91' because walk with this id doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PatchMapping("/{id}/status")
    public ResponseEntity<Object> updateWalkStatus(@PathVariable Long id, @RequestBody @Valid WalkStatusUpdateRequest request) {
        try {
            WalkChangeStatusDTO response = walkService.updateStatus(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (WalkInvalidStatusChangeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (WalkDoesntExistException | WalkStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Change the time period of a particular walk with 'Pending' status.", description = "The time change is allowed only for walks with pending status.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated time period", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WalkWithTimeDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid time period", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "invalid period",
                                            value = "{\n" +
                                                    "  \"general\": \"endTime must be after startTime\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid request data",
                                            value = "{\n" +
                                                    "  \"start\": \"must not be blank\",\n" +
                                                    "  \"finish\": \"must not be blank\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid date format",
                                            value = "{\n" +
                                                    "  \"startTime\": \"must be in zulu time format\",\n" +
                                                    "  \"endTime\": \"must be in zulu time format\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid start time value",
                                            value = "{\n" +
                                                    "  \"startTime\": \"must be in future\"\n" +
                                                    "}"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "403", description = "Walk with this status cannot have time modified", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update period of walk with id '21' because the status is different from 'Pending'"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Walk not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update period of walk with id '123' because walk with this id doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Walk time conflicts with other walk", content = @Content(
                       mediaType = "text/plain",
                       examples = {
                               @ExampleObject(
                                       name = "Animal busy during given period",
                                       value = "Cannot update walk time because the animal has another activity that intersects with given time period"
                               ),
                               @ExampleObject(
                                       name = "Person busy during given period",
                                       value = "Cannot update walk time because the caretaker has another activity that intersects with given time period"
                               )
                       }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PatchMapping("/{id}/time")
    public ResponseEntity<Object> updateWalkTimes(@PathVariable Long id, @RequestBody @Valid WalkTimeUpdateRequest request) {
        try {
            WalkWithTimeDTO response = walkService.updateTime(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (WalkCannotBeModifiedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (WalkDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (WalkTimeIntersectException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}

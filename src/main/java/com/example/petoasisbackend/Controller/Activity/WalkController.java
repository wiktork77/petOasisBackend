package com.example.petoasisbackend.Controller.Activity;


import com.example.petoasisbackend.DTO.Activity.Walk.WalkAddDTO;
import com.example.petoasisbackend.DTO.Activity.Walk.WalkMinimumDTO;
import com.example.petoasisbackend.DTO.Descriptior.WalkStatus.WalkStatusNameDTO;
import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Exception.Person.PersonDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Exception.Walk.WalkCannotBeDeletedException;
import com.example.petoasisbackend.Exception.Walk.WalkDoesntExistException;
import com.example.petoasisbackend.Exception.Walk.WalkTimeIntersectException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusDoesntExistException;
import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.*;
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
@RequestMapping("/walk")
public class WalkController {
    @Autowired
    private WalkService walkService;
    @Autowired
    private WalkStatusService walkStatusService;
    @Autowired
    private ShelterService shelterService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private PersonService personService;

    @Operation(summary = "Get all walks with details depending on given level")
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
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllWalks(@RequestParam DataDetailLevel level) {
        return new ResponseEntity<>(walkService.getWalks(level), HttpStatus.OK);
    }


    @Operation(summary = "Get a walk with given id, with details depending on the given level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned walk",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Walk.class))
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Walk with given id not found",
                            content = @Content(
                                    mediaType = "plain/text",
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
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getWalk(@PathVariable Long id, DataDetailLevel level) {
        try {
            return new ResponseEntity<>(walkService.getWalkById(id, level), HttpStatus.OK);
        } catch (WalkDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Add a new walk")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully added a new walk", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WalkMinimumDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "One of necessary walk parts not found (animal or person or shelter)", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get animal with id '52' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Walk in given time period unavailable, either because time intersects with other walk or the shelter is closed", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot add walk with animal with id '721' because given time intersects with other walk(s)."
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody WalkAddDTO walk) {
        try {
            if (!walkService.checkIfAnimalIsAvailableForWalk(walk.getAnimalId(), walk.getStartTime(), walk.getEndTime())) {
                throw new WalkTimeIntersectException("Cannot add a walk with animal with id '" + walk.getAnimalId() + "' because given time intersects with other walk(s).");
            }

            Animal pupil = animalService.getAnimal(walk.getAnimalId());
            Person caretaker = personService.getPersonById(walk.getPersonId());
            Shelter supervisor = shelterService.getShelterById(walk.getShelterId());


            Walk newWalk = new Walk(
                    pupil,
                    caretaker,
                    supervisor,
                    walk.getStartTime(),
                    walk.getEndTime(),
                    walkStatusService.getWalkStatusByName("Pending")
            );

            Walk addedWalk = walkService.addWalk(newWalk);
            WalkMinimumDTO response = WalkMinimumDTO.fromWalk(addedWalk);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AnimalDoesntExistException | PersonDoesntExistException | ShelterDoesntExistException |
                 WalkStatusDoesntExistException e) {
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
                            mediaType = "plain/text",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete walk with id '2'. Can only delete walks with statuses 'Finished' and 'Cancelled'. Consider changing the status first"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Walk with given id doesn't exist.", content = @Content(
                            mediaType = "plain/text",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete walk with id '52'. It doesn't exist."
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeWalk(@PathVariable Long id) {
        try {
            walkService.removeWalk(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (WalkCannotBeDeletedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (WalkDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Update the status of a walk, eg. in progress, cancelled, finished etc.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Walk with id '4' status updated to 'finished'"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Status or walk not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update walk status of walk with id '21' because status 'xxx' doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PutMapping("/update/status/{walkId}")
    public ResponseEntity<Object> updateWalkStatus(@PathVariable Long walkId, @RequestBody WalkStatusNameDTO newStatus) {
        try {
            Walk walk = walkService.updateWalkStatus(walkId, newStatus.getStatus());
            return new ResponseEntity<>(walk, HttpStatus.OK);
        } catch (WalkStatusDoesntExistException | WalkDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}

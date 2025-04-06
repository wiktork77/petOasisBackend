package com.example.petoasisbackend.Controller.Activity;


import com.example.petoasisbackend.DTO.Activity.WalkAddDTO;
import com.example.petoasisbackend.Exception.Walk.WalkDoesntExistException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusDoesntExistException;
import com.example.petoasisbackend.Exception.WalkStatus.WalkStatusUpdateCollisionException;
import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Service.AnimalService;
import com.example.petoasisbackend.Service.PersonService;
import com.example.petoasisbackend.Service.ShelterService;
import com.example.petoasisbackend.Service.WalkService;
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

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RestController
@RequestMapping("/walk")
public class WalkController {
    @Autowired
    private WalkService walkService;
    @Autowired
    private ShelterService shelterService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private PersonService personService;

    @Operation(summary = "Get ALL walks, irrespective of status")
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
    public List<Walk> getAllWalks() {return walkService.getWalks();}


    @Operation(summary = "Add a new walk")
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody WalkAddDTO walk) {
        try {
            Animal pupil = animalService.getAnimal(walk.getAnimalId());
            Person caretaker = personService.getPersonById(walk.getPersonId());
            Shelter supervisor = shelterService.getShelterById(walk.getShelterId());

            Walk newWalk = new Walk(
                    pupil,
                    caretaker,
                    supervisor,
                    walk.getStartTime(),
                    walk.getEndTime()
            );

            walkService.addWalk(newWalk);

            return new ResponseEntity<>(" walk successfully added!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Delete walk with given id.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeWalk(@PathVariable Long id) {
        try {
            walkService.removeWalk(id);
            return new ResponseEntity<>("walk successfully deleted!", HttpStatus.OK);
        } catch (WalkDoesntExistException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
    @PutMapping("/update/status/{walkId}/{status}")
    public ResponseEntity<String> updateWalkStatus(@PathVariable Long walkId, @PathVariable String status) {
        try {
            walkService.updateWalkStatus(walkId, status);
            return new ResponseEntity<>("Walk with id '" + walkId + "' status updated to " + status, HttpStatus.OK);
        } catch (WalkStatusDoesntExistException | WalkDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

package com.example.petoasisbackend.Controller.User;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUMinimumDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUProfilePictureDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUUpdateDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUVerificationDTO;
import com.example.petoasisbackend.Exception.GSU.UserAlreadyExistsException;
import com.example.petoasisbackend.Exception.GSU.UserDoesntExistException;
import com.example.petoasisbackend.Exception.GSU.UserPasswordDoesntMatch;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.GSU.GSUUpdateRequest;
import com.example.petoasisbackend.Request.GSU.PasswordChangeRequest;
import com.example.petoasisbackend.Service.GSUService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gsu")
public class GSUController {
    @Autowired
    private GSUService gsuService;

    @Operation(summary = "Get all users with given data details")
    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam DataDetailLevel level) {
        List<ModelDTO<GeneralSystemUser>> users = gsuService.getAll(level);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get a user with given id and data details")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getGSUById(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<GeneralSystemUser> user = gsuService.getById(id, level);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/{id}/matches/{password}")
//    public ResponseEntity<Object> matches(@PathVariable Long id, @PathVariable String password) {
//        return new ResponseEntity<>(gsuService.matchesPassword(id, password), HttpStatus.OK);
//    }

    @Operation(summary = "Verify a user with given id")
    @PatchMapping("/verify/{id}")
    public ResponseEntity<Object> verifyUser(@PathVariable Long id) {
        try {
            GSUVerificationDTO user = gsuService.verify(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update the profile picture of a user with given id")
    @PatchMapping("/{id}/profile-picture/{url}")
    public ResponseEntity<Object> uploadProfilePicture(@PathVariable Long id, @PathVariable String url) {
        try {
            GSUProfilePictureDTO user = gsuService.changeProfilePicture(id, url);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update user related data")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid GSUUpdateRequest request) {
        try {
            GSUUpdateDTO user = gsuService.update(id, request);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Change the password of a user with given id")
    @PatchMapping("/{id}/password/")
    public ResponseEntity<Object> changePassword(@PathVariable Long id, @RequestBody @Valid PasswordChangeRequest request) {
        try {
            GSUMinimumDTO response = gsuService.changePassword(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserPasswordDoesntMatch e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

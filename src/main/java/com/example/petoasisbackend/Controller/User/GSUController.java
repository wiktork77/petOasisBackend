package com.example.petoasisbackend.Controller.User;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUUpdateDTO;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.GSU.PasswordChangeRequest;
import com.example.petoasisbackend.Service.GSUService;
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

    @GetMapping("/")
    public List<ModelDTO<GeneralSystemUser>> getAll(@RequestParam DataDetailLevel level) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGSUById(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        return null;
    }

    @PatchMapping("/verify/{id}")
    public ResponseEntity<Object> verifyUser(@PathVariable Long id) {
        return null;
    }

    @PatchMapping("/{id}/profile-picture/{url}")
    public ResponseEntity<Object> uploadProfilePicture(@PathVariable Long id, @PathVariable String url) {
        return null;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody GSUUpdateDTO request) {
        return null;
    }

    @PatchMapping("/{id}/password/")
    public ResponseEntity<Object> changePassword(@PathVariable Long id, @RequestBody PasswordChangeRequest request) {
        return null;
    }

}

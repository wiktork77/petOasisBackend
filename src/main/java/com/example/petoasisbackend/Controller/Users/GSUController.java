package com.example.petoasisbackend.Controller.Users;


import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Service.GSUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gsu")
public class GSUController {
    @Autowired
    private GSUService gsuService;

    @GetMapping("/getAll")
    public List<GeneralSystemUser> getAll() {
        return gsuService.getGSU();
    }

    @GetMapping("/get/{login}")
    public GeneralSystemUser getGSU(@PathVariable String login) {
        return gsuService.getGSUByLogin(login);
    }
}

package com.example.petoasisbackend.Controller.Users;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.GSUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gsu")
public class GSUController {
    @Autowired
    private GSUService gsuService;

    @GetMapping("/getAll")
    public List<ModelDTO<GeneralSystemUser>> getAll(@RequestParam DataDetailLevel level) {
        return gsuService.getGSU(level);
    }

    @GetMapping("/get/{login}")
    public GeneralSystemUser getGSU(@PathVariable String login) {
        return gsuService.getGSUByLogin(login);
    }
}

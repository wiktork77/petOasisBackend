package com.example.petoasisbackend.Controller;


import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import com.example.petoasisbackend.Service.HealthStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/status/health")
public class HealthStatusController {
    @Autowired
    private HealthStatusService healthStatusService;

    @GetMapping("/getAll")
    public List<HealthStatus> getAll() {
        return healthStatusService.getHealthStatuses();
    }
}

package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUMinimumDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUVerboseDTO;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Repository.SystemUserRepository;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GSUService {
    @Autowired
    private SystemUserRepository systemUserRepository;

    public List<ModelDTO<GeneralSystemUser>> getGSU(DataDetailLevel level) {
        // TODO: level
        List<GeneralSystemUser> users = systemUserRepository.findAll();
        switch (level) {
            case MINIMUM -> {
                return users.stream().map(GSUMinimumDTO::fromGSU).collect(Collectors.toList());
            }
            default -> {
                return users.stream().map(GSUVerboseDTO::fromGSU).collect(Collectors.toList());
            }
        }
    }

//    public GeneralSystemUser verify(Long id) {
//
//    }

    public GeneralSystemUser getGSUByLogin(String login) {
        return systemUserRepository.getGeneralSystemUserByLogin(login);
    }
}

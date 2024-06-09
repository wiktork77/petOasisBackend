package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Repository.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GSUService {
    @Autowired
    private SystemUserRepository systemUserRepository;

    public List<GeneralSystemUser> getGSU() {
        return systemUserRepository.findAll();
    }

    public GeneralSystemUser getGSUByLogin(String login) {
        return systemUserRepository.getGeneralSystemUserByLogin(login);
    }
}

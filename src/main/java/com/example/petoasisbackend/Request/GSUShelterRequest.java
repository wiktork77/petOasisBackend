package com.example.petoasisbackend.Request;

import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import lombok.Getter;


@Getter
public class GSUShelterRequest {
    private GeneralSystemUser generalSystemUser;
    private Shelter shelter;
}

package com.example.petoasisbackend.Request;

import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Person;
import lombok.Getter;


@Getter
public class GSUPersonRequest {
    private GeneralSystemUser generalSystemUser;
    private Person person;
}

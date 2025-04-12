package com.example.petoasisbackend.DTO.User.Shelter;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.Shelter;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@ToString
public class ShelterRegisterDTO implements ModelDTO<Shelter> {
    private String login;
    private String password;
    private String phoneNumber;
    private String pictureUrl;
    private String name;
    private String address;
    private String website;
    private String email;
}

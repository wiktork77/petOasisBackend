package com.example.petoasisbackend.DTO.User.Shelter;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ShelterRegisterDTO {
    private String login;
    private String password;
    private String phoneNumber;
    private String pictureUrl;
    private String name;
    private String address;
    private String website;
    private String email;
}

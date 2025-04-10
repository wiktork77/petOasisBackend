package com.example.petoasisbackend.DTO.User.Shelter;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShelterMediumDTO {
    private Long shelterId;
    private String name;
    private String address;
    private String website;
    private String email;
    private String pictureUrl;
    private String phoneNumber;
    private Float rating;
}

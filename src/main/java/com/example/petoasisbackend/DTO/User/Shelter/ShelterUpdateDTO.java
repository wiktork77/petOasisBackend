package com.example.petoasisbackend.DTO.User.Shelter;

import com.example.petoasisbackend.Model.Users.Shelter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShelterUpdateDTO {
    private Long shelterId;
    private String name;
    private String address;
    private String website;

    public static ShelterUpdateDTO fromShelter(Shelter shelter) {
        return new ShelterUpdateDTO(
                shelter.getShelterId(),
                shelter.getName(),
                shelter.getAddress(),
                shelter.getWebsite()
        );
    }
}

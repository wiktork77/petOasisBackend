package com.example.petoasisbackend.DTO.User.Shelter;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUVerboseDTO;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Shelter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ShelterVerboseDTO implements ModelDTO<Shelter> {
    private Long shelterId;
    private String name;
    private String address;
    private String website;
    private Float rating;

    private GSUVerboseDTO generalSystemUser;

    public static ShelterVerboseDTO fromShelter(Shelter shelter) {
        return new ShelterVerboseDTO(
                shelter.getShelterId(),
                shelter.getName(),
                shelter.getAddress(),
                shelter.getWebsite(),
                shelter.getRating(),
                GSUVerboseDTO.fromGSU(shelter.getGeneralSystemUser())
        );
    }
}

package com.example.petoasisbackend.DTO.User.Shelter;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUConciseDTO;
import com.example.petoasisbackend.Model.Users.Shelter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelterConciseDTO implements ModelDTO<Shelter> {
    private Long shelterId;
    private String name;
    private String address;
    private Float rating;

    private GSUConciseDTO gsu;

    public static ShelterConciseDTO fromShelter(Shelter shelter) {
        return new ShelterConciseDTO(
                shelter.getShelterId(),
                shelter.getName(),
                shelter.getAddress(),
                shelter.getRating(),
                GSUConciseDTO.fromGSU(shelter.getGeneralSystemUser())
        );
    }
}

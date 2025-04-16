package com.example.petoasisbackend.DTO.User.Shelter;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUMediumDTO;
import com.example.petoasisbackend.Model.Users.Shelter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelterMediumDTO implements ModelDTO<Shelter> {
    private Long shelterId;
    private String name;
    private String address;
    private String website;
    private Float rating;

    private GSUMediumDTO gsu;


    public static ShelterMediumDTO fromShelter(Shelter shelter) {
        return new ShelterMediumDTO(
                shelter.getShelterId(),
                shelter.getName(),
                shelter.getAddress(),
                shelter.getWebsite(),
                shelter.getRating(),
                GSUMediumDTO.fromGSU(shelter.getGeneralSystemUser())
        );
    }
}

package com.example.petoasisbackend.DTO.User.Shelter;


import com.example.petoasisbackend.DTO.ModelDTO;
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
    private String email;
    private String pictureUrl;
    private String phoneNumber;
    private Float rating;


    public static ShelterMediumDTO fromShelter(Shelter shelter) {
        return new ShelterMediumDTO(
                shelter.getShelterId(),
                shelter.getName(),
                shelter.getAddress(),
                shelter.getWebsite(),
                shelter.getGeneralSystemUser().getEmail(),
                shelter.getGeneralSystemUser().getPictureUrl(),
                shelter.getGeneralSystemUser().getPhoneNumber(),
                shelter.getRating()
        );
    }
}

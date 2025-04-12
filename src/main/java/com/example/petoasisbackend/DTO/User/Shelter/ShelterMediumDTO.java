package com.example.petoasisbackend.DTO.User.Shelter;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.Shelter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShelterMediumDTO implements ModelDTO<Shelter> {
    private Long shelterId;
    private String name;
    private String address;
    private String website;
    private String email;
    private String pictureUrl;
    private String phoneNumber;
    private Float rating;

    private ShelterMediumDTO(Long shelterId, String name, String address, String website, String email, String pictureUrl, String phoneNumber, Float rating) {
        this.shelterId = shelterId;
        this.name = name;
        this.address = address;
        this.website = website;
        this.email = email;
        this.pictureUrl = pictureUrl;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
    }

    public static ShelterMediumDTO fromShelter(Shelter shelter) {
        return new ShelterMediumDTO(
                shelter.getShelterId(),
                shelter.getName(),
                shelter.getAddress(),
                shelter.getWebsite(),
                shelter.getEmail(),
                shelter.getGeneralSystemUser().getPictureUrl(),
                shelter.getGeneralSystemUser().getPhoneNumber(),
                shelter.getRating()
        );
    }
}

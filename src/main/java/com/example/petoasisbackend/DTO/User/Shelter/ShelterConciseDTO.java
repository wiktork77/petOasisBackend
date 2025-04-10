package com.example.petoasisbackend.DTO.User.Shelter;

import com.example.petoasisbackend.Model.Users.Shelter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShelterConciseDTO {
    private Long shelterId;
    private String name;
    private String address;
    private Float rating;
    private String pictureUrl;

    public ShelterConciseDTO(Long shelterId, String name, String address, Float rating, String pictureUrl) {
        this.shelterId = shelterId;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.pictureUrl = pictureUrl;
    }

    public static ShelterConciseDTO fromShelter(Shelter shelter) {
        return new ShelterConciseDTO(
                shelter.getShelterId(),
                shelter.getName(),
                shelter.getAddress(),
                shelter.getRating(),
                shelter.getGeneralSystemUser().getPictureUrl()
        );
    }
}

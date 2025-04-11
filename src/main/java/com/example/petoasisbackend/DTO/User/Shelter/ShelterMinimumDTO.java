package com.example.petoasisbackend.DTO.User.Shelter;


import com.example.petoasisbackend.Model.Users.Shelter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShelterMinimumDTO {
    private Long shelterId;
    private String name;

    private ShelterMinimumDTO(Long shelterId, String name) {
        this.shelterId = shelterId;
        this.name = name;
    }

    public static ShelterMinimumDTO fromShelter(Shelter shelter) {
        return new ShelterMinimumDTO(
                shelter.getShelterId(),
                shelter.getName()
        );
    }
}

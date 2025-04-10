package com.example.petoasisbackend.DTO.User.Shelter;

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
}

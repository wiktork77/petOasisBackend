package com.example.petoasisbackend.DTO.User.Shelter;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Shelter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
public class ShelterVerboseDTO implements ModelDTO<Shelter> {
    private Long shelterId;
    private GeneralSystemUser generalSystemUser;
    private String name;
    private String address;
    private String website;
    private String email;
    private Float rating;
    private Set<Animal> animals;

    private ShelterVerboseDTO(Long shelterId, GeneralSystemUser generalSystemUser, String name, String address, String website, String email, Float rating, Set<Animal> animals) {
        this.shelterId = shelterId;
        this.generalSystemUser = generalSystemUser;
        this.name = name;
        this.address = address;
        this.website = website;
        this.email = email;
        this.rating = rating;
        this.animals = animals;
    }

    public static ShelterVerboseDTO fromShelter(Shelter shelter) {
        return new ShelterVerboseDTO(
                shelter.getShelterId(),
                shelter.getGeneralSystemUser(),
                shelter.getName(),
                shelter.getAddress(),
                shelter.getWebsite(),
                shelter.getEmail(),
                shelter.getRating(),
                shelter.getAnimals()
        );
    }
}

package com.example.petoasisbackend.Model.Users;

import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Descriptor.AnimalComment;
import com.example.petoasisbackend.Request.Shelter.ShelterAddRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shelterId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "system_user_id")
    private GeneralSystemUser generalSystemUser;

    @Column(length = 96, nullable = false, unique = true)
    private String name;

    @Column(length = 64, nullable = false)
    private String address;

    @Column(length = 128)
    private String website;

    @Column(length = 64, nullable = false)
    private String email;

    private Float rating;

    @OneToMany(mappedBy = "home", cascade = CascadeType.REMOVE)
    private Set<Animal> animals;

    public void inheritFromOtherShelter(Shelter other) {
        if (other.generalSystemUser != null) {
            this.generalSystemUser.inheritFromOtherGeneralSystemUser(other.generalSystemUser);
        }
        if (other.name != null) {
            this.name = other.name;
        }
        if (other.address != null) {
            this.address = other.address;
        }
        this.website = other.website;

        if (other.email != null) {
            this.email = other.email;
        }
        this.rating = other.rating;
    }

    public Shelter(String name, String address, String website, String email, Float rating) {
        this.name = name;
        this.address = address;
        this.website = website;
        this.email = email;
        this.rating = rating;
    }


    public static Shelter fromShelterAddRequest(ShelterAddRequest request) {
        return new Shelter(
                request.getName(),
                request.getAddress(),
                request.getWebsite(),
                request.getEmail(),
                null
        );
    }
}

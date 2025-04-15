package com.example.petoasisbackend.Model.Users;

import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Request.User.Shelter.ShelterAddRequest;
import com.example.petoasisbackend.Request.User.Shelter.ShelterUpdateRequest;
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

    private Float rating;

    @OneToMany(mappedBy = "home", cascade = CascadeType.REMOVE)
    private Set<Animal> animals;


    public Shelter(String name, String address, String website, Float rating) {
        this.name = name;
        this.address = address;
        this.website = website;
        this.rating = rating;
    }


    public static Shelter fromShelterAddRequest(ShelterAddRequest request) {
        return new Shelter(
                request.getName(),
                request.getAddress(),
                request.getWebsite(),
                null
        );
    }

    public void update(ShelterUpdateRequest request) {
        this.name = request.getName();
        this.address = request.getAddress();
        this.website = request.getWebsite();
    }
}

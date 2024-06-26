package com.example.petoasisbackend.Model.Users;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
}

package com.example.petoasisbackend.Model.Users;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "system_user_id")
    private GeneralSystemUser generalSystemUser;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(length = 48, nullable = false)
    private String surname;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(length = 64, nullable = false)
    private String email;

    @Column(length = 1)
    private String gender;

    @Column(length = 64)
    private String address;


    public void inheritFromOtherPerson(Person other) {
        if (other.generalSystemUser != null) {
            this.generalSystemUser.inheritFromOtherGeneralSystemUser(other.generalSystemUser);
        }
        if (other.name != null) {
            this.name = other.name;
        }
        if (other.surname != null) {
            this.surname = other.surname;
        }
        if (other.birthDate != null) {
            this.birthDate = other.birthDate;
        }
        if (other.email != null) {
            this.email = other.email;
        }
        this.gender = other.gender;
        this.address = other.address;
    }

}

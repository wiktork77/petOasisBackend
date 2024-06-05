package com.example.petoasisbackend.Model.Descriptor;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer badgeId;

    @Column(length = 48)
    private String badgeName;
}

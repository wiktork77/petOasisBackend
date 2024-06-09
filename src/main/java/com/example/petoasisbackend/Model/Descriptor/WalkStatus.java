package com.example.petoasisbackend.Model.Descriptor;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class WalkStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;

    @Column(length = 48, nullable = false, unique = true)
    private String status;

}

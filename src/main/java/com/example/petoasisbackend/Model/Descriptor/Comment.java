package com.example.petoasisbackend.Model.Descriptor;


import com.example.petoasisbackend.Model.Users.Person;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;


    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person author;

    @Column(length = 1024, nullable = false)
    private String content;

    private LocalDate date;


    public Comment(){}
    public Comment(String content, LocalDate date) {
        this.content = content;
        this.date = date;
    }

    public void inheritFromOther(Comment other) {
        this.content = other.content;
    }
}

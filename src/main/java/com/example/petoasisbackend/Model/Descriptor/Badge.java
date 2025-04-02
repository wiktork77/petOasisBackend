package com.example.petoasisbackend.Model.Descriptor;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer badgeId;

    @Column(length = 64)
    private String badgeName;

    @JsonIgnore
    @OneToMany(mappedBy = "badge", cascade = CascadeType.REMOVE)
    private Set<AnimalBadge> badgeAnimals;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return Objects.equals(badgeId, badge.badgeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(badgeId);
    }
}

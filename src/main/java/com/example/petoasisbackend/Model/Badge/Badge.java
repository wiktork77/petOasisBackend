package com.example.petoasisbackend.Model.Badge;


import com.example.petoasisbackend.Request.Badge.BadgeAddRequest;
import com.example.petoasisbackend.Request.Badge.BadgeUpdateRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer badgeId;

    @Column(length = 64, unique = true)
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

    public Badge(String badgeName) {
        this.badgeName = badgeName;
    }

    public void update(BadgeUpdateRequest request) {
        this.badgeName = request.getBadgeName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(badgeId);
    }


    public static Badge fromBadgeAddRequest(BadgeAddRequest request) {
        return new Badge(
                request.getBadgeName()
        );
    }
}

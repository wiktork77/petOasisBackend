package com.example.petoasisbackend.DTO.Badge.Badge;

import com.example.petoasisbackend.Model.Badge.Badge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class BadgeUpdateDTO {
    private String badgeName;

    public static BadgeUpdateDTO fromBadge(Badge badge) {
        return new BadgeUpdateDTO(
                badge.getBadgeName()
        );
    }
}

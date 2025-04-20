package com.example.petoasisbackend.DTO.Descriptor;

import com.example.petoasisbackend.Model.Descriptor.Badge;
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

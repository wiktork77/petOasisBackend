package com.example.petoasisbackend.DTO.Badge.Badge;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Badge.Badge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeNameDTO implements ModelDTO<Badge> {
    private String badgeName;

    public static BadgeNameDTO fromBadge(Badge badge) {
        return new BadgeNameDTO(
                badge.getBadgeName()
        );
    }
}

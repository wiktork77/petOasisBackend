package com.example.petoasisbackend.DTO.Badge.Badge;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Badge.Badge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeVerboseDTO implements ModelDTO<Badge> {
    private Integer badgeId;
    private String badgeName;

    public static BadgeVerboseDTO fromBadge(Badge badge) {
        return new BadgeVerboseDTO(
                badge.getBadgeId(),
                badge.getBadgeName()
        );
    }
}

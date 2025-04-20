package com.example.petoasisbackend.DTO.Descriptor.Badge;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Descriptor.Badge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeMinimumDTO implements ModelDTO<Badge> {
    private Integer badgeId;

    public static BadgeMinimumDTO fromBadge(Badge badge) {
        return new BadgeMinimumDTO(
                badge.getBadgeId()
        );
    }
}

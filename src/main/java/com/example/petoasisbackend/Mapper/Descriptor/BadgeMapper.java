package com.example.petoasisbackend.Mapper.Descriptor;

import com.example.petoasisbackend.DTO.Descriptor.BadgeMinimumDTO;
import com.example.petoasisbackend.DTO.Descriptor.BadgeNameDTO;
import com.example.petoasisbackend.DTO.Descriptor.BadgeVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
import com.example.petoasisbackend.Model.Descriptor.Badge;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BadgeMapper implements DetailLevelMapper<Badge> {
    @Override
    public Function<Badge, ModelDTO<Badge>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return BadgeVerboseDTO::fromBadge;
            }
            case MINIMUM -> {
                return BadgeMinimumDTO::fromBadge;
            }
            default -> {
                return BadgeNameDTO::fromBadge;
            }
        }
    }
}

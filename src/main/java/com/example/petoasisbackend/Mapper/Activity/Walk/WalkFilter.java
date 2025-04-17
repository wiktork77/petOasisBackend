package com.example.petoasisbackend.Mapper.Activity.Walk;

import com.example.petoasisbackend.Model.Activity.Walk;

import java.util.function.Function;

public class WalkFilter {
    public static Boolean filter(Walk walk, WalkFilterType filterType) {
        switch (filterType) {
            case ALL -> {
                return true;
            }
            case PAST -> {
                return walk.getWalkStatus().getStatus().equals("Finished");
            }
            case CURRENT -> {
                return walk.getWalkStatus().getStatus().equals("In progress");
            }
            case PLANNED -> {
                return walk.getWalkStatus().getStatus().equals("Pending");
            }
            case CANCELLED -> {
                return walk.getWalkStatus().getStatus().equals("Cancelled");
            }
            default -> {
                return false;
            }
        }
    }
}

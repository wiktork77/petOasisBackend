package com.example.petoasisbackend.Tools.Time;

import java.time.ZoneId;

public class TimeParser {
    public Boolean doPeriodsIntersect(Period period, Period other) {
        Long periodStart = period.getStart().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        Long periodEnd = period.getEnd().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

        Long otherStart = other.getStart().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        Long otherEnd = other.getEnd().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

        if ( (periodStart <= otherEnd) || (periodEnd >= otherStart) ) {
            return true;
        }

        return false;
    }
}



package com.example.petoasisbackend.Tools.Time;

import java.time.ZoneId;

public class TimeValidator {
    public Boolean doPeriodsIntersect(Period period, Period other) {
        Long periodStart = period.getStart().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        Long periodEnd = period.getFinish().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

        Long otherStart = other.getStart().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        Long otherEnd = other.getFinish().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

        if ( (periodStart <= otherEnd) || (periodEnd >= otherStart) ) {
            return true;
        }

        return false;
    }

    public Boolean isPeriodValid(Period period) {
        Long periodStart = period.getStart().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        Long periodEnd = period.getFinish().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

        return periodStart < periodEnd;
    }
}



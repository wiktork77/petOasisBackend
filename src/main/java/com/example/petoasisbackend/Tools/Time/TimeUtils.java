package com.example.petoasisbackend.Tools.Time;

import java.time.Period;

public class TimeUtils {
    public static Boolean doTimeIntervalsIntersect(TimeInterval interval, TimeInterval other) {
        return interval.getStart().isBefore(other.getFinish())
                && interval.getFinish().isAfter(other.getStart());
    }
}

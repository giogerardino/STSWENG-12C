package com.orangeandbronze.enlistment;
import org.apache.commons.lang3.Validate;

import javax.management.RuntimeErrorException;
import java.time.LocalTime;
import java.util.Objects;

class Schedule{
    private final LocalTime START_TIME = LocalTime.of(8,00,0,0);
    private final LocalTime END_TIME = LocalTime.of(17,30,0,0);


    private final Days days;
    private final LocalTime periodStart;
    private final LocalTime periodEnd;

    Schedule(Days days, LocalTime periodStart, LocalTime periodEnd) {

        Validate.notNull(days);
        Validate.notNull(periodStart);
        Validate.notNull(periodEnd);
        if(!isTopBottomOfHour(periodStart) || !isTopBottomOfHour(periodEnd) ){
            //Invalid time/s, must be starting and ending at the top or bottom of an hour. (See invalid periods 1 and 2)
            throw new RuntimeErrorException(null, "Invalid time/s, must be starting and ending at the top or bottom of an hour.");
        }
        if(periodStart.equals(periodEnd)){
            //Invalid time/s, must be in 30 minute increments. (See invalid period 2)
            throw new RuntimeErrorException(null, "Invalid time/s, must be in 30 minute increments.");
        }
        if(periodStart.isAfter(periodEnd)){
            //Invalid schedule, periodStart must be before periodEnd  (See invalid period 3)
            throw new RuntimeErrorException(null, "Invalid schedule, periodStart must be before periodEnd");
        }
        if(periodStart.isBefore(START_TIME) || periodStart.isAfter(END_TIME) || periodEnd.isBefore(START_TIME) || periodEnd.isAfter(END_TIME)){
            //Invalid schedule, must be between START_TIME and END_TIME (See invalid period 4)
            throw new RuntimeErrorException(null, "Invalid schedule, must be between START_TIME and END_TIME");
        }
        this.days = days;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    Boolean isTopBottomOfHour(LocalTime time){
        return time.getMinute()==30 || time.getMinute()==0;
    }
    Boolean overlapsWith(Schedule other){
        if(!this.days.equals(other.getDays())) return false;
        return this.periodStart.isAfter(other.getPeriodStart()) && this.periodStart.isBefore(other.getPeriodEnd()) ||
                 this.periodEnd.isAfter(other.getPeriodStart()) && this.periodEnd.isBefore(other.getPeriodEnd()) ||
                 this.periodStart.equals(other.getPeriodStart()) ||
                 this.periodEnd.equals(other.getPeriodEnd());
    }
    Days getDays() {
        Days temp = days;
        return temp;
    }
    LocalTime getPeriodStart() {
        LocalTime temp = periodStart;
        return temp;
    }

    LocalTime getPeriodEnd() {
        LocalTime temp = periodEnd;
        return temp;
    }
    @Override
    public String toString() {
        //return days + " " + period; //TF H0830
        return days + " " + periodStart + " - " + periodEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return days == schedule.days && periodStart.equals(schedule.periodStart) && periodEnd.equals(schedule.periodEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(days, periodStart, periodEnd);
    }

}

enum Days {
        MTH, TF, WS
}

/*
enum Period {
    H0830, H1000, H1130, H1200, H1300, H1430, H1600
}
*/


package com.orangeandbronze.enlistment;

import java.util.Objects;
import javax.management.RuntimeErrorException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

class Section{
    private final String sectionID;
    private final Schedule schedule;
    private final Room room;
    private int sectionEnlistment = 0;

    //class constructor
    Section(String sectionID, Schedule schedule, Room room){

        Validate.notBlank(sectionID);
        Validate.isTrue(StringUtils.isAlphanumeric(sectionID), "section ID must be AlphaNumeric" + sectionID);
        Validate.notNull(schedule);
        Validate.notNull(room);

        this.sectionID = sectionID;
        this.schedule = schedule;
        this.room = room;
    }

    boolean hasConflict (Section other) {
        return this.schedule.equals(other.schedule);
    }

    void checkForConflict (Section other) {
        if (this.schedule.equals(other.schedule) && this.room.equals(other.room)) {
            throw new ScheduleConflictException("this section " + this + 
                " has the same schedule as other section " + other +
                " at schedule " + schedule + ", " + room);
        }
    }

    public int getSectionEnlistment() {
        return sectionEnlistment;
    }

    public boolean atCapacity() {
        return sectionEnlistment == room.getCapacity();
    }

    public void addSectionEnlistment() {
        sectionEnlistment++;
    }

    public void subtractSectionEnlistment() {
        if(sectionEnlistment == 0) {
            throw new RuntimeErrorException(null, "currentEnlisted cannot be be negative.");
        }
        sectionEnlistment--;
    }

    @Override
    public String toString() {
        return sectionID;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Section section = (Section) object;
        return java.util.Objects.equals(sectionID, section.sectionID);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), sectionID);
    }
}
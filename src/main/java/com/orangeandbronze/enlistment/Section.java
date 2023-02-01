package com.orangeandbronze.enlistment;
import static org.apache.commons.lang3.Validate.notBlank;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

class Section{
    private final String sectionID;
    private final Schedule schedule;

    //class constructor
    Section(String sectionID, Schedule schedule){

        notBlank(sectionID);
        Validate.notNull(schedule);
        Validate.isTrue(StringUtils.isAlphanumeric(sectionID), "section ID must be AlphaNumeric" + sectionID);

        this.sectionID = sectionID;
        this.schedule = schedule;
    }

    boolean hasConflict (Section other) {
        return this.schedule.equals(other.schedule);
    }

    void checkForConflict (Section other) {
        if (this.schedule.equals(other.schedule)) {
            throw new ScheduleConflictException("this section " + this + 
                " has the same schedule as other section " + other +
                " at schedule " + schedule);
        }
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
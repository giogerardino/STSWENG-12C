package com.orangeandbronze.enlistment;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import java.util.*;
import static org.apache.commons.lang3.Validate.notBlank;

class Section{
    private final String sectionID;
    private final Schedule schedule ;

    //class constructor
    Section(String sectionID, Schedule schedule ){

        notBlank(sectionID);
        Validate.isTrue(StringUtils.isAlphanumeric(sectionID), "section ID must be AlphaNumeric" + sectionID);

        this.sectionID = sectionID;
        this.schedule = schedule;
    }

    Schedule getSchedule(){
        Schedule tempSchedule = schedule;
        return tempSchedule;
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

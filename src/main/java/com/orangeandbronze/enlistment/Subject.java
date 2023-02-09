package com.orangeandbronze.enlistment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

class Subject {
    private final String subjectId;
    private final Collection<Subject> preReqSubjects = new HashSet<>();
    private final int units;
    private final boolean isLab;
    private final boolean isTaken;

    Subject (String subjectId, int units, boolean isLab, Collection<Subject> preReqSubjects, boolean isTaken) {
        Validate.notNull(subjectId);
        Validate.isTrue(StringUtils.isAlphanumeric(subjectId), "Subject Id must be AlphaNumeric, was " + subjectId);
        Validate.notNull(units);
        Validate.notNull(isLab);
        Validate.notNull(isTaken);

        this.subjectId = subjectId;
        this.units = units;
        this.isLab = isLab;
        this.isTaken = isTaken;

        this.preReqSubjects.addAll(preReqSubjects);
        this.preReqSubjects.removeIf(Objects::isNull);
    }

    Subject (String subjectId, int units, boolean isLab, boolean isTaken) {
        this(subjectId, units, isLab, Collections.emptyList(), isTaken);
    }

    Boolean isTaken () {
        return isTaken;
    }

    Boolean isLab () { return isLab; }

    Collection<Subject> getPreRequisites() {
        return new ArrayList<>(preReqSubjects);
    }
    int getUnits() {
        return units;
    }

    @Override
    public String toString() {
        return subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        return Objects.equals(subjectId, subject.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId);
    }
}

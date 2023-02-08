package com.orangeandbronze.enlistment;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

class Subject {
    private final String subjectId;
    private final int units;
    private final boolean isLab;
    private final boolean isPreReq;

    Subject (String subjectId, int units, boolean isLab, boolean isPreReq) {
        Validate.notNull(subjectId);
        Validate.isTrue(StringUtils.isAlphanumeric(subjectId), "Subject Id must be AlphaNumeric, was " + subjectId);
        Validate.notNull(units);
        Validate.notNull(isLab);

        this.subjectId = subjectId;
        this.units = units;
        this.isLab = isLab;
        this.isPreReq = isPreReq;
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

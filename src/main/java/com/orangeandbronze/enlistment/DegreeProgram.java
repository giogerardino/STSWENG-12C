package com.orangeandbronze.enlistment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

class DegreeProgram {
    private final String programCode;
    private final Collection<Subject> subjects = new HashSet<>();

    DegreeProgram (String programCode, Collection<Subject> subjects) {
        Validate.notNull(programCode);

        this.programCode = programCode;

        this.subjects.addAll(subjects);
        this.subjects.removeIf(Objects::isNull);
    }

    Collection<Subject> getSubjects() {
        return new ArrayList<>(subjects);
    }  

    @Override
    public String toString() {
        return "Degree Program" + programCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DegreeProgram degreeProgram = (DegreeProgram) o;
        return programCode == degreeProgram.programCode && Objects.equals(subjects, degreeProgram.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(programCode, subjects);
    }
}

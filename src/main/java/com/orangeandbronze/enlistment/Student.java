package com.orangeandbronze.enlistment;
import java.util.*;

import org.apache.commons.lang3.Validate;

class Student{
    private final int studentNum;
    private final Collection<Section> sections = new HashSet<>();

    //class constructor
    Student(int studentNum, Collection<Section> sections){

        if(studentNum < 0){
            throw new IllegalArgumentException(
                    "studentNum should be non-negative, was: " + studentNum
            );
        }

        if(sections == null){
            throw new NullPointerException();
        }

        this.studentNum = studentNum;
        this.sections.addAll(sections);
        this.sections.removeIf(Objects::isNull);

    }

    //Another constructor
    Student (int studentNumber) {
        this(studentNumber, Collections.emptyList());
    }

    /** Enlist a student to a section */
    void enlist(Section newSection){
        Validate.notNull(newSection);

        // check if room of section is at max capacity
        if (newSection.atCapacity()) {
            throw new RuntimeException(newSection + " is at max capacity");
        }

        // loop through all current sections, check for same sched
        sections.forEach(currSection -> currSection.checkForConflict(newSection));
        this.sections.add(newSection);
        newSection.addSectionEnlistment();
    }

    Collection<Section> getSections() {
        return new ArrayList<>(sections);
    }

    @Override
    public String toString() {
        return "Student#" + studentNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentNum == student.studentNum && Objects.equals(sections, student.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNum, sections);
    }
}
    
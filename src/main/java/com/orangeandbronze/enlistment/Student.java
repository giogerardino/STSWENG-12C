package com.orangeandbronze.enlistment;
import org.apache.commons.lang3.Validate;

import java.util.*;

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

    /** Enlist a student to a section */
    void enlist(Section newSection){
        Validate.notNull(newSection);
        //loop through al current sections, check for same sched
        sections.forEach(currSection -> {
            if(currSection.getSchedule().equals(newSection.getSchedule())){
                throw new RuntimeException("Current section "+currSection+" has the same schedule as new section "+ newSection+ " at schedule " + currSection.getSchedule());
            }
        });
        this.sections.add(newSection);
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
    
package com.orangeandbronze.enlistment;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang3.Validate;

class Student{
    private final int studentNum;
    private final Collection<Section> sections = new HashSet<>();
    private final Collection<Subject> subjectsTaken;

    private final double MAX_UNITS = 24.0;

    //class constructor
    Student(int studentNum, Collection<Section> sections, Collection<Subject> subjectsTaken){

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

        Collection<Subject> subjectsTakenModifiable = new HashSet<>();
        subjectsTakenModifiable.addAll(subjectsTaken);
        subjectsTakenModifiable.removeIf(Objects::isNull);
        this.subjectsTaken = Collections.unmodifiableCollection(subjectsTakenModifiable);

    }

    //Another constructor
    Student (int studentNumber) {
        this(studentNumber, Collections.emptyList(), Collections.emptyList());
    }

    /** Enlist a student to a section */
    void enlist(Section newSection){
        Validate.notNull(newSection);

        // loop through all current sections, check for same sched
        sections.forEach(currSection -> currSection.checkForConflict(newSection));

        // check if student is already enlisted in a section with same subject
        sections.forEach(currSection -> currSection.checkForSameSubject(newSection.getSubject()));

        // check if all pre-requisites are taken
        sections.forEach(currSection -> currSection.checkAllPreRequisitesTaken(this));

        if (this.willOverload(newSection.getSubject().getUnits())) {
            throw new RuntimeException("Student cannot enroll in " + newSection + " as they will exceed 24.0 units.");
        }
        
        this.sections.add(newSection);
        newSection.addSectionEnlistment();
    }

    void cancelSection(Section enrolledSection){
        Validate.notNull(enrolledSection);

        if(!this.isEnrolledIn(enrolledSection)){
            throw new RuntimeException(enrolledSection + " not in sections.");
        }
        this.sections.remove(enrolledSection);
    }

    Boolean willOverload(float currentSectionUnits){
        float unitsToTake = currentSectionUnits;

        for (Section s: this.sections){
            unitsToTake += s.getSubject().getUnits();
        }

        return (unitsToTake >= MAX_UNITS);
    }

    Boolean isEnrolledIn(Section enrolledSection){
        return sections.contains(enrolledSection);
    }

    Boolean hasTakenSubject(Subject subjectToEnroll) {
        return subjectsTaken.contains(subjectToEnroll);
    }

    BigDecimal requestAssessment () {
        Assessment studentAssessment = new Assessment(this.sections);
        return studentAssessment.getAssessment();
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
        return studentNum == student.studentNum && Objects.equals(sections, student.sections) & Objects.equals(subjectsTaken, student.subjectsTaken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNum, sections, subjectsTaken);
    }
}
    
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

        // loop through all current sections, check for same sched
        sections.forEach(currSection -> currSection.checkForConflict(newSection));

        // check if student is already enlisted in a section with same subject
        sections.forEach(currSection -> currSection.checkForSameSubject(newSection.getSubject()));

        // check if all pre requisites are taken
        sections.forEach(currSection -> currSection.checkAllPreRequisitesTaken());
        
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

    Boolean isEnrolledIn(Section enrolledSection){
        return sections.contains(enrolledSection);
    }

    //TODO
    Double requestAssessment () {
        Double totalAssessment = 0.0;
        Double subjectPrice = 0.0;
        Double valueAddedTax;

        for (Section section: sections){

            subjectPrice = 2000.0*section.getSubject().getUnits();
            if(section.getSubject().isLab() == true){
                subjectPrice+=1000.0;
            }

            totalAssessment+=subjectPrice;
        }

        valueAddedTax = totalAssessment*0.12;
        totalAssessment+=3000.0;
        //idk if you add the VAT or you just compute it and show the value in the assessment like usually in receipts so change it if needed
        totalAssessment+=valueAddedTax;
        return totalAssessment;
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
    
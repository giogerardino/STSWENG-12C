package com.orangeandbronze.enlistment;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.orangeandbronze.enlistment.Period.*;
import static com.orangeandbronze.enlistment.Days.*;

public class StudentTest {

    static final Schedule DEFAULT_SCHEDULE = new Schedule(MTH, H1000);
    static final Room DEFAULT_ROOM = new Room("A1706", 45, Set.of());

    //Prerequisite subjects
    static final Subject prereq1 = new Subject("PREREQ1", 3, false);
    static final Subject prereq2 = new Subject("PREREQ2", 3, false);

    //Sample Subjects w/ Prerequisites
    static final Subject subj1prereq = new Subject("SUBJECT1PREREQ", 3, false, Set.of(prereq1, prereq2));
    static final Subject subj2prereq = new Subject("SUBJECT2PREREQ", 3, false, Set.of(prereq1, prereq2));
    static final Subject subj3prereq = new Subject("SUBJECT3PREREQ", 3, false, Set.of(prereq1, prereq2));

    //Sample Subjects w/o Prerequisites
    static final Subject subj1 = new Subject("SUBJECT1", 3, false);
    static final Subject subj2 = new Subject("SUBJECT2", 3, false);
    static final Subject subj3 = new Subject("SUBJECT3", 3, false);

    static final DegreeProgram DEFAULT_DEGREE = new DegreeProgram("CS-ST18", Set.of(subj1prereq, subj2prereq, subj3));

    @Test
    void enlist_2_sections_no_conflict() { // happy scenario
                    // pseudocode //
        
        // Given 1 student and 2 sections w/ no conflict
        Student student = new Student(1, Set.of(), Set.of(prereq1,prereq2), DEFAULT_DEGREE);

        Section sec1 = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM, subj1prereq);
        Section sec2 = new Section("B", new Schedule(MTH, H0830), DEFAULT_ROOM, subj2prereq);

        // When student enlists in both sections
        student.enlist(sec1);
        student.enlist(sec2);

        // Then the 2 sections should be found in the student and student should ONLY have 2 sections
        Collection<Section> sections = student.getSections();
        assertAll(
                () -> assertTrue(sections.containsAll(List.of(sec1, sec2))),
                () -> assertEquals(2, sections.size())
        );
    }
    @Test // not an actual method. only junit calls it a method
    void enlist_2_sections_same_schedule() { // negative scenario

        // Given a student and 2 sections with same schedule
        Student student = new Student(1, Set.of(), Set.of(prereq1, prereq2), DEFAULT_DEGREE);

        Section sec1 = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM, subj1prereq);
        Section sec2 = new Section("B", DEFAULT_SCHEDULE, DEFAULT_ROOM, subj2prereq);

        // When student enlists in both sections
        student.enlist(sec1);

        // Then on the 2nd enlistment an exception should be thrown
        assertThrows(ScheduleConflictException.class, () -> student.enlist(sec2));
    }

    @Test
    void enlist_section_not_at_room_capacity() { // positive scenario
        // Taken prerequisite subjects
        Subject prereq1 = new Subject("PREREQ1", 3, false);
        Subject prereq2 = new Subject("PREREQ2", 3, false);

        // Given 2 students and a section with a room capacity of 1
        Student student1 = new Student(1, Set.of(), Set.of(prereq1, prereq2), DEFAULT_DEGREE);
        Student student2 = new Student(2, Set.of(), Set.of(prereq1, prereq2), DEFAULT_DEGREE);

        Section sec = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM, subj1prereq);

        // When student1 enlists in the section
        student1.enlist(sec);

        // Then on student2 enlists in the same section, both students should be enlisted in the section and sectionEnlistment should be 2
        student2.enlist(sec);
        Collection<Section> sections1 = student1.getSections();
        Collection<Section> sections2 = student2.getSections();
        assertAll(
                () -> assertTrue(sections1.contains(sec)),
                () -> assertTrue(sections2.contains(sec)),
                () -> assertEquals(2, sec.getSectionEnlistment())
        );
    }
    @Test
    void enlist_section_at_room_capacity() { // negative scenario

        // Given 2 students and a section with a room capacity of 1
        Student student1 = new Student(1, Set.of(), Set.of(prereq1, prereq2), DEFAULT_DEGREE);
        Student student2 = new Student(2, Set.of(), Set.of(prereq1, prereq2), DEFAULT_DEGREE);

        Section sec = new Section("A", DEFAULT_SCHEDULE, new Room("A1705", 1, Set.of()), subj1prereq);

        // When student1 enlists in the section
        student1.enlist(sec);

        // Then on student2 enlists in the same section, an exception should be thrown
        assertThrows(RuntimeException.class, () -> student2.enlist(sec));
    }

    @Test
    void cancelling_an_enrolled_section() {

        Student student = new Student(1, Set.of(), Set.of(prereq1, prereq2), DEFAULT_DEGREE);
        
        Section sec = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM, subj1prereq);

        student.enlist(sec);
        student.cancelSection(sec);
        assertAll(
                () -> assertTrue(student.getSections().isEmpty()),
                () -> assertEquals(0, student.getSections().size())
        );
    }
    @Test
    void cancelling_a_section_not_enrolled() {

        Student student = new Student(1, Set.of(), Set.of(prereq1,prereq2), DEFAULT_DEGREE);

       
        Section sec1 = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM, subj1prereq);
        Section sec2 = new Section("B", new Schedule(MTH, H0830), DEFAULT_ROOM, subj2prereq);

        student.enlist(sec1);
        assertThrows(RuntimeException.class, () -> student.cancelSection(sec2));

    }

    @Test
    void can_enlist_in_two_section_not_same_subject() { // positive scenario

        // Given a student enlisting in 2 sections of DIFFERENT SUBJECTS
        Student student = new Student(1, Set.of(), Set.of(prereq1, prereq2), DEFAULT_DEGREE);

        Section sec1 = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM, subj1prereq);
        Section sec2 = new Section("B", new Schedule(MTH, H0830), DEFAULT_ROOM, subj2prereq);

        // When student enlists in 2 sections and NOT SAME subject
        student.enlist(sec1);
        student.enlist(sec2);

        // Then the student should be able to enlist in both sections
        Collection<Section> sections = student.getSections();
        assertAll(
                () -> assertTrue(sections.containsAll(List.of(sec1,sec2))),
                () -> assertEquals(2, sections.size())
        );
    }
    @Test
    void can_enlist_in_two_section_same_subject() { // negative scenario

        // Given a student enlisting in 2 sections of SAME SUBJECTS
        Student student = new Student(1, Set.of(), Set.of(prereq1, prereq2), DEFAULT_DEGREE);

        // New subjects = same subjectId
        Subject subject1 = new Subject("SUBJECT1PREREQ", 3, false, Set.of(prereq1, prereq2));
        Subject subject2 = new Subject("SUBJECT1PREREQ", 3, false, Set.of(prereq1, prereq2));

        Section sec1 = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM, subject1);
        Section sec2 = new Section("B", new Schedule(MTH, H0830), DEFAULT_ROOM, subject2);

        // When student enlists in a section of a certain subject
        student.enlist(sec1);

        // Then the student should NOT be able to enlist in another section with the same subject
        assertThrows(IllegalArgumentException.class, () -> student.enlist(sec2));
    }

    @Test
    void can_enlist_in_subject_with_taken_prerequisites (){
        // Taken prerequisite subjects
        Subject prereq1 = new Subject("PREREQ1", 3, false);
        Subject prereq2 = new Subject("PREREQ2", 3, false);

        // Given a student enlisting in 1 section (with its subject having prerequisites)
        Student student = new Student(1, Set.of(), Set.of(prereq1, prereq2), DEFAULT_DEGREE);

        Section sec1 = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM, subj1prereq);
        Section sec2 = new Section("A", new Schedule(MTH, H0830), DEFAULT_ROOM, subj2prereq);

        // When student enlists in a section of a certain subject with TAKEN prerequisites
        student.enlist(sec1);
        student.enlist(sec2);

        // Then the student SHOULD be able to enlist in the section
        Collection<Section> sections = student.getSections();
        assertAll(
                () -> assertTrue(sections.containsAll(List.of(sec1, sec2))),
                () -> assertEquals(2, sections.size())
        );
    }

    @Test
    void can_enlist_in_subject_with_no_prerequisites (){
        // Given a student enlisting in 1 section (with its subject having no prerequisites), in degree program
        Student student = new Student(1, DEFAULT_DEGREE);

        Section sec1 = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM, subj3);

        // When student enlists in a section of a certain subject with TAKEN prerequisites
        student.enlist(sec1);

        // Then the student SHOULD be able to enlist in the section
        Collection<Section> sections = student.getSections();
        assertAll(
                () -> assertTrue(sections.containsAll(List.of(sec1))),
                () -> assertEquals(1, sections.size())
        );
    }

    @Test
    void enlist_in_subject_with_not_taken_prerequisites (){ //negative scenario

        Student student = new Student(1, Set.of(), Set.of(prereq1), DEFAULT_DEGREE);

        Section sec1 = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM, subj1prereq);
        
        // When student enlists in a section of a certain subject with NOT TAKEN prerequisites
        student.enlist(sec1);
    
        // Then the student SHOULD NOT be able to enlist in the section
        assertThrows(RuntimeException.class, () -> student.enlist(sec1));
    }

    @Test
    void request_assessment(){

        Subject nonLabSubject1 = new Subject("STSWENG", 3, false, Set.of(prereq1, prereq2));
        Subject nonLabSubject2 = new Subject("STADVDB", 2, false, Set.of(prereq1, prereq2));
        Subject labSubject = new Subject("LBSWENG", 2, true, Set.of(prereq1, prereq2));

        Section STSWENGS12 = new Section("2401", DEFAULT_SCHEDULE, DEFAULT_ROOM, nonLabSubject1);
        Section STADVDBS12 = new Section("2501", new Schedule(MTH, H1600), new Room("GK304A", 45, Set.of()), nonLabSubject2);
        Section LBSWENGS12 = new Section("2601", new Schedule(MTH, H0830), new Room("A1109", 45, Set.of()), labSubject);

        DegreeProgram degreeProgram = new DegreeProgram("SAMPLE-DEGREE", Set.of(nonLabSubject1, nonLabSubject2, labSubject));
        Student student = new Student(1, Set.of(), Set.of(prereq1, prereq2), degreeProgram);

        student.enlist(STSWENGS12);
        student.enlist(STADVDBS12);
        student.enlist(LBSWENGS12);

        Collection<Section> sections = student.getSections();

        BigDecimal studentAssessment = student.requestAssessment().stripTrailingZeros();

        assertAll(
                () -> assertTrue(sections.containsAll(List.of(STSWENGS12, STADVDBS12, LBSWENGS12))),
                () -> assertEquals(3, sections.size()),
                () -> assertEquals(BigDecimal.valueOf(20160).stripTrailingZeros(), studentAssessment)
        );

    }

    @Test
    void enlist_with_overload(){

        // Other subject/section in cart
        Subject subject1 = new Subject("SUBJECT1", 23, false, Set.of());
        Section sec1 = new Section("Z", new Schedule(WS, H1600), new Room("A1901", 45, Set.of()), subject1);

        // New subjects
        Subject subject2 = new Subject("SUBJECT2", 3, false, Set.of(prereq1, prereq2));
        Section sec2 = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM, subject2);

        DegreeProgram SAMPLE_DEGREE = new DegreeProgram("BSIT", Set.of(subject1, subject2, subj3));

        Student student = new Student(1, Set.of(sec1), Set.of(prereq1, prereq2), SAMPLE_DEGREE);

        // Then the student SHOULD NOT be able to enlist in the section
        assertThrows(RuntimeException.class, () -> student.enlist(sec2));
    }

    @Test
    void section_same_room_schedule_not_overlap() {

        Subject sub1 = new Subject("SUBJECT1", 3, false, Set.of());
        Subject sub2 = new Subject("SUBJECT2", 5, false, Set.of());

        Section sec1 = new Section("ABC123", DEFAULT_SCHEDULE, DEFAULT_ROOM,sub1);
        Section sec2 = new Section("ABC321", new Schedule(WS, H1600), DEFAULT_ROOM, sub2);

        Room room = new Room("A", 35, Set.of(sec1, sec2));

        assertAll(
                () -> assertTrue(room.getSections().containsAll(List.of(sec1, sec2))),
                () -> assertEquals(2, room.getSections().size())
        );

    }

    @Test
    void section_same_room_schedule_overlap() { //negative scenario
        Subject sub1 = new Subject("SUBJECT1", 3, false, Set.of());
        Subject sub2 = new Subject("SUBJECT2", 3, false, Set.of());

        Room room = new Room("A1706", 45, Set.of());

        Section sec1 = new Section("ABC123", new Schedule(MTH, H1130), room, sub1);
        Section sec2 = new Section("ABC321", new Schedule(MTH, H1130), room, sub2);

        room.addSection(sec1);

        assertThrows(ScheduleConflictException.class, () -> room.addSection(sec2));
    }

    @Test
    void enlist_subject_in_degree_program() {
        // Given a student enlisting in 1 section (with its subject having prerequisites)
        Student student = new Student(1, Set.of(), Set.of(prereq1, prereq2), DEFAULT_DEGREE);

        // subj1prereq, subj2prereq, subj3 all part of DEFAULT_DEGREE
        Section sec1 = new Section("ABC123", DEFAULT_SCHEDULE, DEFAULT_ROOM, subj1prereq);
        Section sec2 = new Section("ABC456", new Schedule(WS, H1600), DEFAULT_ROOM, subj2prereq);

        student.enlist(sec1);
        student.enlist(sec2);

         // Then the student SHOULD be able to enlist in the section
         Collection<Section> sections = student.getSections();
         assertAll(
                 () -> assertTrue(sections.containsAll(List.of(sec1, sec2))),
                 () -> assertEquals(2, sections.size())
         );

    }

    @Test
    void enlist_subject_not_in_degree_program() { //negative scenario
        // Given a student enlisting in 1 section (with its subject having prerequisites)
        Student student = new Student(1, Set.of(), Set.of(prereq1, prereq2), DEFAULT_DEGREE);

        // only subj1prereq is part of DEFAULT_DEGREE
        Section sec1 = new Section("ABC123", DEFAULT_SCHEDULE, DEFAULT_ROOM, subj1prereq);
        Section sec2 = new Section("ABC456", new Schedule(WS, H1600), DEFAULT_ROOM, subj2);

        student.enlist(sec1);

         // Then the student SHOULD NOT be able to enlist in the section
        assertThrows(IllegalArgumentException.class, () -> student.enlist(sec2));

    }

}

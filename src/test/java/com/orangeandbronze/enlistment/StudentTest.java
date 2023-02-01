package com.orangeandbronze.enlistment;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.orangeandbronze.enlistment.Period.*;
import static com.orangeandbronze.enlistment.Days.*;

public class StudentTest {

    static final Schedule DEFAULT_SCHEDULE = new Schedule(MTH, H1000);
    static final Room DEFAULT_ROOM = new Room("A1706", 45);
    @Test
    void enlist_2_sections_no_conflict() { // happy scenario
                    // pseudocode //
        // Given 1 student and 2 sections w/ no conflict
        Student student = new Student(1);
        Section sec1 = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM);
        Section sec2 = new Section("B", new Schedule(MTH, H0830), DEFAULT_ROOM);
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
        Student student = new Student(1);
        Section sec1 = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM);
        Section sec2 = new Section("B", DEFAULT_SCHEDULE, DEFAULT_ROOM);
        // When student enlists in both sections
        student.enlist(sec1);
        // Then on the 2nd enlistment an exception should be thrown
        assertThrows(ScheduleConflictException.class, () -> student.enlist(sec2));
    }

    @Test
    void enlist_section_not_at_room_capacity() { // positive scenario
        // Given 2 students and a section with a room capacity of 1
        Student student1 = new Student(1);
        Student student2 = new Student(2);
        Section sec = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM);
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
        Student student1 = new Student(1);
        Student student2 = new Student(2);
        Section sec = new Section("A", DEFAULT_SCHEDULE, new Room("A1705", 1));
        // When student1 enlists in the section
        student1.enlist(sec);
        // Then on student2 enlists in the same section, an exception should be thrown
        assertThrows(RuntimeException.class, () -> student2.enlist(sec));
    }

    @Test
    void cancelling_an_enrolled_section() {
        Student student = new Student(1);
        Section sec = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM);

        student.enlist(sec);
        student.cancelSection(sec);
        assertAll(
                () -> assertTrue(student.getSections().isEmpty()),
                () -> assertEquals(0, student.getSections().size())
        );
    }
    @Test
    void cancelling_a_section_not_enrolled() {
        Student student = new Student(1);
        Section sec1 = new Section("A", DEFAULT_SCHEDULE, DEFAULT_ROOM);
        Section sec2 = new Section("B", new Schedule(MTH, H0830), DEFAULT_ROOM);

        student.enlist(sec1);
        assertThrows(RuntimeException.class, () -> student.cancelSection(sec2));

    }

}

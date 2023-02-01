package com.orangeandbronze.enlistment;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.orangeandbronze.enlistment.Period.*;
import static com.orangeandbronze.enlistment.Days.*;

public class StudentTest {

    static final Schedule DEFAULT_SCHEDULE = new Schedule(MTH, H1000);
    @Test
    void enlist_2_sections_no_conflict() { // happy scenario
                    // pseudocode //
        // Given 1 student and 2 sections w/ no conflict
        Student student = new Student(1);
        Section sec1 = new Section("A", DEFAULT_SCHEDULE);
        Section sec2 = new Section("B", new Schedule(MTH, H0830));
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
        Section sec1 = new Section("A", DEFAULT_SCHEDULE);
        Section sec2 = new Section("B", DEFAULT_SCHEDULE);
        // When student enlists in both sections
        student.enlist(sec1);
        // Then on the 2nd enlistment an exception should be thrown
        assertThrows(ScheduleConflictException.class, () -> student.enlist(sec2));
    }
}

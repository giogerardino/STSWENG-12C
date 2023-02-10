# University Enlistment System

### Iteration 1

- [X] A student is identified by his/her student number, which is non-negative integer
- [X] Student enlists in one or more sections. The student may have already previously enlisted in other sections
- [X] A section is identified by its section ID, which is alphanumeric
- [X] A student cannot enlist in the same section more than once
- [X] The system makes sure that the student cannot enlist in any section that has a conflict with previously enlisted sections
- [X] A section is in conflict with another section if the schedules are in conflict
- [X] Schedules are as follows:
  - [X] Days:
    - [X] Mon/Thu, Tue/Fri, Wed/Sat
  - [X] Periods:
    - [X] 8:30am-10am, 10am-11:30, 11:30am-1pm, 1pm-2:30pm, 2:30pm-4pm, 4pm-5:30pm

* [X] A section has a room
* [X] A room is identified by its room name, which is alphanumeric
* [X] A room has a capacity
* [X] Section enlistment may not exceed the capacity of its room
* [X] A student may cancel an enlisted section.

---

### Iteration 2

* [X] All Iteration 1 requirements
* [X] A section has a subject.
* [X] A subject is identified by its alphanumeric Subject ID.
* [X] A student cannot enlist in two sections with the same subject.
* [X] A subject may or may not have one or more prerequisite subjects.
* [X] A student may not enlist in a section if the student has not yet taken the prerequisite subjects.
* [X] Each subject has a corresponding number of units.
* [X] Some subjects may be designated as "laboratory" subjects.
* [X] A student can request to be assessed, which is simply a request for total amount of money that the student will need to pay. It is computed as follows:

  * [X] Each unit is ₱2,000
  * [X] Laboratory subjects have an *additional* ₱1,000 laboratory fee per subject
  * [X] Miscellaneous fees are ₱3,000
  * [X] Value Added Tax (VAT) is 12%

---

### Iteration 3

#### Additional Requirements

* [ ] A student cannot enlist in more than 24 units.
* [ ] No two sections can share the same room if their schedules overlap.
* [ ] A student belongs to a degree program.
* [ ] A degree program is a collection of subjects that a student may take.
* [ ] A student cannot enroll in section if the subject of the section is not part of the student's degree program.
* [X] All financial values should be represented as BigDecimal.

#### Urgent Change Request - Schedules

* [ ] A student may not enlist in a section if its schedule overlaps with the schedule of any of its currently enlisted sections.
* [ ] Periods may be of any duration of 30-min increments, w/in the hours of 8:30am - 5:30pm.
* [ ] Periods may begin and may end at the top of each hour (9:00, 10:00, 11:00...) or at the bottom of each hour (9:30, 10:30, 11:30...).
* [ ] End of a period may not be on or before the start of the period.

* Examples
  * Valid Periods
    * 8:30am - 9:00am
    * 9:00am - 12:00nn
    * 2:30pm - 4:30pm
    * 9:00am - 10:30am
  * Invalid Periods:
    * 8:45am - 10:15am
      * Does not start at top or bottom of the hour
    * 12:00pm - 12:02pm
      * Not a 30 minute increment
    * 4:00pm - 3:00pm
      * Start time is after end time
    * 4:30pm - 6:00pm
      * End time is after 5:30pm

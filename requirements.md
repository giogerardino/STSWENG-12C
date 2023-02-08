# University Enlistment System

### Iteration 1

- [ ] A student is identified by his/her student number, which is non-negative integer
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

### Iteration 2

---

* [X] All Iteration 1 requirements
* [X] A section has a subject.
* [X] A subject is identified by its alphanumeric Subject ID.
* [X] A student cannot enlist in two sections with the same subject.
* [ ] A subject may or may not have one or more prerequisite subjects.
* [ ] A student may not enlist in a section if the student has not yet taken the prerequisite subjects.
* [X] Each subject has a corresponding number of units.
* [X] Some subjects may be designated as "laboratory" subjects.
* [ ] A student can request to be assessed, which is simply a request for total amount of money that the student will need to pay. It is computed as follows:

  * [ ] Each unit is ₱2,000
  * [ ] Laboratory subjects have an *additional* ₱1,000 laboratory fee per subject
  * [ ] Miscellaneous fees are ₱3,000
  * [ ] Value Added Tax (VAT) is 12%

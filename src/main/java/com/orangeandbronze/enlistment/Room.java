package com.orangeandbronze.enlistment;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.*;

class Room {
    private final String roomName;
    private final int capacity;
    private final Collection<Section> sections = new HashSet<>();

    Room(String roomName, int capacity, Collection<Section> sections) {
        Validate.notNull(roomName);
        Validate.isTrue(StringUtils.isAlphanumeric(roomName), "Room Name must be AlphaNumeric, was " + roomName);
        Validate.isTrue(capacity > 0, "Capacity must be greater than 0, was: " + capacity);

        this.roomName = roomName;
        this.capacity = capacity;

        this.sections.addAll(sections);
        this.sections.removeIf(Objects::isNull);
    }
    Room(String roomName, int capacity) {
        this(roomName,capacity, Collections.emptyList());
    }
    void addSection(Section section) {
        sections.add(section);
    }
    int getCapacity() {
        return capacity;
    }
    Collection<Section> getSections() {
        return new ArrayList<>(sections);
    }

    @Override
    public String toString() {
        return "Room " + roomName; // Room ABC
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return Objects.equals(roomName, room.roomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomName);
    }
}

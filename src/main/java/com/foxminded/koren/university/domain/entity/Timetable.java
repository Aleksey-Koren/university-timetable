package com.foxminded.koren.university.domain.entity;

import java.util.List;

public class Timetable {
    
    private TimetablePerson person;
    private List<TimetableEvent> events;
    
    public Timetable(TimetablePerson person, List<TimetableEvent> events) {
        super();
        this.person = person;
        this.events = events;
    }

    public TimetablePerson getPerson() {
        return person;
    }

    public void setPerson(TimetablePerson person) {
        this.person = person;
    }

    public List<TimetableEvent> getEvents() {
        return events;
    }

    public void setEvents(List<TimetableEvent> events) {
        this.events = events;
    }
}
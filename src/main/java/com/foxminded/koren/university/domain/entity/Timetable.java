package com.foxminded.koren.university.domain.entity;

import java.util.List;

import com.foxminded.koren.university.domain.entity.interfaces.TimetableEvent;
import com.foxminded.koren.university.domain.entity.interfaces.TimetablePerson;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((events == null) ? 0 : events.hashCode());
        result = prime * result + ((person == null) ? 0 : person.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Timetable))
            return false;
        Timetable other = (Timetable) obj;
        if (events == null) {
            if (other.events != null)
                return false;
        } else if (!events.equals(other.events))
            return false;
        if (person == null) {
            if (other.person != null)
                return false;
        } else if (!person.equals(other.person))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Timetable [person=" + person + ", events=" + events + "]";
    }
}
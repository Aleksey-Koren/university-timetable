package com.foxminded.koren.university.controller.dto;

import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Year;
import com.foxminded.koren.university.entity.interfaces.TimetableEvent;

import java.util.List;

public class GroupDTO {

    private Group group;
    private List<Student> groupStudents;
    private List<TimetableEvent> events;
    private Year[] years;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Student> getGroupStudents() {
        return groupStudents;
    }

    public void setGroupStudents(List<Student> groupStudents) {
        this.groupStudents = groupStudents;
    }

    public List<TimetableEvent> getEvents() {
        return events;
    }

    public void setEvents(List<TimetableEvent> events) {
        this.events = events;
    }

    public Year[] getYears() {
        return years;
    }

    public void setYears(Year[] years) {
        this.years = years;
    }

    public static class Builder {

        private GroupDTO dto = new GroupDTO();

        public Builder group(Group group) {
            dto.group = group;
            return this;
        }

        public Builder students(List<Student> students) {
            dto.groupStudents = students;
            return this;
        }

        public Builder events(List<TimetableEvent> events) {
            dto.events = events;
            return this;
        }

        public Builder years() {
            dto.years = Year.values();
            return this;
        }

        public GroupDTO build() {
            return dto;
        }
    }
}
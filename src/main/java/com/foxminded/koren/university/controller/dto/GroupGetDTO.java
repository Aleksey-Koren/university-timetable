package com.foxminded.koren.university.controller.dto;

import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Year;
import com.foxminded.koren.university.entity.interfaces.TimetableEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GroupGetDTO {

    private Group group;
    private List<Student> groupStudents;
    private List<TimetableEvent> events;
    private Year[] years;
    private List<Student> studentsWithoutGroup;

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

    public List<Student> getStudentsWithoutGroup() {
        return studentsWithoutGroup;
    }

    public void setStudentsWithoutGroup(List<Student> studentsWithoutGroup) {
        this.studentsWithoutGroup = studentsWithoutGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupGetDTO that = (GroupGetDTO) o;
        return Objects.equals(group, that.group) && Objects.equals(groupStudents, that.groupStudents) && Objects.equals(events, that.events) && Arrays.equals(years, that.years) && Objects.equals(studentsWithoutGroup, that.studentsWithoutGroup);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(group, groupStudents, events, studentsWithoutGroup);
        result = 31 * result + Arrays.hashCode(years);
        return result;
    }

    @Override
    public String toString() {
        return "GroupGetDTO{" +
                "group=" + group +
                ", groupStudents=" + groupStudents +
                ", events=" + events +
                ", years=" + Arrays.toString(years) +
                ", studentsWithoutGroup=" + studentsWithoutGroup +
                '}';
    }

    public static class Builder {

        private GroupGetDTO dto = new GroupGetDTO();

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

        public Builder studentsWithoutGroup(List<Student> studentsWithoutGroup) {
            dto.studentsWithoutGroup = studentsWithoutGroup;
            return this;
        }

        public GroupGetDTO build() {
            return dto;
        }
    }
}
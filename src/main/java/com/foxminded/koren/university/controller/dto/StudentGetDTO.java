package com.foxminded.koren.university.controller.dto;

import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Student;

import java.util.List;

public class StudentGetDTO {

    private Student student;
    private Group group;
    private List<Group> allGroups;

    public StudentGetDTO() {

    }

    public StudentGetDTO(List<Group> allGroups) {
        this.allGroups = allGroups;
    }

    public StudentGetDTO(Student student, Group group) {
        this.student = student;
        this.group = group;
    }

    public StudentGetDTO(Student student, List<Group> allGroups) {
        this.student = student;
        this.allGroups = allGroups;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Group> getAllGroups() {
        return allGroups;
    }

    public void setAllGroups(List<Group> allGroups) {
        this.allGroups = allGroups;
    }
}

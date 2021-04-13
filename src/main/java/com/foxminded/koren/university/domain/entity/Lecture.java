package com.foxminded.koren.university.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Lecture implements TimetableEvent {
    
    private int id;
    private Audience audience;
    private Teacher teacher;
    private Course course;
    private List<Group> groups;
    private List<Student> students;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public Lecture(Audience audience,
                   Teacher teacher, 
                   Course course, 
                   List<Group> groups,
                   List<Student> students,
                   LocalDateTime startTime,
                   LocalDateTime endTime) {
        this.audience = audience;
        this.teacher = teacher;
        this.course = course;
        this.groups = groups;
        this.students = students;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Audience getAudience() {
        return audience;
    }
    public void setAudience(Audience audience) {
        this.audience = audience;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public List<Group> getGroups() {
        return groups;
    } 
    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
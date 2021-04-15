package com.foxminded.koren.university.domain.entity;

import java.util.List;

public class Group {
    
    private int id;
    private String name;
    private List<Course> courses;
    
    public Group(String name, List<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
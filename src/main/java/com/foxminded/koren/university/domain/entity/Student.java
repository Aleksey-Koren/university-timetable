package com.foxminded.koren.university.domain.entity;

public class Student implements TimetablePerson {
    private int id;
    private Group group;
    private String firstName;
    private String lastName;
    private Year year;
    
    public Student(Group group, String firstName, String lastName, Year year) {
        super();
        this.group = group;
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
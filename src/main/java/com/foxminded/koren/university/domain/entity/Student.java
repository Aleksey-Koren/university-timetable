package com.foxminded.koren.university.domain.entity;

public class Student implements TimetablePerson {
    private int id;
    private int groupId;
    private String firstName;
    private String lastName;
    private Year year;
    
    public Student(int groupId, String firstName, String lastName, Year year) {
        this.groupId = groupId;
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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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
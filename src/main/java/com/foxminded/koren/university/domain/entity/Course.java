package com.foxminded.koren.university.domain.entity;

public class Course {
    
    private int id;
    private String name;
    private String descrption;
    
    public Course(String name, String descrption) {
        this.name = name;
        this.descrption = descrption;
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

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }   
}
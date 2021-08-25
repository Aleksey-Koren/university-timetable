package com.foxminded.koren.university.entity;

import java.util.Objects;

public class Group {
    
    private int id;
    private String name;
    private Year year;

    public Group() {
        
    }

    public Group(String name, Year year) {
        this.name = name;
        this.year = year;
    }

    public Group(int id, String name, Year year) {
        this.id = id;
        this.name = name;
        this.year = year;
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

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && Objects.equals(name, group.name) && year == group.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
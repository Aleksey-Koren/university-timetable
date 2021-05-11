package com.foxminded.koren.university.domain.entity;

public class Course {
    
    private int id;
    private String name;
    private String descrption;
    
    public Course() {
        
    }
    
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((descrption == null) ? 0 : descrption.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Course))
            return false;
        Course other = (Course) obj;
        if (descrption == null) {
            if (other.descrption != null)
                return false;
        } else if (!descrption.equals(other.descrption))
            return false;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + ", descrption=" + descrption + "]";
    }   
}
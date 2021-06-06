package com.foxminded.koren.university.entity;

public class Audience {
    
    private int id;
    private int number;
    private int capacity;
    
    public Audience(int number, int capacity) {
        this.number = number;
        this.capacity = capacity;
    }

    public Audience() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + capacity;
        result = prime * result + id;
        result = prime * result + number;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Audience))
            return false;
        Audience other = (Audience) obj;
        if (capacity != other.capacity)
            return false;
        if (id != other.id)
            return false;
        if (number != other.number)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Audience [id=" + id + ", number=" + number + ", capacity=" + capacity + "]";
    }
}
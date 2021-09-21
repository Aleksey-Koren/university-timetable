package com.foxminded.koren.university.entity;
import javax.persistence.*;

@Entity
@Table(name = "audience")
public class Audience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "room_number")
    private int number;
    @Column(name = "capacity")
    private int capacity;

    public Audience() {

    }

    public Audience(Integer id) {
        this.id = id;
    }

    public Audience(int number, int capacity) {
        this.number = number;
        this.capacity = capacity;
    }

    public Audience(int id, int number, int capacity) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
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
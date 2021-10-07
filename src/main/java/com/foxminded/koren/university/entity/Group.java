package com.foxminded.koren.university.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    @Enumerated(EnumType.STRING)
    private Year year;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Student> students;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    private List<Lecture> lectures;

    public Group() {
        
    }

    public Group(int id) {
        this.id = id;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && Objects.equals(name, group.name) && year == group.year /* && Objects.equals(students, group.students)*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, students);
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
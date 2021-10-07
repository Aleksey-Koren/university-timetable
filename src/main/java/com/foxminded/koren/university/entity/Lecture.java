package com.foxminded.koren.university.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.foxminded.koren.university.entity.interfaces.TimetableEvent;

import javax.persistence.*;

@Entity
@Table(name = "lecture")
public class Lecture implements TimetableEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "audience_id")
    private Audience audience;

    @ManyToOne(optional = true)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(optional = true)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "lecture_group",
               joinColumns = @JoinColumn(name = "lecture_id"),
               inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups;

    public Lecture() {
        
    }

    public Lecture(int id) {
        this.id = id;
    }

    public Lecture(Audience audience,
                   Teacher teacher,
                   Course course,
                   LocalDateTime startTime,
                   LocalDateTime endTime) {
        this.audience = audience;
        this.teacher = teacher;
        this.course = course;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Lecture(int id,
                   Audience audience,
                   Teacher teacher,
                   Course course,
                   LocalDateTime startTime,
                   LocalDateTime endTime) {
        this.id = id;
        this.audience = audience;
        this.teacher = teacher;
        this.course = course;
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
    public List<Group> getGroups() {
        return groups;
    }
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return id == lecture.id && Objects.equals(audience, lecture.audience) && Objects.equals(teacher, lecture.teacher) && Objects.equals(course, lecture.course) && Objects.equals(startTime, lecture.startTime) && Objects.equals(endTime, lecture.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, audience, teacher, course, startTime, endTime);
    }

    @Override
    public String toString() {
        return "Lecture [id=" + id + ", audience=" + audience + ", teacher=" + teacher + ", course=" + course
                + ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }

    public static class Builder {

        private Lecture lecture;

        public Builder() {
            lecture = new Lecture();
        }

        public Builder id(int id) {
            lecture.setId(id);
            return this;
        }

        public Builder audience(Audience audience) {
            lecture.audience = audience;
            return this;
        }

        public Builder teacher(Teacher teacher) {
            lecture.teacher = teacher;
            return this;
        }

        public Builder course(Course course) {
            lecture.course = course;
            return this;
        }

        public Builder startTime(LocalDateTime startTime) {
            lecture.startTime = startTime;
            return this;
        }

        public Builder endTime(LocalDateTime endTime) {
            lecture.endTime = endTime;
            return this;
        }

        public Lecture build() {
            return lecture;
        }
    }
}
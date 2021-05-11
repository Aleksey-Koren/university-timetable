package com.foxminded.koren.university.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.foxminded.koren.university.domain.entity.interfaces.TimetableEvent;

public class Lecture implements TimetableEvent {
    
    private int id;
    private Audience audience;
    private Teacher teacher;
    private Course course;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public Lecture() {
        
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((audience == null) ? 0 : audience.hashCode());
        result = prime * result + ((course == null) ? 0 : course.hashCode());
        result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
        result = prime * result + id;
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
        result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Lecture))
            return false;
        Lecture other = (Lecture) obj;
        if (audience == null) {
            if (other.audience != null)
                return false;
        } else if (!audience.equals(other.audience))
            return false;
        if (course == null) {
            if (other.course != null)
                return false;
        } else if (!course.equals(other.course))
            return false;
        if (endTime == null) {
            if (other.endTime != null)
                return false;
        } else if (!endTime.equals(other.endTime))
            return false;
        if (id != other.id)
            return false;
        if (startTime == null) {
            if (other.startTime != null)
                return false;
        } else if (!startTime.equals(other.startTime))
            return false;
        if (teacher == null) {
            if (other.teacher != null)
                return false;
        } else if (!teacher.equals(other.teacher))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Lecture [id=" + id + ", audience=" + audience + ", teacher=" + teacher + ", course=" + course
                + ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }
}
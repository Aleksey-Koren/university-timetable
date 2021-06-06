package com.foxminded.koren.university.entity.interfaces;

import java.time.LocalDateTime;

import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.entity.Teacher;

public interface TimetableEvent {
    
    int getId();
    
    void setId(int id);
    
    Audience getAudience();
    
    void setAudience(Audience audience);
    
    Teacher getTeacher();
    
    void setTeacher(Teacher teacher);

    Course getCourse();
    
    void setCourse(Course course);

    LocalDateTime getStartTime();
    
    void setStartTime(LocalDateTime startTime);
    
    LocalDateTime getEndTime();
    
    void setEndTime(LocalDateTime endTime);
    
    boolean equals(Object o);
    
    int hashCode();
    
    String toString();
}
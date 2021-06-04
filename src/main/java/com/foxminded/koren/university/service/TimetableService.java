package com.foxminded.koren.university.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.interfaces.LectureDao;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.entity.Timetable;
import com.foxminded.koren.university.entity.interfaces.TimetableEvent;
import com.foxminded.koren.university.entity.interfaces.TimetablePerson;
import com.foxminded.koren.university.service.comporators.TimetableStartTimeComparator;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class TimetableService {
    
    @Autowired
    private LectureDao lectureDao;
    
    public Timetable getTmetableByPersonId (TimetablePerson person, LocalDate start, LocalDate finish) {  
        if (person instanceof Teacher) {
            return getTeacherTimetableByPeriod((Teacher) person, start, finish);      
        }  
        
        if (person instanceof Student) {
            return getStudentTimetableByPeriod((Student) person, start, finish);
        }
        
        throw new ServiceException("Unexpected type of TimetablePerson");
    }
    
    public Timetable getTeacherTimetableByPeriod(Teacher teacher, LocalDate start, LocalDate finish) {
        List<Lecture> lectures = lectureDao.getTeacherLecturesByTimePeriod(teacher, start, finish);
        List<TimetableEvent> allEvents = agregateAllToTimetableEvents(lectures);
        allEvents.sort(new TimetableStartTimeComparator());
        return new Timetable(teacher, allEvents); 
    }
    
    public Timetable getStudentTimetableByPeriod(Student student, LocalDate start, LocalDate finish) {
        List<Lecture> lectures = lectureDao.getStudentLecturesByTimePeriod(student, start, finish);
        List<TimetableEvent> allEvents = agregateAllToTimetableEvents(lectures);
        allEvents.sort(new TimetableStartTimeComparator());
        return new Timetable(student, allEvents);        
    }
    
    private List<TimetableEvent> castToTimetableEvents(List<? extends TimetableEvent> events) {
        return events.stream()
                     .map(TimetableEvent.class::cast)
                     .collect(Collectors.toList());
    }
    
    @SafeVarargs
    private List<TimetableEvent> agregateAllToTimetableEvents(List<? extends TimetableEvent>... events){
        List<TimetableEvent> allEvents = new ArrayList<>();
        for(int i = 0; i < events.length; i++) {
            List<TimetableEvent> casted = castToTimetableEvents(events[i]);
            allEvents.addAll(casted);
        }
        return allEvents;
    }
}
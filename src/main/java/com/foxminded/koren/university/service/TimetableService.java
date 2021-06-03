package com.foxminded.koren.university.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.interfaces.LectureDao;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.entity.Timetable;
import com.foxminded.koren.university.entity.interfaces.TimetableEvent;
import com.foxminded.koren.university.entity.interfaces.TimetablePerson;
import com.foxminded.koren.university.service.comporators.TimetableTimeComparator;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class TimetableService {
    
    @Autowired
    LectureDao lectureDao;
    
    public Timetable getTmetableByPersonId (TimetablePerson person, LocalDate start, LocalDate finish) {
        
        if (person instanceof Teacher) {
            return getTeacherTimetableByPeriod((Teacher) person, start, finish);
            
        }
        
        if (person instanceof Student) {
            return new Timetable(person, lectureDao.getLecturesByStudentAndTimePeriod((Student) person, start, finish));
        }
        
        throw new ServiceException("Unexpected type of TimetablePerson");
    }
    
    public Timetable getTeacherTimetableByPeriod(Teacher teacher, LocalDate start, LocalDate finish) {
        List<TimetableEvent> lectures = lectureDao.getLecturesByTeacherAndTimePeriod(teacher, start, finish);
        
        List<TimetableEvent> allEvents = new ArrayList<>();
        allEvents.addAll(lectures);
        allEvents.sort(new TimetableTimeComparator());
        return new Timetable(teacher, allEvents);
    }
    
    public Timetable getStudentTimetableByPeriod(Student student, LocalDate start, LocalDate finish) {
        return new Timetable(student, lectureDao.getLecturesByStudentAndTimePeriod(student, start, finish));
    }
}
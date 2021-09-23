package com.foxminded.koren.university.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.repository.interfaces.LectureDao;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.entity.Timetable;
import com.foxminded.koren.university.entity.interfaces.TimetableEvent;
import com.foxminded.koren.university.service.comporators.TimetableStartTimeComparator;

@Service
public class
TimetableService {
    
    private static final Logger LOG = LoggerFactory.getLogger(TimetableService.class);
    
    @Autowired
    private LectureDao lectureDao;
        
    public Timetable getTeacherTimetableByPeriod(Teacher teacher, LocalDate start, LocalDate finish) {
        List<Lecture> lectures = lectureDao.getTeacherLecturesByTimePeriod(teacher, start, finish);
        
        LOG.debug("List of lectutes for teacher.id = {}, start = {}, finish = {}",
                teacher.getId(), start, finish);
        
        List<TimetableEvent> allEvents = agregateAllToTimetableEvents(lectures);
        allEvents.sort(new TimetableStartTimeComparator());
        return new Timetable(teacher, allEvents); 
    }
    
    public Timetable getStudentTimetableByPeriod(Student student, LocalDate start, LocalDate finish) {
        List<Lecture> lectures = lectureDao.getStudentLecturesByTimePeriod(student, start, finish);
        
        LOG.debug("List of lectutes for student.id = {}, start = {}, finish = {}",
                student.getId(), start, finish);

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
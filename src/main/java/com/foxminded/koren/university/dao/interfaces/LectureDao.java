package com.foxminded.koren.university.dao.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.entity.interfaces.TimetableEvent;

public interface LectureDao extends CrudDao<Integer, TimetableEvent> {
    
    List<TimetableEvent> getLecturesByTeacherAndTimePeriod(Teacher teacher, LocalDate start, LocalDate finish);

    List<TimetableEvent> getLecturesByStudentAndTimePeriod(Student student, LocalDate start, LocalDate finish);
}
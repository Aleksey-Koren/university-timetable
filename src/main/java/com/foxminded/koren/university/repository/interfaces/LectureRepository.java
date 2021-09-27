package com.foxminded.koren.university.repository.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Teacher;

public interface LectureRepository extends CrudRepository<Integer, Lecture> {
    
    List<Lecture> getTeacherLecturesByTimePeriod(Teacher teacher, LocalDate start, LocalDate finish);

    List<Lecture> getStudentLecturesByTimePeriod(Student student, LocalDate start, LocalDate finish);

    boolean removeGroup(int lectureId, int groupId);

    boolean addGroup(int lectureId, int groupId);
}
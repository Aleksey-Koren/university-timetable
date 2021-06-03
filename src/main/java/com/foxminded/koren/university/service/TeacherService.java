package com.foxminded.koren.university.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.interfaces.LectureDao;
import com.foxminded.koren.university.dao.interfaces.TeacherDao;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.entity.Timetable;
import com.foxminded.koren.university.entity.interfaces.TimetableEvent;
import com.foxminded.koren.university.entity.interfaces.TimetablePerson;

@Service
public class TeacherService {
    
    @Autowired
    private TeacherDao teacherDao;
    
    @Autowired
    private LectureDao lectureDao;
    
    public Teacher createNew(Teacher teacher) {
        return teacherDao.save(teacher);
    }
    
    public void update(Teacher teacher) {
        teacherDao.update(teacher);
    }
    
    public boolean deleteById(int id) {
        return teacherDao.deleteById(id);
    }
    
    public Teacher getById(int id) {
        return teacherDao.getById(id);
    }
    
    public List<Teacher> getAll() {
        return teacherDao.getAll();
    }
}
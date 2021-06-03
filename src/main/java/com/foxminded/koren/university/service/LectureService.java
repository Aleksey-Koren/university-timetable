package com.foxminded.koren.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.foxminded.koren.university.dao.interfaces.LectureDao;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.interfaces.TimetableEvent;

public class LectureService {
    
    @Autowired
    private LectureDao lectureDao;
    
    public TimetableEvent createNew(Lecture lecture) {
        return  lectureDao.save(lecture);
    }
    
    public void update(Lecture lecture) {
        lectureDao.update(lecture);
    }
    
    public boolean deleteById(int id) {
        return lectureDao.deleteById(id);
    }
    
    public TimetableEvent getById(int id) {
        return lectureDao.getById(id);
    }
    
    public List<TimetableEvent> getAll() {
        return lectureDao.getAll();
    }
}
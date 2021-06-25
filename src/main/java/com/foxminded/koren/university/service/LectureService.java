package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.interfaces.LectureDao;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.interfaces.TimetableEvent;

@Service
public class LectureService {
    
    private static final Logger LOG = LoggerFactory.getLogger(LectureService.class);
    
    @Autowired
    private LectureDao lectureDao;
    
    public TimetableEvent createNew(Lecture lecture) {
        LOG.debug("Create new lecture: {}", lecture);
        return  lectureDao.save(lecture);
    }
    
    public void update(Lecture lecture) {
        LOG.debug("Update lecture: {}", lecture);
        lectureDao.update(lecture);
    }
    
    public boolean deleteById(int id) {
        LOG.debug("Delete lecture by id = {}", id);
        return lectureDao.deleteById(id);
    }
    
    public TimetableEvent getById(int id) {
        LOG.debug("Get lecture by id = {}", id);
        return lectureDao.getById(id);
    }
    
    public List<Lecture> getAll() {
        LOG.debug("Get all lectures");
        return lectureDao.getAll();
    }
}
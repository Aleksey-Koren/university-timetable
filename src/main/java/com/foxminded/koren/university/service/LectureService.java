package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.LectureDao;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class LectureService {
    
    private static final Logger LOG = LoggerFactory.getLogger(LectureService.class);
    
    @Autowired
    private LectureDao lectureDao;
    
    public Lecture createNew(Lecture lecture) {
        try {
            LOG.debug("Create new lecture: {}", lecture);
            return lectureDao.save(lecture);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void update(Lecture lecture) {
        LOG.debug("Update lecture: {}", lecture);
        lectureDao.update(lecture);
    }
    
    public boolean deleteById(int id) {
        LOG.debug("Delete lecture by id = {}", id);
        return lectureDao.deleteById(id);
    }
    
    public Lecture getById(int id) {
        try {
            LOG.debug("Get lecture by id = {}", id);
            return lectureDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public List<Lecture> getAll() {
        LOG.debug("Get all lectures");
        return lectureDao.getAll();
    }
}
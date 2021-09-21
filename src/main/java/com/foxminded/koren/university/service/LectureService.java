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

    private LectureDao lectureDao;

    @Autowired
    public LectureService(LectureDao lectureDao) {
        this.lectureDao = lectureDao;
    }

    public Lecture createNew(Lecture lecture) {
        try {
            LOG.debug("Create new lecture");
            return lectureDao.save(lecture);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void update(Lecture lecture) {
        LOG.debug("Update lecture id = {}", lecture.getId());
        try {
            lectureDao.update(lecture);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void deleteById(int id) {
        LOG.debug("Delete lecture by id = {}", id);
        try {
            lectureDao.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
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

    public boolean removeGroup(int lectureId, int groupId) {
        LOG.debug("Remove group id = {} from lecture id = {}", groupId, lectureId);
        try {
            return lectureDao.removeGroup(lectureId, groupId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public boolean addGroup(int lectureID, int groupId) {
        LOG.debug("Add group id = {} to lecture id = {}", groupId, lectureID);
        try {
            return lectureDao.addGroup(lectureID,groupId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
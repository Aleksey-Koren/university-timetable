package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.TeacherDao;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class TeacherService {
    
    private static final Logger LOG = LoggerFactory.getLogger(TeacherService.class);
    
    private TeacherDao teacherDao;

    @Autowired
    public TeacherService(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public Teacher createNew(Teacher teacher) {
        LOG.debug("Create new teacher: {}", teacher);
        try {
            return teacherDao.save(teacher);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void update(Teacher teacher) {
        LOG.debug("Update teacher: {}", teacher);
        try {
            teacherDao.update(teacher);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void deleteById(int id) {
        LOG.debug("Delete teacher by id = {}", id);
        try {
            teacherDao.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);

        }
    }
    
    public Teacher getById(int id) {
        LOG.debug("Get teacher by id = {}", id);
        try {
            return teacherDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public List<Teacher> getAll() {
        LOG.debug("Get all teachers");
        try {
            return teacherDao.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.CourseDao;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class CourseService {
    
    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);
    
    @Autowired
    private CourseDao courseDao;
    
    public Course createNew(Course course) {
        try {
            LOG.debug("Save new course: {}", course);
            return courseDao.save(course);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }
    
    public void update(Course course) {
        try {
            LOG.debug("Update course: {}", course);
            courseDao.update(course);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void deleteById(int id) {
        LOG.debug("Delete course by id = {}", id);
        try {
            courseDao.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public Course getById(int id) {
        try {
            LOG.debug("Get course by id = {}", id);
            return courseDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public List<Course> getAll() {
        LOG.debug("Get all courses");
        try {
            return courseDao.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.interfaces.CourseDao;
import com.foxminded.koren.university.entity.Course;

@Service
public class CourseService {
    
    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);
    
    @Autowired
    private CourseDao courseDao;
    
    public Course createNew(Course course) {
        LOG.debug("Save new course: {}", course);
        return courseDao.save(course);
    }
    
    public void update(Course course) {
        LOG.debug("Update course: {}", course);
        courseDao.update(course);
    }
    
    public boolean deleteById(int id) {
        LOG.debug("Delete course by id = {}", id);
        return courseDao.deleteById(id);
    }
    
    public Course getById(int id) {
        LOG.debug("Get course by id = {}", id);
        return courseDao.getById(id);
    }
    
    public List<Course> getAll() {
        LOG.debug("Get all courses");
        return courseDao.getAll();
    }
}
package com.foxminded.koren.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.interfaces.CourseDao;
import com.foxminded.koren.university.entity.Course;

@Service
public class CourseService {
    
    @Autowired
    private CourseDao courseDao;
    
    public Course createNew(Course course) {
        return courseDao.save(course);
    }
    
    public void update(Course course) {
        courseDao.update(course);
    }
    
    public boolean deleteById(int id) {
        return courseDao.deleteById(id);
    }
    
    public Course getById(int id) {
        return courseDao.getById(id);
    }
    
    public List<Course> getAll() {
        return courseDao.getAll();
    }
}
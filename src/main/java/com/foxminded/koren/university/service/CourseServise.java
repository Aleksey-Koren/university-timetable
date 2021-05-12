package com.foxminded.koren.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.JdbcCourseDao;
import com.foxminded.koren.university.domain.entity.Course;

@Service
public class CourseServise {
    
    @Autowired
    private JdbcCourseDao jdbcCourseDao;
    
    public void createNewCourse(Course course) {
        jdbcCourseDao.save(course);
    }
    
    public void createNewCourse(String name, String description) {
        Course course = new Course(name, description);
        jdbcCourseDao.save(course);
    } 
}
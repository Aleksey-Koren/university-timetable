package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.CourseRepository;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class CourseService {
    
    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(@Qualifier("courseRepositoryImpl") CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createNew(Course course) {
        try {
            LOG.debug("Save new course: {}", course);
            return courseRepository.save(course);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }
    
    public void update(Course course) {
        try {
            LOG.debug("Update course: {}", course);
            courseRepository.update(course);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void deleteById(int id) {
        LOG.debug("Delete course by id = {}", id);
        try {
            courseRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public Course getById(int id) {
        try {
            LOG.debug("Get course by id = {}", id);
            return courseRepository.getById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public List<Course> getAll() {
        LOG.debug("Get all courses");
        try {
            return courseRepository.getAll();
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
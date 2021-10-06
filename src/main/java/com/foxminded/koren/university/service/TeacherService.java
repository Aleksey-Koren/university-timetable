package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.TeacherRepository;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class TeacherService {
    
    private static final Logger LOG = LoggerFactory.getLogger(TeacherService.class);
    
    private TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(@Qualifier("teacherRepositoryImpl") TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Teacher createNew(Teacher teacher) {
        LOG.debug("Create new teacher: {}", teacher);
        try {
            return teacherRepository.save(teacher);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void update(Teacher teacher) {
        LOG.debug("Update teacher: {}", teacher);
        try {
            teacherRepository.update(teacher);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void deleteById(int id) {
        LOG.debug("Delete teacher by id = {}", id);
        try {
            teacherRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);

        }
    }
    
    public Teacher getById(int id) {
        LOG.debug("Get teacher by id = {}", id);
        try {
            return teacherRepository.getById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public List<Teacher> getAll() {
        LOG.debug("Get all teachers");
        try {
            return teacherRepository.getAll();
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
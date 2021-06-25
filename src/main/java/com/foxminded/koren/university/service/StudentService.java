package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.interfaces.StudentDao;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Year;

@Service
public class StudentService {
    
    private static final Logger LOG = LoggerFactory.getLogger(StudentService.class);
    
    @Autowired
    private StudentDao studentDao;
    
    public Student createNew(Student student) {
        LOG.debug("Create new student: {}", student);
        return studentDao.save(student);
    }
    
    public void update(Student student) {
        LOG.debug("Update student: {}", student);
        studentDao.update(student);
    }
    
    public boolean deleteById(int id) {
        LOG.debug("Delete student by id = {}", id);
        return studentDao.deleteById(id);
    }
    
    public Student getById(int id) {
        LOG.debug("Get student by id = {}", id);
        return studentDao.getById(id);
    }
    
    public List<Student> getAll() {
        LOG.debug("Get all students");
        return studentDao.getAll();
    }
    
    public void changeYear(Student student, Year year) {
        LOG.debug("Change student's year. student.id = {}, Year = {}", student.getId(), year);
        student.setYear(year);
        studentDao.update(student);
    }
}
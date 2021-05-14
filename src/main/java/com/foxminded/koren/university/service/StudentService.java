package com.foxminded.koren.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.foxminded.koren.university.dao.interfaces.StudentDao;
import com.foxminded.koren.university.domain.entity.Student;
import com.foxminded.koren.university.domain.entity.Year;

public class StudentService {
    
    @Autowired
    private StudentDao studentDao;
    
    public Student createNew(Student student) {
        return studentDao.save(student);
    }
    
    public void update(Student student) {
        studentDao.update(student);
    }
    
    public boolean deleteById(int id) {
        return studentDao.deleteById(id);
    }
    
    public Student getById(int id) {
        return studentDao.getById(id);
    }
    
    public List<Student> getAll() {
        return studentDao.getAll();
    }
    
    public void changeYear(Student student, Year year) {
        student.setYear(year);
        studentDao.update(student);
    }
}
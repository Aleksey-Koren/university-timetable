package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.StudentDao;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class StudentService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentService.class);


    private StudentDao studentDao;

    @Autowired
    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public Student createNew(Student student) {
        LOG.debug("Create new student: {}", student);
        try {
            return studentDao.save(student);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void update(Student student) {
        LOG.debug("Update student: {}", student);
        try {
            studentDao.update(student);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void deleteById(int id) {
        LOG.debug("Delete student by id = {}", id);
        try {
            studentDao.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Student getById(int id) {
        try {
            LOG.debug("Get student by id = {}", id);
            return studentDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Student> getAll() {
        LOG.debug("Get all students");
        try {
            return studentDao.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Student> getByGroupId(int id) {
        LOG.debug("Get all students by group id = {}", id);
        try {
            return studentDao.getByGroupId(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Student> getAllWithoutGroup() {
        LOG.debug("Get all students without group");
        try {
            return studentDao.getAllWithoutGroup();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public boolean addStudentToGroup(int studentId, int groupId) {
        LOG.debug("Add student id = {} to group id = {}", studentId, groupId);
        try {
            return studentDao.addStudentToGroup(studentId, groupId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public boolean removeStudentFromGroup(int studentId) {
        LOG.debug("Remove student id = {} from group", studentId);
        try {
            return studentDao.removeStudentFromGroup(studentId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
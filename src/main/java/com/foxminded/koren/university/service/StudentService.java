package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.StudentRepository;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class StudentService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentService.class);

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(@Qualifier("studentRepositoryImpl") StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createNew(Student student) {
        LOG.debug("Create new student: {}", student);
        try {
            return studentRepository.save(student);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void update(Student student) {
        LOG.debug("Update student: {}", student);
        try {
            studentRepository.update(student);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void deleteById(int id) {
        LOG.debug("Delete student by id = {}", id);
        try {
            studentRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Student getById(int id) {
        try {
            LOG.debug("Get student by id = {}", id);
            return studentRepository.getById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Student> getAll() {
        LOG.debug("Get all students");
        try {
            return studentRepository.getAll();
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Student> getByGroupId(int id) {
        LOG.debug("Get all students by group id = {}", id);
        try {
            return studentRepository.getByGroupId(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Student> getAllWithoutGroup() {
        LOG.debug("Get all students without group");
        try {
            return studentRepository.getAllWithoutGroup();
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public boolean addStudentToGroup(int studentId, int groupId) {
        LOG.debug("Add student id = {} to group id = {}", studentId, groupId);
        try {

            return studentRepository.addStudentToGroup(studentId, groupId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public boolean removeStudentFromGroup(int studentId) {
        LOG.debug("Remove student id = {} from group", studentId);
        try {
            return studentRepository.removeStudentFromGroup(studentId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
package com.foxminded.koren.university.repository;

import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private static final Logger LOG = LoggerFactory.getLogger(StudentRepositoryImpl.class);

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public StudentRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Student save(Student entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Starting to save new student");
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
        LOG.trace("Student has gotten id = {}", entity.getId());
        return entity;
    }

    @Override
    public void update(Student entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Updating student id = {}", entity.getId());
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Deleting student id = {}", id);
        Student student = entityManager.find(Student.class, id);
        if (student == null) {
            throw new RepositoryException(String
                    .format("Unable to delete student id = %s, cause: there is no such student in database", id));
        }
        entityManager.remove(student);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Student getById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Student student = entityManager.find(Student.class, id);
        if (student == null) {
            throw new RepositoryException(String
                    .format("Unable to get student with id = %s, cause: there is no student with such id in database", id));
        }
        entityManager.close();
        return student;
    }

    @Override
    public List<Student> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Student> students = entityManager
                .createQuery("FROM Student order by lastName", Student.class).getResultList();
        entityManager.close();
        return students;
    }

    @Override
    public List<Student> getByGroupId(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Group group = entityManager.find(Group.class, id);
        List<Student> studentsOfGroup = new ArrayList<>(group.getStudents());
        return studentsOfGroup;
    }

    @Override
    public List<Student> getAllWithoutGroup() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Student> studentsWithoutGroup = entityManager
                .createQuery("FROM Student s WHERE s.group is NULL", Student.class).getResultList();
        entityManager.close();
        return studentsWithoutGroup;
    }

    @Override
    public void addStudentToGroup(int studentId, int groupId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Student studentToAdd = entityManager.find(Student.class, studentId);
        Group group = entityManager.find(Group.class, groupId);
        studentToAdd.setGroup(group);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void removeStudentFromGroup(int studentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Student studentToAdd = entityManager.find(Student.class, studentId);
        studentToAdd.setGroup(null);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

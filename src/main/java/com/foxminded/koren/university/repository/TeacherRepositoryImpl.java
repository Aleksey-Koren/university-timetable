package com.foxminded.koren.university.repository;

import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class TeacherRepositoryImpl implements TeacherRepository {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherRepositoryImpl.class);

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public TeacherRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Teacher save(Teacher entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Starting to save new teacher");
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
        LOG.trace("Teacher has gotten id = {}", entity.getId());
        return entity;
    }

    @Override
    public void update(Teacher entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Updating teacher id = {}", entity.getId());
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Deleting teacher id = {}", id);
        Teacher teacher = entityManager.find(Teacher.class, id);
        if (teacher == null) {
            throw new RepositoryException(String
                    .format("Unable to delete teacher id = %s, cause: there is no such teacher in database", id));
        }
        entityManager.remove(teacher);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Teacher getById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Teacher teacher = entityManager.find(Teacher.class, id);
        if (teacher == null) {
            throw new RepositoryException(String
                    .format("Unable to get teacher with id = %s, cause: there is no teacher with such id in database", id));
        }
        entityManager.close();
        return teacher;
    }

    @Override
    public List<Teacher> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Teacher> teachers = entityManager
                .createQuery("FROM Teacher order by lastName", Teacher.class).getResultList();
        entityManager.close();
        return teachers;
    }
}

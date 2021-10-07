package com.foxminded.koren.university.repository;

import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.repository.interfaces.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class CourseRepositoryImpl implements CourseRepository {

    private final static Logger LOG = LoggerFactory.getLogger(CourseRepositoryImpl.class);

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public CourseRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Course> getByGroup(Group group) {
        return null;
    }

    @Override
    public Course save(Course entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Starting to persist new course {}", entity.toString());
        entityManager.merge(entity);
        LOG.trace("Course has gotten id = {}", entity.getId());
        entityManager.getTransaction().commit();
        entityManager.close();
        return entity;
    }

    @Override
    public void update(Course entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Updating course {}", entity.toString());
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        LOG.trace("Trying to delete course id = {}", id);
        entityManager.getTransaction().begin();
        Course course = entityManager.find(Course.class, id);
        if (course == null) {
            throw new RepositoryException(String
                    .format("Unable to delete course with id = %s, cause: there is no course with such id in database", id));
        }
        entityManager.remove(course);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Course getById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        LOG.trace("Getting course with id = {}", id);
        Course course = entityManager.find(Course.class, id);
        if(course == null) {
            throw new RepositoryException(String
                    .format("Unable to get course with id = %s, cause: there is no course with such id in database", id));
        }
        entityManager.close();
        return course;
    }

    @Override
    public List<Course> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        LOG.trace("Starting to get all courses from database");
        List<Course> courses = entityManager
                .createQuery("FROM Course order by name", Course.class).getResultList();
        entityManager.close();
        return courses;
    }
}

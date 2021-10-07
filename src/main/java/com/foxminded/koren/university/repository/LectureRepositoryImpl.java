package com.foxminded.koren.university.repository;

import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.LectureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;

@Repository
public class LectureRepositoryImpl implements LectureRepository {

    private EntityManagerFactory entityManagerFactory;

    private Logger LOG = LoggerFactory.getLogger(LectureRepositoryImpl.class);

    @Autowired
    public LectureRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Lecture save(Lecture entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Saving new lecture");
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        LOG.trace("Lecture has gotten id = {}", entity.getId());
        entityManager.close();
        return entity;
    }

    @Override
    public void update(Lecture entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Updating lecture id = {}", entity.getId());
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Deleting lecture by id = {}", id);
        Lecture lecture = entityManager.find(Lecture.class, id);
        if (lecture == null) {
            throw new RepositoryException(String
                    .format("Unable to delete lecture with id = %s, cause: there is no lecture with such id in database", id));
        }
        entityManager.remove(lecture);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Lecture getById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Getting lecture by id = {}", id);
        Lecture lecture = entityManager.find(Lecture.class, id);
        if (lecture == null) {
            throw new RepositoryException(String
                    .format("Unable to get lecture with id = %s, cause: there is no lecture with such id in database", id));
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return lecture;
    }

    @Override
    public List<Lecture> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Getting all lectures");
        List<Lecture> lectures = entityManager.createQuery("FROM Lecture order by startTime", Lecture.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return lectures;
    }

    @Override
    public List<Lecture> getTeacherLecturesByTimePeriod(Teacher teacher, LocalDate start, LocalDate finish) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Start getting lectures by teacher id = {} and time period start = {} finish = {}",
                teacher.getId(), start, finish);
        List<Lecture> lectures = entityManager.createQuery("SELECT l FROM Lecture l " +
                        "WHERE l.teacher = :teacher " +
                        "AND l.startTime >= :start " +
                        "AND l.startTime <= :finish", Lecture.class)
                        .setParameter("teacher", teacher)
                        .setParameter("start", start.atTime(0,0))
                        .setParameter("finish", finish.atTime(23,59,59))
                        .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return lectures;
    }

    @Override
    public List<Lecture> getStudentLecturesByTimePeriod(Student student, LocalDate start, LocalDate finish) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Start getting lectures by student id = {} and time period start = {} finish = {}",
                student.getId(), start, finish);
        List<Lecture> lectures = entityManager.createQuery("SELECT l FROM Lecture l " +
                        "JOIN l.groups g " +
                        "JOIN g.students s " +
                        "WHERE s = :student " +
                        "AND l.startTime >= :start " +
                        "AND l.startTime <= :finish", Lecture.class)
                .setParameter("student", student)
                .setParameter("start", start.atTime(0,0))
                .setParameter("finish", finish.atTime(23,59,59))
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return lectures;
    }

    @Override
    public void removeGroup(int lectureId, int groupId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Removing group id = {} from lecture id = {}", groupId, lectureId);
                Lecture lecture = entityManager.find(Lecture.class, lectureId);
        boolean isDeleted = lecture.getGroups().removeIf(s -> s.getId() == groupId);
        if (!isDeleted) {
            throw new RepositoryException(String
                    .format("Can't delete group id = %s from lecture id = %s. Cause: lecture doesn't contains such group",
                            groupId, lectureId));
        }
        entityManager.merge(lecture);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void addGroup(int lectureId, int groupId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Adding group id = {} to lecture id = {}", groupId, lectureId);
        Lecture lecture = entityManager.find(Lecture.class, lectureId);
        Group group = entityManager.find(Group.class, groupId);
        lecture.getGroups().add(group);
        entityManager.merge(lecture);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

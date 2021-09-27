package com.foxminded.koren.university.repository;

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
        return null;
    }

    @Override
    public void update(Lecture entity) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Lecture getById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
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
        return null;
    }

    @Override
    public List<Lecture> getTeacherLecturesByTimePeriod(Teacher teacher, LocalDate start, LocalDate finish) {
        return null;
    }

    @Override
    public List<Lecture> getStudentLecturesByTimePeriod(Student student, LocalDate start, LocalDate finish) {
        return null;
    }

    @Override
    public boolean removeGroup(int lectureId, int groupId) {
        return false;
    }

    @Override
    public boolean addGroup(int lectureId, int groupId) {
        return false;
    }
}

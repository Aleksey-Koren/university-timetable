package com.foxminded.koren.university.repository;

import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class GroupRepositoryImpl implements GroupRepository {

    EntityManagerFactory entityManagerFactory;

    private static final Logger LOG = LoggerFactory.getLogger(GroupRepositoryImpl.class);

    public GroupRepositoryImpl() {

    }

    @Autowired
    public GroupRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public Group save(Group entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Starting to persist new group {}", entity.toString());
        entityManager.persist(entity);
        LOG.trace("Group has gotten id = {}", entity.getId());
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public void update(Group entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Updating group {}", entity.toString());
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Deleting group with id = {}", id);
        Group group = entityManager.find(Group.class, id);
        if (group == null) {
            throw new RepositoryException(String
                    .format("Unable to delete group with id = %s, cause: there is no group with such id in database", id));
        }
    }

    @Override
    public Group getById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Getting group with id = {}", id);
        Group group = entityManager.find(Group.class, id);
        if (group == null) {
            throw new RepositoryException(String
                    .format("Unable to get group with id = %s, cause: there is no group with such id in database", id));
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return group;
    }

    @Override
    public List<Group> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        LOG.trace("Getting all groups from database");
        entityManager.getTransaction().begin();
        List<Group> groups = entityManager.createQuery("FROM Group order by name", Group.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return groups;
    }

    @Override
    public List<Group> getGroupsByLectureId(Integer lectureId) {
        return null;
    }

    @Override
    public List<Group> getAllGroupsExceptAddedToLecture(int lectureId) {
        return null;
    }
}

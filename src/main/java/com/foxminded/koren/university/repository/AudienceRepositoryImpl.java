package com.foxminded.koren.university.repository;

import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.repository.interfaces.AudienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class AudienceRepositoryImpl implements AudienceRepository {

    private EntityManagerFactory entityManagerFactory;

    private static final Logger LOG = LoggerFactory.getLogger(AudienceRepositoryImpl.class);

    public AudienceRepositoryImpl() {

    }

    @Autowired
    public AudienceRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Audience save(Audience entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Starting to persist new audience {}", entity.toString());
        entityManager.persist(entity);
        LOG.trace("Audience has gotten id = {}", entity.getId());
        entityManager.getTransaction().commit();
        entityManager.close();
        return entity;
    }

    @Override
    public void update(Audience entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Updating audience {}", entity.toString());
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        LOG.trace("Trying to delete audience id = {}", id);
        Audience audience = entityManager.find(Audience.class, id);
        if (audience == null) {
            throw new RepositoryException(String
                    .format("Unable to delete audience with id = %s, cause: there is no audience with such id in database", id));
        }
        entityManager.getTransaction().begin();
        entityManager.remove(audience);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Audience getById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Getting audience with id = {}", id);
        Audience audience = entityManager.find(Audience.class, id);
        if(audience == null) {
            throw new RepositoryException(String
                    .format("Unable to get audience with id = %s, cause: there is no audience with such id in database",
                    id));
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return audience;
    }

    @Override
    public List<Audience> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        LOG.trace("Starting to get all audiences from database");
        List<Audience> audiences = entityManager
                .createQuery("FROM Audience order by number", Audience.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return audiences;
    }
}

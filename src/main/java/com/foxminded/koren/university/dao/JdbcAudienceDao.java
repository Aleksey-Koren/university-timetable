package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.AudienceDao;
import com.foxminded.koren.university.dao.mappers.AudienceMapper;
import com.foxminded.koren.university.entity.Audience;

import static com.foxminded.koren.university.dao.sql.AudienceSql.SAVE;
import static com.foxminded.koren.university.dao.sql.AudienceSql.UPDATE;
import static com.foxminded.koren.university.dao.sql.AudienceSql.DELETE;
import static com.foxminded.koren.university.dao.sql.AudienceSql.GET_BY_ID;
import static com.foxminded.koren.university.dao.sql.AudienceSql.GET_ALL;

@Repository
public class JdbcAudienceDao implements AudienceDao {
    
    private static final Logger LOG = LoggerFactory.getLogger(JdbcAudienceDao.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
       
    @Override
    public Audience save(Audience entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        LOG.debug("Update database SQL = {} number = {} capasity = {}",
                "\n" + SAVE, entity.getNumber(), entity.getCapacity());
        
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SAVE, new String[] { "id" });
                statement.setInt(1, entity.getNumber());
                statement.setInt(2, entity.getCapacity());
                return statement;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            throw new DAOException(e.getMessage(), e);
        }
        entity.setId(keyHolder.getKeyAs(Integer.class));
        
        LOG.debug("New Audience has gotten id = {} ", keyHolder.getKeyAs(Integer.class));
        
        return entity;
    }

    @Override
    public void update(Audience entity) {
        LOG.debug("Update database. Update audience SQL: {} audience {}", "\n" + UPDATE, entity);
        try {
            jdbcTemplate.update(UPDATE, entity.getNumber(), entity.getCapacity(), entity.getId());
        } catch (DuplicateKeyException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        LOG.debug("Update database. Delete audience by id. SQL: {} audience.id = {}", "\n" + DELETE, id);
        return jdbcTemplate.update(DELETE, id) > 0;
    }

    @Override
    public Audience getById(Integer id) {
        LOG.debug("Query to database. Get audience by id. SQL: {} audience.id = {}", "\n" + GET_BY_ID, id);
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new AudienceMapper(), id);
        }catch(EmptyResultDataAccessException e) {
            throw new DAOException("No such id in database", e);
        }
    }

    @Override
    public List<Audience> getAll() {
        LOG.debug("Query to database. Get all audiences. SQL: {}", "\n" + GET_ALL);
        return jdbcTemplate.query(GET_ALL, new AudienceMapper());
    }
}
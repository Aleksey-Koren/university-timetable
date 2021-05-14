package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;
import java.util.List;

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
import com.foxminded.koren.university.domain.entity.Audience;

import static com.foxminded.koren.university.dao.sql.AudienceSql.SAVE;
import static com.foxminded.koren.university.dao.sql.AudienceSql.UPDATE;
import static com.foxminded.koren.university.dao.sql.AudienceSql.DELETE;
import static com.foxminded.koren.university.dao.sql.AudienceSql.GET_BY_ID;
import static com.foxminded.koren.university.dao.sql.AudienceSql.GET_ALL;

@Repository
public class JdbcAudienceDao implements AudienceDao{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
       
    @Override
    public Audience save(Audience entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
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
        return entity;
    }

    @Override
    public void update(Audience entity) {
        try {
            jdbcTemplate.update(UPDATE, entity.getNumber(), entity.getCapacity(), entity.getId());
        } catch (DuplicateKeyException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return jdbcTemplate.update(DELETE, id) > 0;
    }

    @Override
    public Audience getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new AudienceMapper(), id);
        }catch(EmptyResultDataAccessException e) {
            throw new DAOException("No such id in database", e);
        }
    }

    @Override
    public List<Audience> getAll() {
        return jdbcTemplate.query(GET_ALL, new AudienceMapper());
    }
}
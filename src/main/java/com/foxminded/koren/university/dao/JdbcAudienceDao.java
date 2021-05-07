package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.AudienceDao;
import com.foxminded.koren.university.dao.interfaces.CrudDao;
import com.foxminded.koren.university.dao.mappers.AudienceMapper;
import com.foxminded.koren.university.dao.sql.AudienceSql;
import com.foxminded.koren.university.domain.entity.Audience;

@Repository
public class JdbcAudienceDao implements AudienceDao{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
       
    @Override
    public Audience save(Audience entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(AudienceSql.getSave(), new String[] {"id"});
            statement.setInt(1, entity.getNumber());
            statement.setInt(2,  entity.getCapacity());
            return statement;
            }, keyHolder);
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Audience entity) {
        jdbcTemplate.update(AudienceSql.getUpdate(), entity.getNumber(), entity.getCapacity(), entity.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        return jdbcTemplate.update(AudienceSql.getDelete(), id) > 0;
    }

    @Override
    public Audience getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(AudienceSql.getGetById(), new AudienceMapper(), id);
        }catch(EmptyResultDataAccessException e) {
            throw new DAOException("No such id in database", e);
        }
    }
}
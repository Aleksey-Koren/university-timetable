package com.foxminded.koren.university.repository.jdbcDao;

import java.sql.PreparedStatement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.TeacherRepository;
import com.foxminded.koren.university.entity.Teacher;

import static com.foxminded.koren.university.repository.sql.TeacherSql.SAVE;
import static com.foxminded.koren.university.repository.sql.TeacherSql.UPDATE;
import static com.foxminded.koren.university.repository.sql.TeacherSql.DELETE;
import static com.foxminded.koren.university.repository.sql.TeacherSql.GET_BY_ID;
import static com.foxminded.koren.university.repository.sql.TeacherSql.GET_ALL;

@Repository
public class JdbcTeacherDao implements TeacherRepository {
    
    private static final Logger LOG = LoggerFactory.getLogger(JdbcTeacherDao.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTeacherDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Teacher save(Teacher entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        LOG.debug("Update database. SQL = {}, first name: {}, last name: {}",
                SAVE, entity.getFirstName(), entity.getLastName());

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SAVE, new String[] {"id"});
                statement.setString(1, entity.getFirstName());
                statement.setString(2, entity.getLastName());
                return statement;
            }, keyHolder);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }

        entity.setId(keyHolder.getKeyAs(Integer.class));
        LOG.debug("New teacher has gotten id = {} ", keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Teacher entity) {
        LOG.debug("Update database SQL: {} teacher {}", UPDATE, entity);
        try {
            jdbcTemplate.update(UPDATE, entity.getFirstName(), entity.getLastName(), entity.getId());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        LOG.debug("Update database SQL: {} teacher.id = {}", DELETE, id);
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public Teacher getById(Integer id) {
        LOG.debug("Query to database SQL: {} teacher.id = {}", GET_BY_ID, id);
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new BeanPropertyRowMapper<>(Teacher.class), id);
        }catch(DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public List<Teacher> getAll() {
        LOG.debug("Query to database SQL: {}", GET_ALL);
        try {
            return jdbcTemplate.query(GET_ALL, new BeanPropertyRowMapper<>(Teacher.class));
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
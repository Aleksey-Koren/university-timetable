package com.foxminded.koren.university.dao;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.StudentDao;
import com.foxminded.koren.university.dao.mappers.StudentMapper;
import com.foxminded.koren.university.entity.Student;

import java.sql.PreparedStatement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.foxminded.koren.university.dao.sql.StudentSql.SAVE;
import static com.foxminded.koren.university.dao.sql.StudentSql.UPDATE;
import static com.foxminded.koren.university.dao.sql.StudentSql.DELETE;
import static com.foxminded.koren.university.dao.sql.StudentSql.GET_BY_ID;
import static com.foxminded.koren.university.dao.sql.StudentSql.GET_ALL;
import static com.foxminded.koren.university.dao.sql.StudentSql.DELETE_BY_GROUP_ID;
@Repository
public class JdbcStudentDao implements StudentDao {
    
    private static final Logger LOG = LoggerFactory.getLogger(JdbcStudentDao.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
        
    @Override
    @Transactional
    public Student save(Student entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        LOG.debug("Update database. SQL = {} group: {}, first name: {}, last name: {}, year: {}",
                SAVE, entity.getGroup(), entity.getFirstName(), entity.getLastName(), entity.getYear());
        
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE, new String[]{"id"});
            if(entity.getGroup() != null) {
                statement.setInt(1, entity.getGroup().getId());
            }else {
                statement.setObject(1, null);
            }
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getYear().toString());
            return statement;
        }, keyHolder);
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        LOG.debug("New student has gotten id = {} ", keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Student entity) {
        LOG.debug("Update database SQL: {} student {}", UPDATE, entity);
        jdbcTemplate.update(UPDATE, 
                entity.getGroup() != null ? entity.getGroup().getId() : null, 
                entity.getFirstName(),
                entity.getLastName(),
                entity.getYear().toString(),
                entity.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        LOG.debug("Update database SQL: {} student.id = {}", DELETE, id);
        return jdbcTemplate.update(DELETE, id) > 0;
    }

    @Override
    public Student getById(Integer id) {
        LOG.debug("Query to database SQL: {} student.id = {}", GET_BY_ID, id);

        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new StudentMapper(), id);
        }catch(EmptyResultDataAccessException e) {
            throw new DAOException("No such id in database", e);
        }
    }

    @Override
    public void deleteByGroupId(int id) {
        LOG.debug("Update database SQL: {} group.id {}", DELETE_BY_GROUP_ID, id);
        jdbcTemplate.update(DELETE_BY_GROUP_ID, id);
    }

    @Override
    public List<Student> getAll() {
        LOG.debug("Query to database SQL: {}", GET_ALL);
        return jdbcTemplate.query(GET_ALL, new StudentMapper());
    }
}
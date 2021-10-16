package com.foxminded.koren.university.repository.jdbcDao;

import java.sql.PreparedStatement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.GroupRepository;
import com.foxminded.koren.university.repository.mappers.GroupMapper;
import com.foxminded.koren.university.entity.Group;

import static com.foxminded.koren.university.repository.sql.GroupSql.*;

@Repository
public class JdbcGroupDao implements GroupRepository {
    
    private static final Logger LOG = LoggerFactory.getLogger(JdbcGroupDao.class);
    

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcGroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group save(Group entity) {
        LOG.debug("Update database. Save new group SQL = {} group {}", 
                SAVE, entity);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SAVE, new String[] { "id" });
                statement.setString(1, entity.getName());
                statement.setString(2, entity.getYear().toString());
                return statement;
            }, keyHolder);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        LOG.debug("New group has gotten id = {} ", keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Group entity) {
        LOG.debug("Update database. Update group SQL: {} group {}", UPDATE, entity);
        try {
            jdbcTemplate.update(UPDATE, entity.getName(), entity.getYear().toString(), entity.getId());
        } catch (DuplicateKeyException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        LOG.debug("Update database. Delete group by id. SQL: {} group.id = {}", DELETE, id);
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(),e);
        }
    }

    @Override
    public Group getById(Integer id) {
        LOG.debug("Query to database. Get group by id. SQL: {} group.id = {}", GET_BY_ID, id);

        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new GroupMapper(), id);
        }catch(EmptyResultDataAccessException e) {
            throw new RepositoryException("No such id in database", e);
        }
    }

    @Override
    public List<Group> getAll() {
        LOG.debug("Query to database. Get all groups. SQL: {}", GET_ALL);
        return jdbcTemplate.query(GET_ALL, new GroupMapper());
    }

    public List<Group> getGroupsByLectureId(Integer lectureId) {
        LOG.debug("Query to database. Get groups by lecture id = {}. SQL: {}", "\n" + lectureId, GET_BY_LECTURE_ID);
        return jdbcTemplate.query(GET_BY_LECTURE_ID, new GroupMapper(), lectureId);
    }

    @Override
    public List<Group> getAllGroupsExceptAddedToLecture(int lectureId) {
        LOG.debug("Query to database. Get all groups except added to current lecture id = {}", lectureId);
        try {
            return jdbcTemplate.query(GET_ALL_EXCEPT_ADDED, new GroupMapper(), lectureId);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;
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
import com.foxminded.koren.university.dao.interfaces.GroupDao;
import com.foxminded.koren.university.dao.mappers.GroupMapper;
import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.entity.Group;

import static com.foxminded.koren.university.dao.sql.GroupSql.SAVE;
import static com.foxminded.koren.university.dao.sql.GroupSql.UPDATE;
import static com.foxminded.koren.university.dao.sql.GroupSql.DELETE;
import static com.foxminded.koren.university.dao.sql.GroupSql.GET_BY_ID;
import static com.foxminded.koren.university.dao.sql.GroupSql.GET_ALL;
import static com.foxminded.koren.university.dao.sql.GroupSql.ADD_COURSE;
import static com.foxminded.koren.university.dao.sql.GroupSql.REMOVE_COURSE;

@Repository
public class JdbcGroupDao implements GroupDao {
    
    private static final Logger LOG = LoggerFactory.getLogger(JdbcGroupDao.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Group save(Group entity) {
        LOG.debug("Update database. Save new group SQL = {} group {}", 
                SAVE, entity);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SAVE, new String[] { "id" });
                statement.setString(1, entity.getName());
                return statement;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            throw new DAOException(e.getMessage(), e);
        }
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        LOG.debug("New group has gotten id = {} ", keyHolder.getKeyAs(Integer.class));
        return entity;
    }

    @Override
    public void update(Group entity) {
        LOG.debug("Update database. Update group SQL: {} group {}", UPDATE, entity);
        try {
            jdbcTemplate.update(UPDATE, entity.getName(), entity.getId());
        } catch (DuplicateKeyException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        LOG.debug("Update database. Delete group by id. SQL: {} group.id = {}", DELETE, id);
        return jdbcTemplate.update(DELETE, id) > 0;
    }

    @Override
    public Group getById(Integer id) {
        LOG.debug("Query to database. Get group by id. SQL: {} group.id = {}", GET_BY_ID, id);

        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new GroupMapper(), id);
        }catch(EmptyResultDataAccessException e) {
            throw new DAOException("No such id in database", e);
        }
    }
    
    public List<Group> getAll() {
        LOG.debug("Query to database. Get all groups. SQL: {}", GET_ALL);
        return jdbcTemplate.query(GET_ALL, new GroupMapper());
    }
    
    @Override
    public void addCourse(Group group, Course course) {
        LOG.debug("Update to database. Add course to group. SQL: {} group.id = {} course.id = {}",
                ADD_COURSE, group.getId(), course.getId());
        jdbcTemplate.update(ADD_COURSE, group.getId(), course.getId());
    }
    @Override
    public boolean removeCourse(Group group, Course course) {
        LOG.debug("Update to database. Remove course from group. SQL: {} group.id = {} course.id = {}",
                REMOVE_COURSE, group.getId(), course.getId());
        return jdbcTemplate.update(REMOVE_COURSE, group.getId(), course.getId()) > 0;
    }
}
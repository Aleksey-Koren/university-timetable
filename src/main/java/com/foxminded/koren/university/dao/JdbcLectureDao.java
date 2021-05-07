package com.foxminded.koren.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.LectureDao;
import com.foxminded.koren.university.dao.mappers.LectureMapper;
import com.foxminded.koren.university.dao.sql.LectureSql;
import com.foxminded.koren.university.domain.entity.Lecture;

@Repository
public class JdbcLectureDao implements LectureDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Lecture save(Lecture entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Lecture entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean deleteById(Integer id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Lecture getById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(LectureSql.getGetById(), new LectureMapper(), id);
        }catch(EmptyResultDataAccessException e){
            throw new DAOException("No such id in database", e);
        }
    }
}
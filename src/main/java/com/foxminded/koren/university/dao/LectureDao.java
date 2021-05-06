package com.foxminded.koren.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.Dao;
import com.foxminded.koren.university.dao.mappers.LectureMapper;
import com.foxminded.koren.university.dao.sql.LectureSql;
import com.foxminded.koren.university.domain.entity.Lecture;

@Repository
public class LectureDao implements Dao<Integer, Lecture> {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
//    private static final String GET_BY_ID =
//            "SELECT \r\n"
//            + "    l.id lecture_id,\r\n"
//            + "    c.id course_id, c.name course_name, c.description course_description,\r\n"
//            + "    t.id teacher_id , t.first_name teacher_first_name, t.last_name teacher_last_name,  \r\n"
//            + "    a.id audience_id, a.room_number audience_room_number, a.capacity audience_capacity,\r\n"
//            + "    l.start_time,\r\n"
//            + "    l.end_time \r\n"
//            + "FROM lecture l\r\n"
//            + "   LEFT JOIN course c ON l.course_id = c.id\r\n"
//            + "       LEFT JOIN teacher t ON l.teacher_id = t.id \r\n"
//            + "           LEFT JOIN audience a ON l.audience_id = a.id\r\n"
//            + "WHERE l.id = ?;";

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
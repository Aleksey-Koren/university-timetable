package com.foxminded.koren.university.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.foxminded.koren.university.domain.entity.Audience;

@Component
public class AudienceDao implements Dao<Integer, Audience> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String SAVE = "INSERT INTO audience\r\n"
                                     + "(room_number, capacity)\r\n"
                                     + "VALUES\r\n"
                                     + "(?, ?);";
    
    private static final String UPDATE = "UPDATE audience\r\n"
                                       + "SET room_number = ?, capacity = ?\r\n"
                                       + "WHERE id = ?;";
    
    private static final String DELETE = "DELETE FROM audience\r\n"
                                       + "WHERE id = ?;";
    
    private static final String GET_BY_ID = "SELECT id, room_number, capacity\r\n"
                                          + "FROM audience\r\n"
                                          + "WHERE id = ?;";

    @Override
    public void save(Audience entity) {
        jdbcTemplate.update(SAVE, entity.getNumber(), entity.getCapacity());
    }

    @Override
    public void update(Audience entity) {
        jdbcTemplate.update(UPDATE, entity.getNumber(), entity.getCapacity(), entity.getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        return jdbcTemplate.update(DELETE, id) > 0;
    }

    @Override
    public Optional<Audience> getById(Integer id) {
        List<Audience> result = jdbcTemplate.query(GET_BY_ID, new AudienceMapper(), id);
        if(result.isEmpty()) {
            return Optional.empty();
        }else {
            return Optional.of(result.get(0));     
        }
    }

}
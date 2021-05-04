package com.foxminded.koren.university.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    public Audience save(Audience entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE, new String[] {"id"});
            statement.setInt(1, entity.getNumber());
            statement.setInt(2,  entity.getCapacity());
            return statement;
            }, keyHolder);
        
        entity.setId(keyHolder.getKeyAs(Integer.class));
        return entity;
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
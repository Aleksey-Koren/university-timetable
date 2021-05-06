package com.foxminded.koren.university.dao.test_data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TablesCreation {
    
    @Autowired
    @Qualifier("tablesCreationUrl")
    private String tablesCreationUrl;
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    public void createTables() throws IOException {
        jdbcTemplate.execute(retriveSql());
    }
    
    private String retriveSql() throws IOException {
        try(Stream<String> stream = Files.lines(Path.of(tablesCreationUrl))){
            return stream.reduce("", (a,b) -> a + b);
        }
    }
}
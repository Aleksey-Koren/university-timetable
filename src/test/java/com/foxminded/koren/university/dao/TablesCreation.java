package com.foxminded.koren.university.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
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
    
    public void createTables() throws DataAccessException, IOException {
        jdbcTemplate.execute(retriveSql());
    }
    
    private String retriveSql() throws IOException {
        try(Stream<String> stream = Files.lines(Path.of(tablesCreationUrl))){
            String s = stream.peek(System.out::println).reduce("", (a,b) -> a + b);
            return s;
        }
    }
    
    public static void main(String[] args) throws DataAccessException, IOException {
        ClassPathXmlApplicationContext con = 
                new ClassPathXmlApplicationContext("ApplicationContext.xml");
        TablesCreation c = con.getBean("tablesCreation", TablesCreation.class);
        c.createTables();
    }
}
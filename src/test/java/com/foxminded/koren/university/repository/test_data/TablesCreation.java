//package com.foxminded.koren.university.repository.test_data;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.stream.Stream;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TablesCreation {
//
//    @Autowired
//    @Qualifier("tablesCreationUrl")
//    private String tablesCreationUrl;
//
//    @Autowired
//    @Qualifier("jdbcTemplate")
//    private JdbcTemplate jdbcTemplate;
//
//    public void createTables() throws IOException {
//        jdbcTemplate.execute(retrieveSql());
//    }
//
//    private String retrieveSql() throws IOException {
//        try(Stream<String> stream = Files.lines(Path.of(tablesCreationUrl))){
//            return stream.reduce("", (a,b) -> a + b);
//        }
//    }
//}
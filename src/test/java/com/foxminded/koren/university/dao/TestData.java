package com.foxminded.koren.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestData {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public void prepareTestData() {
        String insertCourses = 
                "INSERT INTO course (name, description)\n"
              + "VALUES\n"
              + "('name1', 'desc1'),\n"
              + "('name2', 'desc2'),\n"
              + "('name3', 'desc3'),\n"
              + "('name4', 'desc4');";
        jdbcTemplate.update(insertCourses);
   
        String insertGroups = 
                "INSERT INTO group_table (name)\n"
              + "VALUES\n"
              + "('group name1'),\n"
              + "('group name2');";    
        jdbcTemplate.update(insertGroups);
   
        String addCoursesToGroup = "INSERT INTO group_course (group_id, course_id)\r\n"
                            + "VALUES\n"
                            + "(1, 1),\n"
                            + "(1, 2),\n"
                            + "(2, 1),\n"
                            + "(2, 2),\n"
                            + "(2, 4)";      
        jdbcTemplate.update(addCoursesToGroup);
    }
}
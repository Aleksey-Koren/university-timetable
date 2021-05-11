package com.foxminded.koren.university.dao.sql;

public class CourseSql {
    
    public static final String SAVE = 
            "INSERT INTO course\r\n"
          + "(name, description)\r\n"
          + "VALUES\r\n"
          + "(?, ?);";
    
    public static final String UPDATE = 
            "UPDATE course\r\n"
          + "SET name = ?,\r\n"
          + "    description = ?\r\n"
          + "WHERE id = ?;";
    
    public static final String DELETE = 
            "DELETE FROM course\r\n"
          + "WHERE id = ?;";
    
    public static final String GET_BY_ID = 
            "SELECT id course_id, name course_name, description course_description\r\n"
          + "FROM course\r\n"
          + "WHERE id = ?;";
    
    public static final String GET_BY_GROUP_ID = 
            "SELECT c.id course_id, c.name course_name, c.description course_description\r\n"
          + "FROM group_course gc\r\n"
          + "    JOIN course c ON gc.course_id = c.id\r\n"
          + "WHERE gc.group_id = ?"
          + "ORDER BY id;";
}
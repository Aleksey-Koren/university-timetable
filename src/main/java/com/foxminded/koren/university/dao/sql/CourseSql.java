package com.foxminded.koren.university.dao.sql;

public class CourseSql {
    
    private static final String SAVE = 
            "INSERT INTO course\r\n"
          + "(name, description)\r\n"
          + "VALUES\r\n"
          + "(?, ?);";
    
    private static final String UPDATE = 
            "UPDATE course\r\n"
          + "SET name = ?,\r\n"
          + "    description = ?\r\n"
          + "WHERE id = ?;";
    
    private static final String DELETE = 
            "DELETE FROM course\r\n"
          + "WHERE id = ?;";
    
    private static final String GET_BY_ID = 
            "SELECT id, name, description\r\n"
          + "FROM course\r\n"
          + "WHERE id = ?;";
    
    private static final String GET_BY_GROUP_ID = 
            "SELECT c.id , c.name , c.description\r\n"
          + "FROM group_course gc\r\n"
          + "    JOIN course c ON gc.course_id = c.id\r\n"
          + "WHERE gc.group_id = ?"
          + "ORDER BY id;";

    public static String getSave() {
        return SAVE;
    }

    public static String getUpdate() {
        return UPDATE;
    }

    public static String getDelete() {
        return DELETE;
    }

    public static String getGetById() {
        return GET_BY_ID;
    }

    public static String getGetByGroupId() {
        return GET_BY_GROUP_ID;
    }
    
    
}

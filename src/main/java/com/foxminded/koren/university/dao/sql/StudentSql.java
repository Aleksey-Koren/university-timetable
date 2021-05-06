package com.foxminded.koren.university.dao.sql;

public class StudentSql {
    
    private static final String SAVE = 
            "INSERT INTO student\r\n"
          + "(group_id, first_name, last_name, student_year)\r\n"
          + "VALUES\r\n"
          + "(?, ?, ?, ?);";
    
    private static final String GET_BY_ID = 
            "SELECT s.id,\r\n"
          + "       s.first_name,\r\n"
          + "       s.last_name,\r\n"
          + "       s.student_year,\r\n"
          + "       gt.id group_id,\r\n"
          + "       gt.name group_name\r\n"
          + "FROM student s\r\n"
          + "    LEFT JOIN group_table gt ON s.group_id = gt.id\r\n"
          + "WHERE s.id = ?;";
    
    private static final String UPDATE = 
            "UPDATE student \r\n"
          + "SET group_id = ?,\r\n"
          + "    first_name = ?,\r\n"
          + "    last_name = ?,\r\n"
          + "    student_year = ?\r\n"
          + "WHERE id = ?;";
    
    private static final String DELETE_BY_ID =
            "DELETE FROM student\r\n"
          + "WHERE id = ?;";

    public static String getSave() {
        return SAVE;
    }

    public static String getGetById() {
        return GET_BY_ID;
    }

    public static String getUpdate() {
        return UPDATE;
    }

    public static String getDeleteById() {
        return DELETE_BY_ID;
    }
}
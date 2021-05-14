package com.foxminded.koren.university.dao.sql;

public class StudentSql {
    
    public static final String SAVE = 
            "INSERT INTO student\r\n"
          + "(group_id, first_name, last_name, student_year)\r\n"
          + "VALUES\r\n"
          + "(?, ?, ?, ?);";
    
    public static final String GET_BY_ID = 
            "SELECT s.id,\r\n"
          + "       s.first_name,\r\n"
          + "       s.last_name,\r\n"
          + "       s.student_year,\r\n"
          + "       gt.id group_id,\r\n"
          + "       gt.name group_name\r\n"
          + "FROM student s\r\n"
          + "    LEFT JOIN group_table gt ON s.group_id = gt.id\r\n"
          + "WHERE s.id = ?;";
    
    public static final String GET_ALL = 
            "SELECT s.id,\r\n"
          + "       s.first_name,\r\n"
          + "       s.last_name,\r\n"
          + "       s.student_year,\r\n"
          + "       gt.id group_id,\r\n"
          + "       gt.name group_name\r\n"
          + "FROM student s\r\n"
          + "    LEFT JOIN group_table gt ON s.group_id = gt.id\r\n"
          + "ORDER BY s.id;";
        
    public static final String UPDATE = 
            "UPDATE student \r\n"
          + "SET group_id = ?,\r\n"
          + "    first_name = ?,\r\n"
          + "    last_name = ?,\r\n"
          + "    student_year = ?\r\n"
          + "WHERE id = ?;";
    
    public static final String DELETE =
            "DELETE FROM student\r\n"
          + "WHERE id = ?;";
    
    public static final String DELETE_BY_GROUP_ID =
            "DELETE FROM student\r\n"
          + "WHERE group_id = ?;";
}
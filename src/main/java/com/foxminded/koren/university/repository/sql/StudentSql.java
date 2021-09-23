package com.foxminded.koren.university.repository.sql;

public class StudentSql {
    
    public static final String SAVE = 
            "INSERT INTO student\r\n"
          + "(group_id, first_name, last_name)\r\n"
          + "VALUES\r\n"
          + "(?, ?, ?);";
    
    public static final String GET_BY_ID = 
            "SELECT s.id,\r\n"
          + "       s.first_name,\r\n"
          + "       s.last_name,\r\n"
          + "       gt.id group_id,\r\n"
          + "       gt.name group_name,\r\n"
          + "       gt.year group_year\r\n"
          + "FROM student s\r\n"
          + "    LEFT JOIN group_table gt ON s.group_id = gt.id\r\n"
          + "WHERE s.id = ?;";
    
    public static final String GET_ALL = 
            "SELECT s.id,\r\n"
          + "       s.first_name,\r\n"
          + "       s.last_name,\r\n"
          + "       gt.id group_id,\r\n"
          + "       gt.name group_name,\r\n"
          + "       gt.year group_year\r\n"
          + "FROM student s\r\n"
          + "    LEFT JOIN group_table gt ON s.group_id = gt.id\r\n"
          + "ORDER BY s.last_name;";
        
    public static final String UPDATE = 
            "UPDATE student \r\n"
          + "SET group_id = ?,\r\n"
          + "    first_name = ?,\r\n"
          + "    last_name = ?\r\n"
          + "WHERE id = ?;";
    
    public static final String DELETE =
            "DELETE FROM student\r\n"
          + "WHERE id = ?;";
    
    public static final String DELETE_BY_GROUP_ID =
            "DELETE FROM student\r\n"
          + "WHERE group_id = ?;";

    public static final String GET_BY_GROUP_ID =
            "SELECT s.id, s.first_name, s.last_name,\n" +
            "       gt.id   group_id,\n" +
            "       gt.name group_name,\n" +
            "       gt.year group_year\n" +
            "FROM student s\n" +
            "          JOIN group_table gt ON s.group_id = gt.id\n" +
            "WHERE group_id = ?\n" +
            "ORDER BY s.last_name;";

    public static final String GET_ALL_WITHOUT_GROUP =
            "SELECT id , group_id, first_name, last_name\n" +
            "FROM student\n" +
            "WHERE group_id IS NULL\n" +
            "ORDER BY last_name;";

    public static final String ADD_STUDENT_TO_GROUP =
            "UPDATE student\n" +
            "SET group_id = ?\n" +
            "WHERE id = ?;";

    public static final String REMOVE_STUDENT_FROM_GROUP =
            "UPDATE student\n" +
            "SET group_id = NULL\n" +
            "WHERE id = ?;";
}
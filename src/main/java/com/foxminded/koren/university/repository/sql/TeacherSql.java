package com.foxminded.koren.university.repository.sql;

public class TeacherSql {
    
    public static final String SAVE = 
            "INSERT INTO teacher (first_name, last_name)\r\n"
          + "VALUES (?, ?);";

    public static final String UPDATE = 
              "UPDATE teacher\r\n"
            + "SET first_name = ?,\r\n"
            + "    last_name = ?\r\n"
            + "WHERE id = ?;";

    public static final String DELETE = 
              "DELETE FROM teacher\r\n"
            + "WHERE id = ?;";

    public static final String GET_BY_ID = 
                 "SELECT id, first_name, last_name\r\n"
               + "FROM teacher\r\n"
               + "WHERE id = ?;";
    
    public static final String GET_ALL = 
            "SELECT id, first_name, last_name\r\n"
          + "FROM teacher\r\n"
          + "ORDER BY id;";
}
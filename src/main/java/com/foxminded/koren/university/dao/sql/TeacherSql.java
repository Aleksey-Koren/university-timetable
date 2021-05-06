package com.foxminded.koren.university.dao.sql;

public class TeacherSql {
    
    private static final String SAVE = 
            "INSERT INTO teacher (first_name, last_name)\r\n"
          + "VALUES (?, ?);";

    private static final String UPDATE = 
              "UPDATE teacher\r\n"
            + "SET first_name = ?,\r\n"
            + "    last_name = ?\r\n"
            + "WHERE id = ?;";

    private static final String DELETE = 
              "DELETE FROM teacher\r\n"
            + "WHERE id = ?;";

    private static final String GET_BY_ID = 
                 "SELECT id, first_name, last_name\r\n"
               + "FROM teacher\r\n"
               + "WHERE id = ?;";

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
}
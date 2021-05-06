package com.foxminded.koren.university.dao.sql;


public class AudienceSql {

    private static final String SAVE = "INSERT INTO audience\r\n"
            + "(room_number, capacity)\r\n"
            + "VALUES\r\n"
            + "(?, ?);";

    private static final String UPDATE = "UPDATE audience\r\n"
              + "SET room_number = ?, capacity = ?\r\n"
              + "WHERE id = ?;";

    private static final String DELETE = "DELETE FROM audience\r\n"
              + "WHERE id = ?;";

    private static final String GET_BY_ID = "SELECT id, room_number, capacity\r\n"
                 + "FROM audience\r\n"
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
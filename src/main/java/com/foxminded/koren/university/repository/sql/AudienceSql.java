package com.foxminded.koren.university.repository.sql;


public class AudienceSql {

    public static final String SAVE = 
            "INSERT INTO audience\r\n"
            + "(room_number, capacity)\r\n"
            + "VALUES\r\n"
            + "(?, ?);";

    public static final String UPDATE = 
            "UPDATE audience\r\n"
            + "SET room_number = ?, capacity = ?\r\n"
            + "WHERE id = ?;";

    public static final String DELETE = 
            "DELETE FROM audience\r\n"
            + "WHERE id = ?;";

    public static final String GET_BY_ID = 
              "SELECT id audience_id,\n" +
              "       room_number audience_room_number,\n" +
              "       capacity audience_capacity\n"
            + "FROM audience\n"
            + "WHERE id = ?;";
    
    public static final String GET_ALL = 
              "SELECT id audience_id,\n" +
              "       room_number audience_room_number,\n" +
              "       capacity audience_capacity\n"
            + "FROM audience\n"
            + "ORDER BY room_number;";
}
package com.foxminded.koren.university.dao.sql;

public class GroupSql {

    public static final String SAVE = 
            "INSERT INTO group_table\r\n"
          + "(name)\r\n"
          + "VALUES\r\n"
          + "(?);";

    public static final String UPDATE = "UPDATE group_table\r\n"
          + "SET name = ?\r\n"
          + "WHERE id = ?;";

    public static final String DELETE = 
            "DELETE FROM group_table\r\n"
          + "WHERE id = ?;";

    public static final String GET_GROUP_BY_ID = 
            "SELECT id group_id, name group_name\r\n"
          + "FROM group_table\r\n"
          + "WHERE id = ?;";
      
    public static final String ADD_COURSE = 
            "INSERT INTO group_course (group_id, course_id)\r\n"
          + "VALUES (?, ?);";

    public static final String REMOVE_COURSE = 
            "DELETE FROM group_course\r\n"
          + "WHERE group_id = ?\r\n"
          + "AND course_id = ?;";
}
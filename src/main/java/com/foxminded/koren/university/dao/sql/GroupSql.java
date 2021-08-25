package com.foxminded.koren.university.dao.sql;

public class GroupSql {

    public static final String SAVE = 
            "INSERT INTO group_table\r\n"
          + "(name, year)\r\n"
          + "VALUES\r\n"
          + "(?, ?);";

    public static final String UPDATE = "UPDATE group_table\r\n"
          + "SET name = ?,\r\n"
          + "    year = ?\r\n"
          + "WHERE id = ?;";

    public static final String DELETE = 
            "DELETE FROM group_table\r\n"
          + "WHERE id = ?;";

    public static final String GET_BY_ID = 
            "SELECT id group_id, name group_name, year group_year\r\n"
          + "FROM group_table\r\n"
          + "WHERE id = ?;";
    
    public static final String GET_ALL = 
            "SELECT id group_id, name group_name, year group_year\r\n"
            + "FROM group_table\r\n"
            + "ORDER BY id;";
      
//    public static final String ADD_COURSE =
//            "INSERT INTO group_course (group_id, course_id)\r\n"
//          + "VALUES (?, ?);";
//
//    public static final String REMOVE_COURSE =
//            "DELETE FROM group_course\r\n"
//          + "WHERE group_id = ?\r\n"
//          + "AND course_id = ?;";

    public static final String GET_BY_LECTURE_ID =
            "SELECT id group_id , name group_name, year group_year\n" +
            "   FROM group_table\n" +
            "       JOIN lecture_group lg on group_table.id = lg.group_id\n" +
            "   WHERE lg.lecture_id = ?" +
            "ORDER BY group_name";

    public static final String GET_ALL_EXCEPT_ADDED =
            "SELECT id group_id, name group_name, year group_year\n" +
            "FROM group_table\n" +
            "EXCEPT (SELECT id, name\n" +
            "            FROM group_table\n" +
            "                JOIN lecture_group lg on group_table.id = lg.group_id\n" +
            "                WHERE lg.lecture_id = ?)\n" +
            "ORDER BY group_id;";
}
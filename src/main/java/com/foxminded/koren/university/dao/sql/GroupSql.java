package com.foxminded.koren.university.dao.sql;

public class GroupSql {

    private static final String SAVE = 
            "INSERT INTO group_table\r\n"
          + "(name)\r\n"
          + "VALUES\r\n"
          + "(?);";

    private static final String UPDATE = "UPDATE group_table\r\n"
          + "SET name = ?\r\n"
          + "WHERE id = ?;";

    private static final String DELETE = 
            "DELETE FROM group_table\r\n"
          + "WHERE id = ?;";

    private static final String GET_GROUP_BY_ID = 
            "SELECT id, name\r\n"
          + "FROM group_table\r\n"
          + "WHERE id = ?;";
      
    private static final String ADD_COURSE = 
            "INSERT INTO group_course (group_id, course_id)\r\n"
          + "VALUES (?, ?);";

    private static final String REMOVE_COURSE = 
            "DELETE FROM group_course\r\n"
          + "WHERE group_id = ?\r\n"
          + "AND course_id = ?;";

    public static String getSave() {
      return SAVE;
    }

    public static String getUpdate() {
      return UPDATE;
    }

    public static String getDelete() {
      return DELETE;
    }

    public static String getGetGroupById() {
        return GET_GROUP_BY_ID;
    }

    public static String getAddCourse() {
        return ADD_COURSE;
    }

    public static String getRemoveCourse() {
        return REMOVE_COURSE;
    }
}
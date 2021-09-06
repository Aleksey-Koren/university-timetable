package com.foxminded.koren.university.dao.sql;

public class LectureSql {
    
    private LectureSql() {
        
    }
    
    public static final String GET_BY_ID =
            "SELECT \r\n"
            + "    l.id lecture_id,\r\n"
            + "    c.id course_id, c.name course_name, c.description course_description,\r\n"
            + "    t.id teacher_id , t.first_name teacher_first_name, t.last_name teacher_last_name,  \r\n"
            + "    a.id audience_id, a.room_number audience_room_number, a.capacity audience_capacity,\r\n"
            + "    l.start_time,\r\n"
            + "    l.end_time \r\n"
            + "FROM lecture l\r\n"
            + "   LEFT JOIN course c ON l.course_id = c.id\r\n"
            + "       LEFT JOIN teacher t ON l.teacher_id = t.id \r\n"
            + "           LEFT JOIN audience a ON l.audience_id = a.id\r\n"
            + "WHERE l.id = ?;";
    
    public static final String GET_ALL =
            "SELECT \r\n"
            + "    l.id lecture_id,\r\n"
            + "    c.id course_id, c.name course_name, c.description course_description,\r\n"
            + "    t.id teacher_id , t.first_name teacher_first_name, t.last_name teacher_last_name,  \r\n"
            + "    a.id audience_id, a.room_number audience_room_number, a.capacity audience_capacity,\r\n"
            + "    l.start_time,\r\n"
            + "    l.end_time \r\n"
            + "FROM lecture l\r\n"
            + "   LEFT JOIN course c ON l.course_id = c.id\r\n"
            + "       LEFT JOIN teacher t ON l.teacher_id = t.id \r\n"
            + "           LEFT JOIN audience a ON l.audience_id = a.id\r\n"
            + "ORDER BY l.start_time;";
    
    public static final String SAVE =
            "INSERT INTO lecture\r\n"
            + "(course_id, teacher_id, audience_id, start_time, end_time)\r\n"
            + "VALUES\r\n"
            + "(?, ?, ?, ?, ?)";
    
    public static final String UPDATE =
            "UPDATE lecture\r\n"
               + "SET course_id = ?,\r\n"
               + "    teacher_id = ?,\r\n"
               + "    audience_id = ?,\r\n"
               + "    start_time = ?,\r\n"
               + "    end_time = ?"
          + "WHERE id = ?;";
    
    public static final String DELETE =
            "DELETE FROM lecture\r\n"
            + "WHERE id = ?;";
    
    public static final String GET_BY_TEACHER_AND_TIME_PERIOD = 
              "SELECT \r\n"
            + "    l.id lecture_id,\r\n"
            + "    c.id course_id, c.name course_name, c.description course_description,\r\n"
            + "    t.id teacher_id , t.first_name teacher_first_name, t.last_name teacher_last_name,\r\n"
            + "    a.id audience_id, a.room_number audience_room_number, a.capacity audience_capacity,\r\n"
            + "    l.start_time start_time,\r\n"
            + "    l.end_time end_time\r\n"
            + "FROM lecture l\r\n"
            + "   LEFT JOIN course c ON l.course_id = c.id\r\n"
            + "       LEFT JOIN teacher t ON l.teacher_id = t.id\r\n"
            + "           LEFT JOIN audience a ON l.audience_id = a.id\r\n"
            + "WHERE t.id = ?\r\n"
            + "AND l.start_time >= ?\r\n"
            + "AND l.start_time <= ?\r\n"
            + "ORDER BY l.start_time;";
    
    public static final String GET_BY_STUDENT_AND_TIME_PERIOD = 
              "SELECT l1.id lecture_id,\r\n"
            + "       c.id course_id, c.name corse_name, c.description course_description,\r\n"
            + "       t.id teacher_id, t.first_name teacher_first_name, t.last_name teacher_last_name,\r\n"
            + "       a.id audience_id, a.room_number audience_room_number, a.capacity audience_capacity,\r\n"
            + "       l1.start_time start_time,\r\n"
            + "       l1.end_time end_time\r\n"
            + "FROM (SELECT l.id id,\r\n"
            + "           l.course_id course_id, \r\n"
            + "           l.teacher_id teacher_id,\r\n"
            + "           l.audience_id audience_id,\r\n"
            + "           l.start_time start_time,\r\n"
            + "           l.end_time end_time\r\n"
            + "    FROM lecture l \r\n"
            + "        JOIN lecture_group lg ON l.id = lg.lecture_id\r\n"
            + "            JOIN student s ON lg.group_id = s.group_id\r\n"
            + "    WHERE s.id = ?\r\n"
            + "    AND l.start_time >= ?\r\n"
            + "    AND l.end_time <= ?) l1 \r\n"
            + "        LEFT JOIN course c ON l1.course_id = c.id\r\n"
            + "            LEFT JOIN teacher t ON l1.teacher_id = t.id \r\n"
            + "                LEFT JOIN audience a ON l1.audience_id = a.id;";

    public static final String REMOVE_GROUP =
            "DELETE FROM lecture_group\n" +
            "WHERE lecture_id = ?\n" +
            "AND group_id = ?;";

    public static final String ADD_GROUP =
            "INSERT INTO lecture_group (lecture_id, group_id)\n" +
            "VALUES (?,?);";
}
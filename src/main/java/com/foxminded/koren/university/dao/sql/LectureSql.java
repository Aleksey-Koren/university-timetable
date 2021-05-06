package com.foxminded.koren.university.dao.sql;

public class LectureSql {
    
    private static final String GET_BY_ID =
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

    public static String getGetById() {
        return GET_BY_ID;
    }
}
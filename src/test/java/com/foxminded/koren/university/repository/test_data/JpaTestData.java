package com.foxminded.koren.university.repository.test_data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Profile("test")
public class JpaTestData {

    private final EntityManagerFactory entityManagerFactory;

    private final String tablesCreationUrl;

    @Autowired
    public JpaTestData(EntityManagerFactory entityManagerFactory, String tablesCreationUrl) {
        this.entityManagerFactory = entityManagerFactory;
        this.tablesCreationUrl = tablesCreationUrl;
    }

    public void createTables() throws IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery(retrieveSql());
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private String retrieveSql() throws IOException {
        return new String(Files.readAllBytes(Path.of(tablesCreationUrl)), StandardCharsets.UTF_8);
    }

    public void loadTestData () {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String insertCourses =
                "INSERT INTO course (name, description)\r\n"
                        + "VALUES\r\n"
                        + "('name1', 'desc1'),\r\n"
                        + "('name2', 'desc2'),\r\n"
                        + "('name3', 'desc3'),\r\n"
                        + "('name4', 'desc4');";
        var query = entityManager.createNativeQuery(insertCourses);
        query.executeUpdate();

        String insertGroups =
                "INSERT INTO group_table (name, year)\n"
                        + "VALUES\n"
                        + "('group name1', 'FIRST'),\r\n"
                        + "('group name2', 'SECOND'),\r\n" +
                        "('group name3', 'THIRD');\r\n";
        query = entityManager.createNativeQuery(insertGroups);
        query.executeUpdate();

        String addCoursesToGroup =
                "INSERT INTO group_course (group_id, course_id)\r\n"
                        + "VALUES\r\n"
                        + "(1, 1),\r\n"
                        + "(1, 2),\r\n"
                        + "(2, 1),\r\n"
                        + "(2, 2),\r\n"
                        + "(2, 4)";
        query = entityManager.createNativeQuery(addCoursesToGroup);
        query.executeUpdate();

        String insertTeachers =
                "INSERT INTO teacher (first_name, last_name)\r\n"
                        + "VALUES\n"
                        + "('first name1', 'last name1'),\n"
                        + "('first name2', 'last name2');";
        query = entityManager.createNativeQuery(insertTeachers);
        query.executeUpdate();

        String insertStudents =
                "INSERT INTO student (id, group_id, first_name, last_name)\n"
                        + "VALUES\n"
                        + "(1, 1, 'first name1', 'last name1'),\n"
                        + "(2, 2, 'first name2', 'last name2'),\n"
                        + "(3, 2, 'first name3', 'last name3'),\n"
                        + "(4, NULL, 'first name4', 'last name4');";
        query = entityManager.createNativeQuery(insertStudents);
        query.executeUpdate();

        String insertAudience =
                "INSERT INTO audience \r\n"
                        + "(id, room_number, capacity)\r\n"
                        + "VALUES\r\n"
                        + "(1, 4, 30),\r\n"
                        + "(2, 34, 30),\r\n"
                        + "(3, 37, 150)";
        query = entityManager.createNativeQuery(insertAudience);
        query.executeUpdate();

        String insertLectures =
                "INSERT INTO lecture \r\n"
                        + "(id, course_id, teacher_id, audience_id, start_time , end_time)\r\n"
                        + "VALUES\r\n"
                        + "(1 , 1, 1, 1, '2021-05-02 16:00:00', '2021-05-02 17:00:00'),\r\n"
                        + "(2 , 2, 1, 2, '2021-06-02 16:00:00', '2021-06-02 17:00:00'),\r\n"
                        + "(3 , 1, 1, 1, '2021-06-02 18:00:00', '2021-06-02 19:00:00'),\r\n"
                        + "(4 , 2, 1, 3, '2021-06-02 19:00:00', '2021-06-02 20:00:00'),\r\n"
                        + "(5 , 1, 1, 2, '2021-06-02 21:00:00', '2021-06-02 22:00:00'),\r\n"
                        + "(6 , 2, 1, 1, '2021-06-02 07:00:00', '2021-06-02 08:00:00'),\r\n"
                        + "(7 , 1, 1, 2, '2021-06-02 18:00:00', '2021-06-02 19:00:00'),\r\n"
                        + "(8 , 1, NULL, NULL, '2021-06-02 18:00:00', '2021-06-02 19:00:00'),\r\n"
                        + "(9 , 1, NULL, 2, '2021-06-02 18:00:00', '2021-06-02 19:00:00');";
        query = entityManager.createNativeQuery(insertLectures);
        query.executeUpdate();

        String addGroupsToLecture = """
                INSERT INTO lecture_group (lecture_id, group_id)
                VALUES
                (1, 1),
                (1, 2);""";
        entityManager.createNativeQuery(addGroupsToLecture).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

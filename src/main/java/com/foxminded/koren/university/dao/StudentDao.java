package com.foxminded.koren.university.dao;
import com.foxminded.koren.university.domain.entity.Course;
import com.foxminded.koren.university.domain.entity.Group;
import com.foxminded.koren.university.domain.entity.Student;
import com.foxminded.koren.university.domain.entity.Year;

import java.util.Arrays;
import java.util.Optional;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.postgresql.Driver;

@Component
public class StudentDao implements Dao<Integer, Student> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String SAVE = "INSERT INTO student\r\n"
                                     + "(group_id, first_name, last_name, student_year)\r\n"
                                     + "VALUES\r\n"
                                     + "(?, ?, ?, ?);";
    
    public JdbcTemplate getJdbc() {
        return this.jdbcTemplate;
    }

    @Override
    public void save(Student entity) {
        jdbcTemplate.update(SAVE, entity.getGroup().getId(),
                                  entity.getFirstName(),
                                  entity.getLastName(),
                                  entity.getYear().toString());
    }

    @Override
    public void update(Student entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean deleteById(Integer id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Optional<Student> getById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static void main(String[] args) {
        ApplicationContext x = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        StudentDao studentDao = x.getBean("studentDao", StudentDao.class);
        Course course = new Course("course1", "desc");
        Group group = new Group("group1", Arrays.asList(course));
        group.setId(1);
        Student student = new Student(group, "Ivan", "Ivanov", Year.FIRST);
        studentDao.save(student);
        studentDao.save(student);
        studentDao.save(student);
//        System.out.println(Year.FIFTH.toString());
    }
}
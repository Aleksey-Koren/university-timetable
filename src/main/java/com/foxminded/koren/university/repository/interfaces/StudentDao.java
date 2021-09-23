package com.foxminded.koren.university.repository.interfaces;

import com.foxminded.koren.university.entity.Student;

import java.util.List;

public interface StudentDao extends CrudRepository<Integer, Student> {
    
    void deleteByGroupId(int id);

    List<Student> getByGroupId(int id);

    List<Student> getAllWithoutGroup();

    boolean addStudentToGroup(int studentId, int groupId);

    boolean removeStudentFromGroup(int studentId);
}
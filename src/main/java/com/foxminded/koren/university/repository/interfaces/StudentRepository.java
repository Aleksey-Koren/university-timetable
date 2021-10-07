package com.foxminded.koren.university.repository.interfaces;

import com.foxminded.koren.university.entity.Student;

import java.util.List;

public interface StudentRepository extends CrudRepository<Integer, Student> {

    List<Student> getByGroupId(int id);

    List<Student> getAllWithoutGroup();

    void addStudentToGroup(int studentId, int groupId);

    void removeStudentFromGroup(int studentId);
}
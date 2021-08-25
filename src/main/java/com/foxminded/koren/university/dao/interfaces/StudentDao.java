package com.foxminded.koren.university.dao.interfaces;

import com.foxminded.koren.university.entity.Student;

import java.util.List;

public interface StudentDao extends CrudDao<Integer, Student> {
    
    void deleteByGroupId(int id);

    List<Student> getByGroupId(int id);

}
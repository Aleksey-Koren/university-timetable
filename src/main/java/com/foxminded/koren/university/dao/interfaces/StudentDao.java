package com.foxminded.koren.university.dao.interfaces;

import com.foxminded.koren.university.entity.Student;

public interface StudentDao extends CrudDao<Integer, Student> {
    
    void deleteByGroupId(int id);
}
package com.foxminded.koren.university.dao.interfaces;

import java.util.List;

import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.entity.Group;

public interface CourseDao extends CrudDao<Integer, Course> {
    
    List<Course> getByGroup(Group group);
    
}
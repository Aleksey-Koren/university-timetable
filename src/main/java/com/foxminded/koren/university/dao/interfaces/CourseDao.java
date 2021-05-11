package com.foxminded.koren.university.dao.interfaces;

import java.util.List;

import com.foxminded.koren.university.domain.entity.Course;
import com.foxminded.koren.university.domain.entity.Group;

public interface CourseDao extends CrudDao<Integer, Course> {
    
    List<Course> getByGroup(Group group);
    
}
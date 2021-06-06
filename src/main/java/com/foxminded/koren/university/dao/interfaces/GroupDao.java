package com.foxminded.koren.university.dao.interfaces;

import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.entity.Group;

public interface GroupDao extends CrudDao<Integer, Group> {
    
    void addCourse(Group group, Course course);
    
    boolean removeCourse (Group group, Course course);
}
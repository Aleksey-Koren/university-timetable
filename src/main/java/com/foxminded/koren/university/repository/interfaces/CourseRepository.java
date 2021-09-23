package com.foxminded.koren.university.repository.interfaces;

import java.util.List;

import com.foxminded.koren.university.entity.Course;
import com.foxminded.koren.university.entity.Group;

public interface CourseRepository extends CrudRepository<Integer, Course> {
    
    List<Course> getByGroup(Group group);
    
}
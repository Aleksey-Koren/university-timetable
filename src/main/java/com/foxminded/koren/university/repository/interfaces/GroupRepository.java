package com.foxminded.koren.university.repository.interfaces;

import com.foxminded.koren.university.entity.Group;

import java.util.List;

public interface
GroupRepository extends CrudRepository<Integer, Group> {
    
//    void addCourse(Group group, Course course);
//
//    boolean removeCourse (Group group, Course course);

    List<Group> getGroupsByLectureId(Integer lectureId);

    List<Group> getAllGroupsExceptAddedToLecture(int lectureId);
 }
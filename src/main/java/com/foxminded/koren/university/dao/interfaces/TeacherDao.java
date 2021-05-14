package com.foxminded.koren.university.dao.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import com.foxminded.koren.university.domain.entity.Teacher;
import com.foxminded.koren.university.domain.entity.interfaces.TimetableEvent;

public interface TeacherDao extends CrudDao<Integer, Teacher> {

    List<TimetableEvent> getEventsById(Integer id, LocalDateTime period);

}
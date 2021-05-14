package com.foxminded.koren.university.dao.interfaces;

import java.util.List;

import com.foxminded.koren.university.domain.entity.Lecture;
import com.foxminded.koren.university.domain.entity.interfaces.TimetableEvent;

public interface LectureDao extends CrudDao<Integer, Lecture> {

    List<TimetableEvent> getLecturesByPersonId(Integer id);
}

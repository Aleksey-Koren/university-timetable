package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.LectureRepository;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class LectureService {
    
    private static final Logger LOG = LoggerFactory.getLogger(LectureService.class);

    private LectureRepository lectureRepository;

    @Autowired
    public LectureService(@Qualifier("lectureRepositoryImpl") LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public Lecture createNew(Lecture lecture) {
        try {
            LOG.debug("Create new lecture");
            return lectureRepository.save(lecture);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void update(Lecture lecture) {
        LOG.debug("Update lecture id = {}", lecture.getId());
        try {
            lectureRepository.update(lecture);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void deleteById(int id) {
        LOG.debug("Delete lecture by id = {}", id);
        try {
            lectureRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public Lecture getById(int id) {
        try {
            LOG.debug("Get lecture by id = {}", id);
            return lectureRepository.getById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public List<Lecture> getAll() {
        LOG.debug("Get all lectures");
        return lectureRepository.getAll();
    }

    public void removeGroup(int lectureId, int groupId) {
        LOG.debug("Remove group id = {} from lecture id = {}", groupId, lectureId);
        try {
            lectureRepository.removeGroup(lectureId, groupId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void addGroup(int lectureID, int groupId) {
        LOG.debug("Add group id = {} to lecture id = {}", groupId, lectureID);
        try {
            lectureRepository.addGroup(lectureID,groupId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
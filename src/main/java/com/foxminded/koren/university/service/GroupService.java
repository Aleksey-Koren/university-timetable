package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.GroupRepository;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class GroupService {

    private GroupRepository groupRepository;
    
    private static final Logger LOG = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    public GroupService(@Qualifier("groupRepositoryImpl")GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group createNew(Group group) {
        try {
            LOG.debug("Save new group: {}", group);
            return groupRepository.save(group);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void update(Group group) {
        try {
            LOG.debug("Update group: {}", group);
            groupRepository.update(group);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void deleteById(int id) {
        LOG.debug("Delete group by id = {}", id);
        try {
            groupRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public Group getById(int id) {
        try {
            LOG.debug("Get group by id = {}", id);
            return groupRepository.getById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public List<Group> getAll(){
        LOG.debug("Get all groups");
        return groupRepository.getAll();
    }
    
    public void releaseGroup(Group group) {
        LOG.debug("Release group. {}", group);
        groupRepository.deleteById(group.getId());
    }

    public List<Group> getGroupsByLectureId(Integer id) {
        LOG.debug("Retrieve groups by lecture id = {}", id);
        return groupRepository.getGroupsByLectureId(id);
    }

    public List<Group> getAllExceptAddedToLecture(int lectureId) {
        LOG.debug("Get all groups except added to lecture id ={}", lectureId);
        try {
            return groupRepository.getAllGroupsExceptAddedToLecture(lectureId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
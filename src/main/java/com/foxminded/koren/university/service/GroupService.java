package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.GroupDao;
import com.foxminded.koren.university.entity.Group;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class GroupService {
    
    private static final Logger LOG = LoggerFactory.getLogger(GroupService.class);
    
    @Autowired
    private GroupDao groupDao;
        
    public Group createNew(Group group) {
        try {
            LOG.debug("Save new group: {}", group);
            return groupDao.save(group);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void update(Group group) {
        try {
            LOG.debug("Update group: {}", group);
            groupDao.update(group);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public boolean deleteById(int id) {
        LOG.debug("Delete group by id = {}", id);
        return groupDao.deleteById(id);
    }
    
    public Group getById(int id) {
        try {
            LOG.debug("Get group by id = {}", id);
            return groupDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public List<Group> getAll(){
        LOG.debug("Get all groups");
        return groupDao.getAll();
    }
    
    public void releaseGroup(Group group) {
        LOG.debug("Release group. {}", group);
        groupDao.deleteById(group.getId());
    }
}
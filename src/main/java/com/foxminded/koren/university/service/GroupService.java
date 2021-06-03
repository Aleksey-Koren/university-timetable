package com.foxminded.koren.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.interfaces.GroupDao;
import com.foxminded.koren.university.dao.interfaces.StudentDao;
import com.foxminded.koren.university.entity.Group;

@Service
public class GroupService {
    
    @Autowired
    private GroupDao groupDao;
    
    @Autowired
    private StudentDao studentDao;
    
    public Group createNew(Group group) {
        return groupDao.save(group);
    }
    
    public void update(Group course) {
        groupDao.update(course);
    }
    
    public boolean deleteById(int id) {
        return groupDao.deleteById(id);
    }
    
    public Group getById(int id) {
        return groupDao.getById(id);
    }
    
    public List<Group> getAll(){
        return groupDao.getAll();
    }
    
    public void releaseGroup(Group group) {
        studentDao.deleteByGroupId(group.getId());
        groupDao.deleteById(group.getId());
    }
}
package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.AudienceDao;
import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class AudienceService {
    
    private static final Logger LOG = LoggerFactory.getLogger(AudienceService.class);
    
    @Autowired
    @Qualifier("jdbcAudienceDao")
    private AudienceDao audienceDao;
        
    public Audience createNew(Audience audience) {
        try {
            LOG.debug("Save new audience: {}", audience);
            return audienceDao.save(audience);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void update(Audience audience) {
        try {
            LOG.debug("Update audience: {}", audience);
            audienceDao.update(audience);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public boolean deleteById(int id) {
        LOG.debug("Delete audience by id = {}", id);
        return audienceDao.deleteById(id);
    }
    
    public Audience getById(int id) {
        try {
            LOG.debug("Get audience by id = {}", id);
            return audienceDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public List<Audience> getAll() {
        LOG.debug("Get all audiences");
        return audienceDao.getAll();
    }
}
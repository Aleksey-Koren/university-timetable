package com.foxminded.koren.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.dao.interfaces.AudienceDao;
import com.foxminded.koren.university.domain.entity.Audience;

@Service
public class AudienceService {
    
    @Autowired
    private AudienceDao audienceDao;
    
    public Audience createNew(Audience audience) {
        return audienceDao.save(audience);
    }
    
    public void update(Audience audience) {
        audienceDao.update(audience);
    }
    
    public boolean deleteById(int id) {
        return audienceDao.deleteById(id);
    }
    
    public Audience getById(int id) {
        return audienceDao.getById(id);
    }
    
    public List<Audience> getAll() {
        return audienceDao.getAll();
    }
}
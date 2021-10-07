package com.foxminded.koren.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.AudienceRepository;
import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.service.exceptions.ServiceException;

@Service
public class AudienceService {
    
    private static final Logger LOG = LoggerFactory.getLogger(AudienceService.class);

    private AudienceRepository audienceRepository;


    @Autowired
    public AudienceService(@Qualifier("audienceRepositoryImpl") AudienceRepository audienceRepository) {
        this.audienceRepository = audienceRepository;
    }

    public Audience createNew(Audience audience) {
        try {
            LOG.debug("Save new audience: {}", audience);
            return audienceRepository.save(audience);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
    
    public void update(Audience audience) {
        try {
            LOG.debug("Update audience: {}", audience);
            audienceRepository.update(audience);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public void deleteById(int id) {
        LOG.debug("Delete audience by id = {}", id);
        audienceRepository.deleteById(id);
    }
    
    public Audience getById(int id) {
        try {
            LOG.debug("Get audience by id = {}", id);
            return audienceRepository.getById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    public List<Audience> getAll() {
        LOG.debug("Get all audiences");
        return audienceRepository.getAll();
    }
}
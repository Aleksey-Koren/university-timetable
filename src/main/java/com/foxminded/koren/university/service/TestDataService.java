package com.foxminded.koren.university.service;

import com.foxminded.koren.university.dao.interfaces.TestDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestDataService {

    @Autowired
    private TestDataDao testDataDao;

    private final Logger LOG = LoggerFactory.getLogger(TestDataService.class);

    public void reloadTestData() {
        LOG.trace("Reloading test data");
        testDataDao.reloadTestData();
    }
}

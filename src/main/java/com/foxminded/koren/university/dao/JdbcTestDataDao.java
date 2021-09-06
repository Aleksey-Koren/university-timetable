package com.foxminded.koren.university.dao;

import com.foxminded.koren.university.controller.TestDataController;
import com.foxminded.koren.university.controller.exceptions.ControllerException;
import com.foxminded.koren.university.dao.exceptions.DAOException;
import com.foxminded.koren.university.dao.interfaces.TestDataDao;
import com.foxminded.koren.university.service.TestDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;

@Repository
public class JdbcTestDataDao implements TestDataDao {

    private final Logger LOG = LoggerFactory.getLogger(JdbcTestDataDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void reloadTestData() {
        LOG.trace("Reloading test data");
        jdbcTemplate.execute(retrieveTestDataSQL());
    }

    private String retrieveTestDataSQL() {
        try (InputStream inputStream = TestDataController.class.getClassLoader().getResourceAsStream("TablesCreation.sql")) {
            return new String(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new DAOException("Can't read test data SQL file. Probably, it doesn't exist", e);
        }
    }
}
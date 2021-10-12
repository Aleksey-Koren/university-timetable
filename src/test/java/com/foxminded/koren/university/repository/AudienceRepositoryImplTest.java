package com.foxminded.koren.university.repository;

import com.foxminded.koren.university.Application;

import com.foxminded.koren.university.entity.Audience;
import com.foxminded.koren.university.repository.exceptions.RepositoryException;
import com.foxminded.koren.university.repository.interfaces.AudienceRepository;
import com.foxminded.koren.university.repository.test_data.JpaTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {Application.class})
@ActiveProfiles("test")
class AudienceRepositoryImplTest {

    @Autowired
    @Qualifier("audienceRepositoryImpl")
    private AudienceRepository audienceRepository;
    @Autowired
    private JpaTestData testData;

    @Test
    void getById_shouldWorkCorrectly() throws IOException {
        testData.createTables();
        testData.loadTestData();
        Audience expected = new Audience(1, 4, 30);
        assertEquals(expected, audienceRepository.getById(expected.getId()));
    }
    
    @Test
    void getAll_shouldWorkCorrectly() throws IOException {
        testData.createTables();
        testData.loadTestData();
        Audience audience1 = new Audience(4, 30);
        audience1.setId(1);
        Audience audience2 = new Audience(34, 30);
        audience2.setId(2);
        Audience audience3 = new Audience(37, 150);
        audience3.setId(3);
        List<Audience> expected = List.of(audience1, audience2, audience3);
        assertEquals(expected, audienceRepository.getAll());
    }
    
    @Test
    void save_shouldWorkCorrectly() throws IOException {
        testData.createTables();
        testData.loadTestData();
        int expectedId = 4;
        Audience expected = new Audience(113, 30);  
        audienceRepository.save(expected);
        expected.setId(expectedId);
        assertEquals(expected, audienceRepository.getById(expectedId));
    }
    
    @Test
    void update_shouldWorkCorrectly() throws IOException {
        testData.createTables();
        testData.loadTestData();
        int expectedId = 1;
        Audience expected = new Audience(4, 30);  
        expected.setId(expectedId);
        assertEquals(expected, audienceRepository.getById(expectedId));
        expected.setNumber(35);
        audienceRepository.update(expected);
        assertEquals(expected, audienceRepository.getById(expectedId));
    }
    
    @Test
    void deleteById_shouldWorkCorrectly() throws IOException {
        testData.createTables();
        testData.loadTestData();
        int expectedId = 1;
        Audience audience = new Audience(4, 30);  
        audience.setId(expectedId);
        assertEquals(audience, audienceRepository.getById(expectedId));
        audienceRepository.deleteById(expectedId);
        RepositoryException exception = assertThrows(RepositoryException.class, () -> audienceRepository.getById(audience.getId()));
        String expectedMessage = String
                .format("Unable to get audience with id = %s, cause: there is no audience with such id in database",
                        expectedId);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getAll_shouldSortAudiencesByName() throws IOException {
        testData.createTables();
        Audience audience1 = new Audience(5, 30);
        audienceRepository.save(audience1);
        Audience audience2 = new Audience(9, 30);
        audienceRepository.save(audience2);
        Audience audience3 = new Audience(6, 150);
        audienceRepository.save(audience3);
        List<Audience> audiences = List.of(audience1, audience2, audience3);
        assertNotEquals(audiences, audienceRepository.getAll());
        audiences = audiences.stream().sorted(comparingInt(Audience::getNumber)).collect(Collectors.toList());
        assertEquals(audiences, audienceRepository.getAll());
    }
}
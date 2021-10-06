
package com.foxminded.koren.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.foxminded.koren.university.SpringConfigT;
import com.foxminded.koren.university.repository.interfaces.LectureRepository;
import com.foxminded.koren.university.entity.Lecture;
import com.foxminded.koren.university.entity.Student;
import com.foxminded.koren.university.entity.Teacher;
import com.foxminded.koren.university.entity.Timetable;
import com.foxminded.koren.university.entity.interfaces.TimetableEvent;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {SpringConfigT.class})
@ExtendWith(MockitoExtension.class)
class TimetableServiceTest {
    
    @Mock
    @Autowired
    @Qualifier("lectureRepositoryImpl")
    private LectureRepository mockLectureRepository;
    
    @Autowired

    @InjectMocks
    private TimetableService timetableService;
    
    
    @Test
    void getTeacherTimetableByPeriod_shouldReturnTimetableEntityWithTeacherAndHisEvents() {
        List<Lecture> testLectures = retrieveTestLectures();
        Teacher testTeacher = new Teacher();
        LocalDate testDate = LocalDate.now();
        
        Mockito.when(mockLectureRepository.getTeacherLecturesByTimePeriod(Mockito.any(), Mockito.any(), Mockito.any()))
               .thenReturn(testLectures);
        
        Timetable timetable = timetableService.getTeacherTimetableByPeriod(testTeacher, testDate, testDate);
        
        Mockito.verify(mockLectureRepository, Mockito.times(1))
                .getTeacherLecturesByTimePeriod(testTeacher, testDate, testDate);
        
        List<TimetableEvent> expectedEvents = testLectures.stream()
                                                          .map(TimetableEvent.class::cast)
                                                          .collect(Collectors.toList());
        
        Timetable expected = new Timetable(testTeacher, expectedEvents);
        
        assertEquals(expected, timetable);
    }
    
    @Test
    void getStudentTimetableByPeriod_shouldReturnTimetableEntityWithStudentAndHisEvents() {
        List<Lecture> testLectures = retrieveTestLectures();
        Student testStudent = new Student();
        LocalDate testDate = LocalDate.now();
        
        Mockito.when(mockLectureRepository.getStudentLecturesByTimePeriod(Mockito.any(), Mockito.any(), Mockito.any()))
               .thenReturn(testLectures);
        
        Timetable timetable = timetableService.getStudentTimetableByPeriod(testStudent, testDate, testDate);
        
        Mockito.verify(mockLectureRepository, Mockito.times(1))
                .getStudentLecturesByTimePeriod(testStudent, testDate, testDate);
        
        List<TimetableEvent> expectedEvents = testLectures.stream()
                                                          .map(TimetableEvent.class::cast)
                                                          .collect(Collectors.toList());
        
        Timetable expected = new Timetable(testStudent, expectedEvents);
        
        assertEquals(expected, timetable);
    }
    
    private List<Lecture> retrieveTestLectures() {
        Lecture testLecture1 = new Lecture();
        testLecture1.setStartTime(LocalDate.now().atStartOfDay());
        testLecture1.setEndTime(LocalDate.now().atStartOfDay());
        Lecture testLecture2 = new Lecture();
        testLecture2.setStartTime(LocalDate.now().atStartOfDay());
        testLecture2.setEndTime(LocalDate.now().atStartOfDay());
        return Arrays.asList(testLecture1, testLecture2);
    }

}
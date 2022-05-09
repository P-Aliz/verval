package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class ServiceMockTest {
    @Spy
    private static Service mockedService;

    StudentXMLRepository fileRepository1;

    @Mock
    HomeworkXMLRepository fileRepository2;

    @Mock
    GradeXMLRepository fileRepository3;

    Homework homework;

    @Mock
    Integer nr = 3;

    @BeforeEach
    void init() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        mockedService = new Service(fileRepository1, fileRepository2, fileRepository3);
        MockitoAnnotations.initMocks(this);
        homework = new Homework("23", "XML", 8, 5);
    }

    @Test
    void homeworkShouldBeSaved() {
        Mockito.doReturn(homework).when(fileRepository2).save(any());
        assertEquals(0, mockedService.saveHomework("23", "XML", 8, 5));
    }


    @Test
    void homeworkShouldNotBeDeleted() {
        Mockito.when(fileRepository2.delete(anyString())).thenReturn(null);
        assertEquals(0, mockedService.deleteHomework("1"));
    }

    @Test
    void shouldExtendDeadlineMultipleTimes() {
        Service mockService = Mockito.mock(Service.class);
        mockService.extendDeadline(anyString(), anyInt());
        mockService.extendDeadline(anyString(), anyInt());
        mockService.extendDeadline(anyString(), anyInt());
        mockService.extendDeadline(anyString(), anyInt());

        Mockito.verify(mockService, Mockito.atLeastOnce());
    }
}

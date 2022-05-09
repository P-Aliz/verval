package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private static Service service;

    @BeforeAll
    static void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        //biztos legyen benne
        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    void findAllHomework() {
        AtomicInteger nr = new AtomicInteger();
        service.findAllHomework().forEach(homework -> {
            nr.set(nr.get() + 1);
        });
        assertTrue(nr.get() >0);

    }

    @Test
    void saveHomeworkWithInvalidData() {
        assertNotEquals(0, service.saveHomework("23", "Xvml", 5, 8));
    }

    @Test
    void saveHomeworkWithValidData() {
        assertEquals(0, service.saveHomework("23", "XML", 8, 5));
    }

    @Test
    void saveExistingHomework() {
        assertEquals(0, service.saveHomework("23", "XML", 8, 5));
    }

    @Test
    void saveStudentWithValidData() {
        assertEquals(1, service.saveStudent("13", "Name", 566));
    }

    @Test
    void deleteHomework() {
        assertEquals(0, service.deleteHomework("1"));
    }

    @Test
    void deleteStudent() {
        assertEquals(0, service.deleteStudent("1"));
    }

    @Test
    void updateHomework() {
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2})
    void extendDeadline(int nr) {
        assertTrue(service.extendDeadline(String.valueOf(nr), 9)==0);
    }
}
package com.coolwen.experimentplatformv2;

import com.coolwen.experimentplatformv2.dao.StudentRepository;
import com.coolwen.experimentplatformv2.service.ArrangeClassService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Experimentplatformv2ApplicationTests {


    @Autowired
    ArrangeClassService arrangeClassService;
    @Autowired
    StudentRepository studentRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        arrangeClassService.findByTeacherIdAndCourseId(1, 1);
    }

    @Test
    void test1() {
        studentRepository.listStudentMTestAnswerDTO();
    }
}

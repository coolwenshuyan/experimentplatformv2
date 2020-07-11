package com.coolwen.experimentplatformv2;

import com.coolwen.experimentplatformv2.dao.ExpModelRepository;
import com.coolwen.experimentplatformv2.dao.StudentRepository;
import com.coolwen.experimentplatformv2.dao.UserRepository;
import com.coolwen.experimentplatformv2.service.ArrangeClassService;
import com.coolwen.experimentplatformv2.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class Experimentplatformv2ApplicationTests {


    @Autowired
    ArrangeClassService arrangeClassService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ExpModelRepository expModelRepository;
    @Autowired
    UserService userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        arrangeClassService.findByTeacherIdAndCourseId(1, 1);
    }

    @Test
    void test1() {
//        studentRepository.listStudentMTestAnswerDTO();
//        System.out.println(expModelRepository.findAllByTeacher(1));
        System.out.println("————————————————"+userRepository.list());
    }
}

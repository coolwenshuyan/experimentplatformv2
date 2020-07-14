package com.coolwen.experimentplatformv2;


import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private DockerService dockerService;

    @Autowired
    private ClazzService clazzService;

    @Test
    public void addStudent() {
        Student student;
        for (int i = 1; i <= 50; i++) {
            student = new Student();
            student.setClassId(0);
            student.setStuMobile("136000000" + i);
            student.setStuName("stuName" + i);
            student.setStuUname("stu" + i);
            student.setStuPassword("123");
            student.setStuXuehao(String.valueOf(i));
            studentService.saveStudent(student);
        }

    }

    @Test
    public void addDocker() {
        Docker docker;
        for (int i = 1; i <= 100; i++) {
            docker = new Docker();
            docker.setDc_url("www.sctu" + i + ".edu.cn");
            docker.setDc_state(false);
            dockerService.addDocker(docker);
        }

    }

    @Test
    public void addClass() {
        ClassModel classModel;
        for (int i = 1; i < 10; i++) {
            classModel = new ClassModel();
            classModel.setClassCollage("信息与工程学院");
            classModel.setClassMajor("信息管理与信息系统");
            classModel.setClassGrade("200" + i);
            classModel.setClassName("信管" + i);
            classModel.setClassTeacher("张" + i);
            clazzService.saveClazz(classModel);
        }

    }
}
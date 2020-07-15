package com.coolwen.experimentplatformv2;


import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceImplTest {

    Random ran = new Random();

    @Autowired
    private StudentService studentService;

    @Autowired
    private DockerService dockerService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private CourseInfoService courseInfoService;

    @Test
    public void addStudent() {
        Student student;
        for (int i = 10; i <= 50; i++) {
            student = new Student();
            student.setClassId(0);
            student.setStuMobile("136000000" + i);
            student.setStuName(getName());
            student.setStuUname("stu" + i);
            student.setStuPassword("123");
            student.setStuXuehao("11111111" + i);
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

    @Test
    public void addCourse() {
        CourseInfo courseInfo;
        courseInfo = new CourseInfo();
        courseInfo.setCourseName("Java");
        courseInfo.setCourseCode("Java1");
        courseInfo.setCourseImgurl("c3");
        courseInfo.setCourseIntruduce("Good Good Study！");
        courseInfo.setTeacherId(1);
        courseInfoService.add(courseInfo);
        courseInfo = new CourseInfo();
        courseInfo.setCourseName("Python");
        courseInfo.setCourseCode("P1");
        courseInfo.setCourseImgurl("c2");
        courseInfo.setTeacherId(1);
        courseInfo.setCourseIntruduce("Day Day up！");
        courseInfoService.add(courseInfo);
        courseInfo = new CourseInfo();
        courseInfo.setCourseName("微信小程序");
        courseInfo.setCourseCode("wechat1");
        courseInfo.setCourseImgurl("c1");
        courseInfo.setTeacherId(2);
        courseInfo.setCourseIntruduce("培养学生很重要！");
        courseInfoService.add(courseInfo);
        courseInfo = new CourseInfo();
        courseInfo.setCourseName("代码很简单");
        courseInfo.setCourseCode("wechat1");
        courseInfo.setCourseImgurl("c4");
        courseInfo.setTeacherId(2);
        courseInfo.setCourseIntruduce("重要的逻辑思维能力！");
        courseInfoService.add(courseInfo);
    }

    private String getName() {
        String[] name1 = new String[]{"孔", "张", "叶", "李", "叶入", "孔令",
                "张立", "陈", "刘", "牛", "夏侯", "令", "令狐", "赵", "母", "穆", "倪",
                "张毅", "称", "程", "王", "王志", "刘金", "冬", "吴", "马", "沈"};

        String[] name2 = new String[]{"凡", "课", "颖", "页", "源", "都",
                "浩", "皓", "西", "东", "北", "南", "冲", "昊", "力", "量", "妮",
                "敏", "捷", "杰", "坚", "名", "生", "华", "鸣", "蓝", "春", "虎", "刚", "诚"};

        String[] name3 = new String[]{"吞", "明", "敦", "刀", "备", "伟",
                "唯", "楚", "勇", "诠", "佺", "河", "正", "震", "点", "贝", "侠",
                "伟", "大", "凡", "琴", "青", "林", "星", "集", "财"};

        boolean two = ran.nextInt(50) >= 45 ? false : true;
        if (two) {
            String n1 = name1[ran.nextInt(name1.length)];
            String n2;
            int n = ran.nextInt(10);
            if (n > 5) {
                n2 = name2[ran.nextInt(name2.length)];
            } else {
                n2 = name3[ran.nextInt(name3.length)];
            }
            return n1 + n2;
        } else {
            String n1 = name1[ran.nextInt(name1.length)];
            String n2 = name2[ran.nextInt(name2.length)];
            String n3 = name3[ran.nextInt(name3.length)];
            return n1 + n2 + n3;
        }
    }
}
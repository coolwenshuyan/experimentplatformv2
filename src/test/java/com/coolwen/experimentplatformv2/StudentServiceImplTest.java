package com.coolwen.experimentplatformv2;


import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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


    @Autowired
    private SetInfoService setInfoService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReplyService replyService;

    protected static final Logger logger = LoggerFactory.getLogger(StudentServiceImplTest.class);

    @Test
    public void addUser() {
        User user = new User();
        for (int i = 10; i <= 50; i++) {
            user = new User();
            user.setNickname(getName());
            user.setUsername("t" + i);
            user.setPassword("123");
            user.setStatus(false);
            user.setGonghao("00009" + i);
            userService.add(user);
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
    public void addStudentCheckedAndClassed() {
        Student student;
        List<ClassModel> classModelList = clazzService.findCurrentClass();
        for (int i = 1000; i < 1100; i++) {
            student = new Student();
            int indexId = (int) (Math.random() * classModelList.size() + 1);
            int classId = classModelList.get(indexId - 1).getClassId();
            student.setClassId(classId);
            student.setStuCheckstate(true);
            student.setStuMobile("136000" + i);
            student.setStuName(getName());
            student.setStuUname("stu" + i);
            student.setStuPassword("123");
            student.setStuXuehao("111111" + i);
            studentService.saveStudent(student);
        }

    }

    @Test
    public void addDockerUsed() {
        Docker docker;
        List<Student> studentList = studentService.findByClassModelIdAndIsChecked(true, 0);
        logger.debug("学生信息:" + studentList);
        for (int i = 1; i < 100; i++) {
            docker = new Docker();
            docker.setDc_url("www.sctu" + i + ".edu.cn");
            docker.setDc_state(false);
            int indexId = ran.nextInt((studentList.size()));
            int studentId = studentList.get(indexId).getId();
            docker.setStu_id(studentId);
            docker.setDc_state(true);
            Date dateStart = randomDate("2019-01-01", "2019-7-15");
            Date dateEnd = randomDate("2020-01-01", "2020-12-30");
            docker.setDc_start_datetime(dateStart);
            docker.setDc_end_datetime(dateEnd);
            studentList.removeIf(Student -> Student.getId() == studentId);
            logger.debug("学生信息:" + studentList);
            logger.debug("docker信息:" + docker);
            dockerService.addDocker(docker);
        }
    }

    @Test
    public void addDockerNoUsed() {
        Docker docker;
        for (int i = 100; i <= 200; i++) {
            docker = new Docker();
            docker.setDc_url("www.sctu" + i + ".edu.cn");
            docker.setDc_state(false);
            dockerService.addDocker(docker);
        }
    }

    @Test
    public void addStudentNoCheckedNoClass() {
        Student student;
        for (int i = 1100; i < 1200; i++) {
            student = new Student();
            student.setClassId(0);
            student.setStuMobile("136000" + i);
            student.setStuName(getName());
            student.setStuUname("stu" + i);
            student.setStuPassword("123");
            student.setStuXuehao("111111" + i);
            studentService.saveStudent(student);
        }
    }

    @Test
    public void addStudentCheckedNoClassed() {
        Student student;
        for (int i = 1200; i < 1300; i++) {
            student = new Student();
            student.setClassId(0);
            student.setStuCheckstate(true);
            student.setStuMobile("136000" + i);
            student.setStuName(getName());
            student.setStuUname("stu" + i);
            student.setStuPassword("123");
            student.setStuXuehao("111111" + i);
            studentService.saveStudent(student);
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

    @Test
    public void addSetInfor() {
        SetInfo setInfo = new SetInfo();
        setInfo.setId(1);
        setInfo.setSet_aboutus("aboutus");
        setInfo.setSet_platintro("platintro");
        setInfo.setSet_platstep("step");
        setInfo.setSet_rotateimg("1、2");
        setInfoService.add(setInfo);
    }

    @Test
    public void addQuestion() {
        Question question = new Question();
        for (int i = 1; i < 100; i++) {
            question = new Question();
            int coureId = (int) (Math.random() * 4 + 1);
            question.setContent("aboutus");
            question.setCourseId(coureId);
            question.setContent(getName());
            Date date = randomDate("2019-01-01", "2020-07-15");
            ;

            question.setQuestionDatetime(date);
            if (i % 2 == 0)
                question.setIsreply(true);
            else
                question.setIsreply(false);
            question.setSid(i % 10);
            questionService.add(question);
        }

    }


    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);

            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    @Test
    public void findQuestion() {
        Question question = new Question();
        question.setIsreply(true);
        question.setCourseId(1);
        logger.debug("问题信息:" + questionService.findAllByCourseId(0, question).getContent());
    }
    @Test
    public void findStudent() {

        logger.debug("学生信息:" + userService.findByGonghao("0000935"));
    }

    @Test
    public void addRelpy() {
        Reply reply = new Reply();
        List<Question> questionList = questionService.getAll();
        List<User> userList = userService.list();
        logger.debug("问题信息:" + questionList);
        for (int i = 1; i < 100; i++) {
            reply = new Reply();
            Date date = randomDate("2019-01-01", "2020-07-15");
            reply.setDicDatetime(date);
            int indexId = (int) (Math.random() * questionList.size() + 1);
            int questionId = questionList.get(indexId - 1).getId();
            reply.setQid(questionId);
            int teacherId = ran.nextInt(userList.size());
            reply.setReplyPname(userList.get(teacherId).getUsername());
            Question question = questionService.findById(questionId);
            logger.debug("回答信息:" + question);
            CourseInfo courseInfo = courseInfoService.findById(question.getCourseId());
            reply.setContent(courseInfo.getCourseName() + "仿真平台大法好,我爱仿真平台！");
            logger.debug("回答信息:" + reply);
            replyService.add(reply);
        }
    }

    @Test
    public void addQuestionStuId1() {
        Question question = new Question();
        for (int i = 1; i < 100; i++) {
            question = new Question();
            int coureId = (int) (Math.random() * 4 + 1);
            question.setContent("aboutus");
            question.setCourseId(coureId);
            question.setContent(getName());
            question.setSid(1);
            Date date = randomDate("2019-01-01", "2020-07-15");
            question.setQuestionDatetime(date);
            if (i % 2 == 0)
                question.setIsreply(true);
            else
                question.setIsreply(false);
            questionService.add(question);
        }

    }
}
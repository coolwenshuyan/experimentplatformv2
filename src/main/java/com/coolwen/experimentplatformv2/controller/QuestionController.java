package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.*;
import com.coolwen.experimentplatformv2.exception.UserException;
import com.coolwen.experimentplatformv2.kit.ShiroKit;
import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto;
import com.coolwen.experimentplatformv2.model.DTO.QuestionStudentDto;
import com.coolwen.experimentplatformv2.service.*;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yellow
 */
@Controller
@RequestMapping(value = "question")
public class QuestionController {

    //    注入
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseInfoService courseInfoService;

    @Autowired
    private ArrangeClassService arrangeClassService;//课程安排表

    @Autowired
    private ClazzService classService;

    @Autowired
    private StudentService studentService;

    @Value("${SimplePageBuilder.pageSize}")
    int size;

    protected static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    //用户提问点击进入提交页
    @GetMapping(value = "/add")
    public String QuestionAdd() {
        return "question_reply/add";
    }

    //学生完成添加提交问题操作
    @PostMapping(value = "/add")
    public String add(Question question, HttpSession session) {
//        从seesion拿到student的内容
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        logger.debug("提问学生信息" + student);
        //暂时做了修改
        if (ShiroKit.isEmpty(student)) {
            throw new UserException("请先登录后再来提问!");
        }
        if (ShiroKit.isEmpty(session.getAttribute("question_courseId"))) {
            throw new UserException("请先选择课程!");
        } else {
            int courseId = (int) session.getAttribute("question_courseId");
            if (courseId == 0) {
                throw new UserException("请先选择课程!");
            }
            //        插入数据到数据库
            question.setSid(student.getId());
            question.setIsreply(false);
            question.setQuestionDatetime(new Date());
            question.setCourseId(courseId);
            questionService.add(question);
            return "redirect:/question/list";//list
        }
    }

//    //老师端看到question列表，查出来
//    @GetMapping(value = "/teacherlist")
//    public String loadAllModel(Model model,
//                               @RequestParam(required = true, defaultValue = "") String select_orderId,
//                               @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {
//
//        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
//        logger.debug("登陆用户信息:" + user);
//        boolean choose = false;
//        model.addAttribute("Choose", choose);
//        List<CourseInfo> courseInfoList = courseInfoService.getclass_by_arrangeteacher(1);
//        model.addAttribute("courseInfoList", courseInfoList);
//        return "shouye/dayiManage";
//    }

    //老师端看到question列表，查出来
    @GetMapping(value = "/teacherlist/{courseinfoId}")
    public String QuestionList(Model model, HttpSession session,
                               @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum, @PathVariable int courseinfoId) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("登陆老师信息:" + user);
        model.addAttribute("selected", courseinfoId);
        List<CourseInfo> courseInfoList = courseInfoService.getclass_by_arrangeteacher(1);
        logger.debug("该老师负责的课程信息:" + courseInfoList);
        model.addAttribute("courseInfoList", courseInfoList);
        if (courseinfoId == -1) {
            logger.debug("courseinfoId信息:" + courseinfoId);
            Pageable pageable = PageRequest.of(pageNum, size);
            //查询该老师的课程信息
            //TODO 加入老师信息筛选
            Page<QuestionStudentDto> page = questionService.findAndUname(pageable);
            model.addAttribute("questionPageInfo", page);
            return "shouye/dayiManage";
        } else {
            List<ArrangeClassDto> arrangeClassDtos = arrangeClassService.findByTeacherIdAndCourseId(1, courseinfoId);
            //获取所有班级的ID
            List<Integer> integerList = arrangeClassDtos.stream().map(ArrangeClassDto -> ArrangeClassDto.getcIlassId()).collect(Collectors.toList());
            logger.debug("该老师负责的班级信息有:" + arrangeClassDtos);
            Page<QuestionStudentDto> page = questionService.findByCourseIdAndTeacherId(courseinfoId, 1, pageNum);
            logger.debug("该老师负责的班级信息有:" + page.getContent());
            model.addAttribute("questionPageInfo", page);
        }
        return "shouye/dayiManage";
    }

    @GetMapping(value = "/{courseId}/list")
    public String questionList(Model model,
                               HttpSession session,
                               @PathVariable int courseId,
                               @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {

        User user = (User) session.getAttribute("admin");
        logger.debug("user:>>" + user);
        model.addAttribute("courseId", courseId);

//        分页查询，每页最多五条数据
        Pageable pageable = PageRequest.of(pageNum, 10);
        Page<QuestionStudentDto> page = questionService.findAllByCourseId(pageable, courseId);
        model.addAttribute("questionPageInfo", page);

        List<CourseInfo> courseInfoList = courseInfoService.getclass_by_arrangeteacher(user.getId());
        model.addAttribute("courseInfoList", courseInfoList);
        return "shouye/dayiManage";
    }

    //学生用户端看到question列表，查出来
    @GetMapping(value = "/list")
    public String QuestionList1(Model model, @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum,
                                @RequestParam(defaultValue = "0", required = true, value = "courseId") Integer courseId,
                                @RequestParam(defaultValue = "0", required = true, value = "isreply") Integer isreply,
                                @RequestParam(defaultValue = "0", required = true, value = "day") Integer day, HttpSession session
    ) {
        logger.debug("接口信息: + /question/list");
        Question question = new Question();
        logger.debug("课程ID:" + courseId);
        logger.debug("day:" + day);
        logger.debug("isreply:" + isreply);
        boolean isr = true;
        if (isreply != 0) {
            courseId = (Integer) session.getAttribute("question_courseId");
            logger.debug("isreply的课程ID:" + courseId);
            if (ShiroKit.isEmpty(courseId)) {
                courseId = courseInfoService.findAll().get(1).getId();
            }
            session.setAttribute("question_isreply", isreply);
            if (isreply == 1) {
                isr = true;
            } else {
                isr = false;
            }
        }
        if (courseId != 0) {
            isreply = (Integer) session.getAttribute("question_isreply");
            session.setAttribute("question_courseId", courseId);
        }
        question.setIsreply(isr);
        question.setCourseId(courseId);
        model.addAttribute("day", day);
        model.addAttribute("courseId", courseId);
        model.addAttribute("isreply", isreply);
        List<CourseInfo> courseInfoList = courseInfoService.findAll();
        model.addAttribute("courseInfoList", courseInfoList);
        CourseInfo courseInfo = courseInfoService.findById(courseId);
        logger.debug("课程信息:" + courseInfo);
        model.addAttribute("courseInfo", courseInfo);
        logger.debug("查询信息:" + question);
        Page<QuestionStudentDto> page = questionService.findAllByCourseId(pageNum, question);
//        page.getContent()
        logger.debug("问题信息:" + page.getContent());
        model.addAttribute("questionPageInfo", page);
        return "home_page/question";
    }
//    //学生用户端看到question列表，查出来
//    @GetMapping(value = "/list1")
//    public String QuestionListCourse(Model model, @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum,
//                                @RequestParam(defaultValue = "0", required = true, value = "courseId") Integer courseId,
//                                @RequestParam(defaultValue = "0", required = true, value = "isreply") Integer isreply) {
////        分页查询，每页最多五条数据
//        Pageable pageable = PageRequest.of(pageNum, 5);
//        Page<QuestionStudentDto> page = questionService.findAllByCourseId(pageable, 1);
//        List<CourseInfo> courseInfoList = courseInfoService.findAll();
//        model.addAttribute("courseInfoList", courseInfoList);
////        Page<QuestionStudentDto> page = questionService.findByCourse_idAndDic_datetimeAndIsreply(pageNum, 1, new Date(), isreply);
//        logger.debug("问题信息:" + page.getContent());
//        model.addAttribute("questionPageInfo", page);
//        return "home_page/question";
//    }

//    //进入修改界面
//    @GetMapping(value = "/{id}/update")
//    public String update(@PathVariable int id, Model model) {
//        Reply reply = replyService.findById(id);
//        model.addAttribute("reply",reply);
////        Question question = questionService.findById(id);
////        model.addAttribute("question", question);
//        return "question_reply/update";
//    }
//
//    //完成修改操作
//    @PostMapping(value = "/{id}/update")
//    public String update(@PathVariable int id, Question question) {
//        question.setId(id);
//        question.setSid(99);
////        name
//        question.setContent("adsadsad");
//        question.setDic_datetime(new Date());
//        questionService.add(question);
//        logger.debug("修改成功");
//        return "redirect:/questionORreply/{id}/seesee";
//    }

    //删除问题及所有回复
    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable int id) {
//        通过id删除所有该问题的回复
        replyService.deleteByQid(id);
//        通过id删除该问题
        questionService.delete(id);
        return "redirect:/question/list";
    }

//    //后台管理进入答疑室
//    @GetMapping(value = "index")
//    public String index() {
//        return "question_reply/index";
//    }


    //老师进入查看页
    @GetMapping(value = "/{id}/dayiMore")
    public String seesee(@PathVariable int id, Model model) {
//        查到该问题
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
//        查到所有回复
        List<Reply> replies = replyService.findByreplycontent(id);
        model.addAttribute("replies", replies);
//        查出学生的名字
        int a = question.getSid();
        String studentName = studentRepository.findStudentname(a);
        model.addAttribute("studentName", studentName);
        return "shouye/dayiMore";
    }

    //学生进入查看页
    @GetMapping(value = "/detaill/{id}")
    public String seesee1(@PathVariable int id, Model model, @RequestParam(required = true, defaultValue = "0") Integer pageNum) {
//        查到该问题
        Question question = questionService.findById(id);
        logger.debug("问题信息:" + question);
        model.addAttribute("question", question);
//        查到所有回复
        Page<Reply> replies = replyService.findPageByQuesionId(pageNum, question.getId());
        logger.debug("回答列表信息:" + replies.getContent());
        model.addAttribute("replies", replies);
//        查出学生的名字
        int a = question.getSid();
        Student student = studentRepository.findAllById(a);
        model.addAttribute("student", student);
        return "home_page/detaill";
    }

}

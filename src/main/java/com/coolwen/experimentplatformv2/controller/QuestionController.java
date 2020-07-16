package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.*;
import com.coolwen.experimentplatformv2.exception.UserException;
import com.coolwen.experimentplatformv2.kit.ShiroKit;
import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.model.DTO.QuestionStudentDto;
import com.coolwen.experimentplatformv2.service.CourseInfoService;
import com.coolwen.experimentplatformv2.service.QuestionService;
import com.coolwen.experimentplatformv2.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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

        //暂时做了修改
        if (student == null) {
            throw new UserException("请先登录后再来提问!");
        }

//        插入数据到数据库
        question.setSid(student.getId());
        question.setIsreply(false);
        question.setQuestionDatetime(new Date());
        questionService.add(question);
        return "redirect:/question/list1";//list
    }

    //老师端看到question列表，查出来
    @GetMapping(value = "/teacherlist")
    public String QuestionList(Model model, HttpSession session,
                               @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {

        User user = (User) session.getAttribute("admin");
        logger.debug("user:>>" + user);
//        分页查询，每页最多五条数据
        Pageable pageable = PageRequest.of(pageNum, 10);
        Page<QuestionStudentDto> page = questionService.findAndUname(pageable);
        model.addAttribute("questionPageInfo", page);

        List<CourseInfo> courseInfoList = courseInfoService.getclass_by_arrangeteacher(user.getId());
        model.addAttribute("courseInfoList", courseInfoList);
        return "shouye/dayiManage";
    }

    @GetMapping(value = "/{courseId}/list")
    public String questionList(Model model,
                               HttpSession session,
                               @PathVariable int courseId,
                               @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {

        User user = (User) session.getAttribute("admin");
        logger.debug("user:>>" + user);
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
        Question question = new Question();
        logger.debug("课程ID:" + courseId);
        logger.debug("day:" + day);
        logger.debug("isreply:" + isreply);
        boolean isr = true;
//        if (day != 0) {
//            session.setAttribute("question_day", day);
//            day = (Integer) session.getAttribute("question_day");
//            String strDate1 = "2020-00-00";
////        }
//        if (session.getAttribute("question_isreply") != null && isreply != 0) {
//            isreply = (Integer) session.getAttribute("question_isreply");
//            isreply = (Integer) session.getAttribute("question_isreply");
//        }
//        if (session.getAttribute("question_courseId") != null && courseId != 0) {
//            session.setAttribute("question_courseId", courseId);
//            courseId = (Integer) session.getAttribute("question_courseId");
//        }
        if (isreply != 0) {
            courseId = (Integer) session.getAttribute("question_courseId");
            logger.debug("isreply的课程ID:" + courseId);
            if (ShiroKit.isEmpty(courseId)) {
                courseId = courseInfoService.findAll().get(1).getId();
//                courseId = 0;
            }
            session.setAttribute("question_isreply", isreply);
//            isreply = (Integer) session.getAttribute("question_isreply");
            if (isreply == 1) {
                isr = true;
            } else {
                isr = false;
            }
//            question.setIsreply(isr);
        }
        if (courseId != 0) {
            isreply = (Integer) session.getAttribute("question_isreply");
            session.setAttribute("question_courseId", courseId);
//            question.setCourse_id(courseId);
        }
        question.setIsreply(isr);
//        question.setIsreply(isr);
        question.setCourse_id(courseId);
        model.addAttribute("day", day);
        model.addAttribute("courseId", courseId);
        model.addAttribute("isreply", isreply);
//        分页查询，每页最多五条数据
        Pageable pageable = PageRequest.of(pageNum, 5);
//        Page<QuestionStudentDto> page = questionService.findAllByCourseId(pageable, 1);
        List<CourseInfo> courseInfoList = courseInfoService.findAll();
        model.addAttribute("courseInfoList", courseInfoList);

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
    public String seesee1(@PathVariable int id, Model model) {
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
        return "home_page/detaill";
    }

}

package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.ReplyRepository;
import com.coolwen.experimentplatformv2.model.Admin;
import com.coolwen.experimentplatformv2.model.Question;
import com.coolwen.experimentplatformv2.model.Reply;
import com.coolwen.experimentplatformv2.model.Student;
import com.coolwen.experimentplatformv2.service.QuestionService;
import com.coolwen.experimentplatformv2.service.ReplyService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author yellow
 */
@Controller
@RequestMapping(value = "reply")
public class ReplyController {
    protected static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
    //    注入
    @Autowired
    private ReplyService replyService;


    @Autowired
    private QuestionService questionService;

    //用户提问点击提交
//    @GetMapping(value = "/add1")
//    public String ReplyAdd(@PathVariable int id) {
//        return "question_reply/seesee";
//    }

    //老师完成添加回复操作
    @PostMapping(value = "/{id}/add1")
    public String add(@PathVariable int id, Reply reply, String uploadfile, HttpSession session) {

//        回复的问题地存为qid
        reply.setQid(id);
        logger.debug("插入的回复保存为：" + id);

//        seesion获得老师信息
        Admin admin = null;
        try {
            admin = (Admin) SecurityUtils.getSubject().getPrincipal();
        } catch (Exception e) {

        }
        if (admin == null) {

            admin = new Admin();
            admin.setUname("管理教师");
        }

//        插入回复
        reply.setReplyPname(admin.getUname());//获得并存入回复名字
        reply.setDicDatetime(new Date());
        replyService.add(reply);
        Question question = questionService.findById(id);
//        表示已回复
        question.setIsreply(true);
        questionService.add(question);
        return "redirect:/question/" + id + "/dayiMore";//list
    }

    //学生回复并操作
    @PostMapping(value = "/add2/{questionid}")
    public String add1(@PathVariable int questionid, Reply reply, HttpSession session) {
//        回复的问题地存为qid
//        Reply reply = new Reply();
        reply.setQid(questionid);
        logger.debug("回复问题ID：" + questionid);
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) SecurityUtils.getSubject().getSession().getAttribute("student");
//        Student student = (Student) session.getAttribute("student");

//        插入回复
        reply.setReplyPname(student.getStuName());//获得并存入回复名字
        reply.setDicDatetime(new Date());
        logger.debug("回复问题内容：" + reply);
        replyService.add(reply);
        Question question = questionService.findById(questionid);
//      表示老师未回复
        question.setIsreply(false);
        questionService.add(question);
        return "redirect:/question/detaill/" + questionid;//list
    }


//    //查出来
//    @GetMapping(value = "/list")
//    public String ReplyList(Model model, @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {
//        Pageable pageable = PageRequest.of(pageNum, 5);
//        Page<Reply> page = replyRepository.findAll(pageable);
//        model.addAttribute("replyPageInfo", page);
//        return "question_reply/reply";
//    }

//    //进入修改界面
//    @GetMapping(value = "/{id}/update")
//    public String update(@PathVariable int id, Model model) {
//        Reply reply = replyService.findById(id);
//        model.addAttribute("reply",reply);
//        return "question_reply/update";
//    }

    //老师完成修改操作
    @PostMapping(value = "/{id}/update")
    public String update(@PathVariable int id, Reply reply) {
//        先查出内容
        Reply replyupdate = replyService.findById(id);
//        重新写入内容
        replyupdate.setContent(reply.getContent());
        replyService.add(replyupdate);
        logger.debug("修改成功");
        return "redirect:/question/" + replyupdate.getQid() + "/dayiMore";
    }

    //删除
    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable int id) {
        Reply reply = replyService.findById(id);
        int mid = reply.getQid();
        replyService.delete(id);
        return "redirect:/question/" + mid + "/dayiMore";
    }
}

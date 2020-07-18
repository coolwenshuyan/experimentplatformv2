package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.kit.ShiroKit;
import com.coolwen.experimentplatformv2.model.Student;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.StudentService;
import com.coolwen.experimentplatformv2.service.UserService;
import com.coolwen.experimentplatformv2.utils.CasUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @author CoolWen
 * @version 2020-05-06 21:46
 */
@Controller
//@RequestMapping("/admin")
public class HelloController {


    protected static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
//    @Lazy//解决shiro和事务管理不生效问题
    private UserService userService;

    @Autowired
//    @Lazy//解决shiro和事务管理不生效问题
    private StudentService studentService;

    /**
     * 从信息门户单点登陆后会直接进入该接口，对学生老师的信息进行更新
     *
     * @return
     */
    @RequestMapping("/")
//    @RequiresRoles("Admin")
    public String hello(HttpSession session, SessionStatus sessionStatus) {
        logger.debug("通过信息门户进入系统的");
        //拿到登陆账号，学生是学号，老师是身份证号码
        String account = (String) SecurityUtils.getSubject().getSession().getAttribute("account");
        Map<Object, Object> map = CasUtils.getUserInfo(SecurityUtils.getSubject().getSession());
        //获取登陆用户角色
        String comsys_role = (String) map.get("comsys_role");
        logger.debug("角色信息:" + comsys_role);
        //用户姓名
        String comsys_name = (String) map.get("comsys_name");
        logger.debug("登陆姓名信息:" + comsys_role);
        if (comsys_role.contains("ROLE_STUDENT")) {

            Student student = studentService.findStudentByXueHao(account);
            //学生账号为空，学生还没有在本系统注册，需要让学生去注册
            if (ShiroKit.isEmpty(student)) {
                session.invalidate();
                sessionStatus.setComplete();
                return "redirect:/choose/course/nochoose";
            }
            try {
                student.setStuName(URLDecoder.decode(comsys_name, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            studentService.updateStudent(student);
            logger.debug("学生信息:" + student);
            SecurityUtils.getSubject().getSession().setAttribute("student", student);
//            return "redirect:/newsinfo/newslist";
        } else if (comsys_role.contains("ROLE_TEACHER")) {
            //获取老师的工号
            String teacherGongHao = (String) map.get("comsys_teaching_number");
            try {
                teacherGongHao = URLDecoder.decode(teacherGongHao, "UTF-8");
                logger.debug("查询老师工号:" + teacherGongHao);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            User user = userService.findByGonghao(teacherGongHao);
            logger.debug("查询老师信息:" + user);
            //老师账号为空，老师还没有在本系统中，需要超级管理员去添加
            if (ShiroKit.isEmpty(user)) {
                session.invalidate();
                sessionStatus.setComplete();
                return "redirect:/choose/course/nochoose";
            }
            user.setUsername(account);
            user.setNickname(comsys_name);
            logger.debug("老师信息:" + user);
            userService.update(user);
            SecurityUtils.getSubject().getSession().setAttribute("teacher", user);
//            return "redirect:/learning/kuangjia";
        }
        return "redirect:/newsinfo/newslist";
    }

    @GetMapping("/405")
    public String quanxian() {
        return "405";
    }
}

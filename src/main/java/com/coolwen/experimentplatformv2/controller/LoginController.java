package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.kit.ShiroKit;
import com.coolwen.experimentplatformv2.model.Admin;
import com.coolwen.experimentplatformv2.model.Student;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.AdminService;
import com.coolwen.experimentplatformv2.service.StudentService;
import com.coolwen.experimentplatformv2.utils.CasUtils;
import com.coolwen.experimentplatformv2.utils.LoginToken;
import com.coolwen.experimentplatformv2.utils.Message;
import com.coolwen.experimentplatformv2.utils.VerifyCode.IVerifyCodeGen;
import com.coolwen.experimentplatformv2.utils.VerifyCode.SimpleCharVerifyCodeGenImpl;
import com.coolwen.experimentplatformv2.utils.VerifyCode.VerifyCode;
import javafx.scene.control.Skin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author CoolWen
 * @version 2018-11-01 7:16
 */
@Controller
public class LoginController {

    protected static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private StudentService studentService;
    @Autowired
    private AdminService adminService;


    @GetMapping(value = {"/verifyCode"})
    public void Code(HttpServletRequest request, HttpServletResponse response) {
        IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        try {
            //设置长宽
            VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();
            //将VerifyCode绑定session
            request.getSession().removeAttribute("VerifyCode");
            request.getSession().setAttribute("VerifyCode", code);
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            logger.debug("错误");
        }
    }


    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "home_page/login";
    }

    /**
     * Cas登录成功后跳转的链接地址
     * <P></P>
     * 根据cas返回的信息
     * <P></P>
     * 判断当前用户身份类型
     * <P></P>
     * 根据当前用户类型指定跳转地址
     *
     * @return {@link  ModelAndView}
     */
    @GetMapping("/index")
    public ModelAndView index(Model model) {
        logger.debug("index_方法进入");
        Session session = SecurityUtils.getSubject().getSession();

        ModelAndView modelAndView = new ModelAndView();
        Map<Object, Object> map = CasUtils.getUserInfo(SecurityUtils.getSubject().getSession());
//        Subject subject = SecurityUtils.getSubject();
//        subject.getPrincipal();
//        ModelAndView model = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
//        Session session = subject.getSession();
        String comsys_role = (String) map.get("comsys_role");
        String number = (String) map.get("comsys_student_number");

        if (comsys_role.contains("ROLE_STUDENT")) {
            LoginToken token = new LoginToken("zhuzhiwen", ShiroKit.md5("123", "zhuzhiwen"), "student");
            subject.login(token);
//            Student student2 = (Student) subject.getPrincipal();
//            身份类型是学生
            Student student = studentService.findByStuXuehao(number);
            session.setAttribute("username", student.getStuUname());
            session.setAttribute("student", student);
            session.setAttribute("loginType", "student");
            modelAndView.setViewName("redirect:/newsinfo/newslist");
        } else {
            //            身份类型不是学生
            Admin admin = adminService.findByUname(number);
            if (admin != null) {
                session.setAttribute("admin", admin);
                modelAndView.setViewName("redirect:/learning/kuangjia");
            } else {
                modelAndView.setViewName("redirect:/newsinfo/newslist");
            }
        }

        return modelAndView;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("account") String username,
//    public String login(@RequestParam("account") String username,
                              @RequestParam("password") String password,
                              @RequestParam("type") String loginType,
                              @RequestParam("code") String loginCode,
                              Model model1) {
        //1:获取cookie里面的验证码信息
        ModelAndView model = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if (ShiroKit.isEmpty(session.getAttribute("VerifyCode"))) {
            model.setViewName("redirect:/learning/kuangjia");
        }
        String code = ((String) session.getAttribute("VerifyCode")).toLowerCase();//转换成小写;
        loginCode = loginCode.toLowerCase();
        logger.debug("loginCode:" + loginCode);
        if (!loginCode.equals(code)) {
            model.addObject("msg", "验证码错误");
            model.setViewName("home_page/login");
//            return model;
//            model1.addAttribute("msg1","验证码错误");
//            return "home_page/login";
        }
//        LoginToken token = new LoginToken(username, ShiroKit.md5(password, username), loginType);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Message message = new Message();
        try {
//            subject.login(token);
            if (loginType.equals("student")) {

//                Student student = (Student) subject.getPrincipal();
                //TODO 没有对学生验证
                Student student = studentService.findByUname(username);
                logger.debug("学生登陆信息" + student);
                if (ShiroKit.isEmpty(student)) {
                    model.addObject("msg", "此账号暂未通过审核!");
                    logger.debug("学生登陆信息");
                    model.setViewName("home_page/login");
                    return model;
                }
                if (!student.isStuCheckstate()) {
                    model.addObject("msg", "此账号暂未通过审核!");
                    model.setViewName("home_page/login");
                } else {
                    session.setAttribute("student", student);
//                    session.setAttribute("student", student);
                    session.setAttribute("loginType", loginType);
                    model.setViewName("redirect:/newsinfo/newslist");//设置登陆成功之后默认跳转页面
//                return "redirect:/newsinfo/newslist";
                }
            }
            if (loginType.equals("admin")) {
                subject.login(token);
                logger.debug("老师登陆");
                User admin = (User) subject.getPrincipal();
                session.setAttribute("admin", admin);
                model.setViewName("redirect:/learning/kuangjia");
//                return "redirect:/learning/kuangjia";
            }
        } catch (UnknownAccountException e) {
            //message.put("emsg","用户名/密码错误");
            model.addObject("msg", "用户名/密码错误");
            model.setViewName("home_page/login");
//            model1.addAttribute("msg1","用户名/密码错误");
        } catch (IncorrectCredentialsException e) {
            //message.put("emsg","用户名/密码错误");
            model.addObject("msg", "用户名/密码错误");
            model.setViewName("home_page/login");
//            model1.addAttribute("msg1","用户名/密码错误");
        } catch (ExcessiveAttemptsException e) {
            // TODO: handle exception
            //message.put("emsg","登录失败多次，账户锁定1小时!");
            model.addObject("msg", "登录失败多次，账户锁定1小时!");
            model.setViewName("home_page/login");
//            model1.addAttribute("msg1","登录失败多次，账户锁定1小时!");
        } catch (AuthenticationException e) {
            //message.put("emsg",e.getMessage());
            logger.debug(e.getMessage());
            model.addObject("msg", e.getMessage());
            //           logger.info("登录信息MSG:" + msg);
            model.setViewName("home_page/login");
//            model1.addAttribute("msg1",e.getMessage());
        } catch (ClassCastException e) {
            model.addObject("msg", "用户名/密码错误");
            //           logger.info("登录信息MSG:" + msg);
            model.setViewName("home_page/login");
        }
        logger.debug("登陆错误信息:" + message.get("emsg"));
//        return "home_page/login";
        return model;
    }


    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public ModelAndView register(@RequestParam("account") String username,
                                 @RequestParam("pass") String pass,
                                 @RequestParam("password") String password,
                                 @RequestParam("stu_xuehao") String stu_xuehao,
                                 @RequestParam("stu_isinschool") boolean stu_isinschool,
//                                 @RequestParam("class_id") String class_id,
                                 @RequestParam("tel") String tel,
                                 @RequestParam("nickname") String nickname) {
        ModelAndView model = new ModelAndView();
//        try {
        if (pass.equals(password)) {
//            Student student1 = studentService.findByUname(username);
            Student student1 = studentService.findByStuXuehao(stu_xuehao);
            if (student1 != null) {
                model.addObject("msg1", "用户名已存在");
                model.setViewName("register");
                return model;
            }
            Pattern p = Pattern.compile("^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$");
            Matcher m = p.matcher(tel);
            Pattern p1 = Pattern.compile("^$|^\\d{10}$");
            Matcher m1 = p1.matcher(stu_xuehao);
            if (!m.matches()) {
                model.addObject("telmsg", "请输入11位数字正确的电话号码");
                model.setViewName("register");
                return model;
            }
            if (!m1.matches()) {
                model.addObject("xuehaomsg", "请输入正确的学号");
                model.setViewName("register");
                return model;
            } else {
                Student student = new Student();
                student.setStuIsinschool(stu_isinschool);
//                    if (class_id != "") {
//                        student.setClassId(Integer.valueOf(class_id));
//                    }
                student.setStuUname(stu_xuehao);
                student.setStuPassword(password);
                if (stu_isinschool && stu_xuehao.equals("")) {
//                        throw new UserException("在校学生须填写学号!");
                    model.addObject("xuehaomsg", "在校学生须填写学号!");
                    model.setViewName("register");
                    return model;
                }
                if (!stu_xuehao.equals("")) {
                    Student student2 = studentService.findByStuXuehao(stu_xuehao);
                    if (student2 != null) {
//                            throw new UserException("学号已经被使用!");
                        model.addObject("xuehaomsg", "学号已经被使用!");
                        model.setViewName("register");
                        return model;
                    }
                    student.setStuXuehao(stu_xuehao);
                }
                student.setStuName(nickname);
                Student stu = studentService.findByStuMobile(tel);
                if (stu != null) {
//                        throw new UserException("手机号已被使用!");
//                        throw new Exception("发生错误");
                    model.addObject("telmsg", "手机号已被使用!");
                    model.setViewName("register");
                    return model;
                }
                logger.debug(">>>>>>>>>>>>>>>>>>>>>>>" + stu);
                logger.debug("Result {0}.", stu);
                student.setStuMobile(tel);
                studentService.addStudent(student);
//                logger.debug(student.toString());
                logger.debug("注册学生信息message: {}", student.toString());
                model.addObject("msg2", "注册成功！！！");
                model.setViewName("home_page/login");
            }
        } else {
            model.setViewName("register");
            model.addObject("msg3", "两次输入密码不同");
        }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return model;
    }

//    @GetMapping("/logout")
//    public String Logout() {
//        SecurityUtils.getSubject().logout();
//        logger.debug("fsdfasdasdgasdg");
//        return "redirect:/login";
//    }


    @PostMapping(value = {"/change"})//修改个人信息
    public ModelAndView change(@RequestParam("account") String username,
                               @RequestParam("password") String password,
                               @RequestParam("type") String registType,
                               @RequestParam("tel") String tel, HttpSession session) {
        ModelAndView model = new ModelAndView();
//        String uName = (String) session.getAttribute("username");
        if (((String) session.getAttribute("username")).equals("student")) {
            //studentService.updateByUnmae(uName);
        }
        if (((String) session.getAttribute("username")).equals("teacher")) {

        }
        return model;
    }

    @GetMapping(value = "/logout")
    public String loginOut(HttpSession session, SessionStatus sessionStatus) {
        SecurityUtils.getSubject().logout();
//        session.invalidate();
//        sessionStatus.setComplete();
        return "redirect:http://tysf.sctu.edu.cn:8071/app/index.action";
    }
}


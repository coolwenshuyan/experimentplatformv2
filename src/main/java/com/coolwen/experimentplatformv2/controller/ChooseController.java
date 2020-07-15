package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.kit.ShiroKit;
import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.service.ArrangeClassService;
import com.coolwen.experimentplatformv2.service.ClassService;
import com.coolwen.experimentplatformv2.service.CourseInfoService;
import com.coolwen.experimentplatformv2.service.StudentService;
import com.coolwen.experimentplatformv2.utils.Message;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CoolWen
 * @version 2020-05-06 21:46
 */
@Controller
@RequestMapping("/choose/course")
public class ChooseController {

    protected static final Logger logger = LoggerFactory.getLogger(ChooseController.class);

    @Autowired
    private StudentService studentService;
    @Autowired
    private ClassService classService;

    @Autowired
    private ArrangeClassService arrangeClassService;

    @Autowired
    private CourseInfoService courseInfoService;

    @GetMapping("list")
    public String choosecourse(Model model) {
        //获取登陆学生的信息
        logger.debug("登陆信息:laile" );
        Student student = (Student) SecurityUtils.getSubject().getSession().getAttribute("student");
        logger.debug("登陆信息:" + student);
        ClassModel classModel = classService.findClassById(student.getClassId());
        //学生还没有分班级
        String emsg = null;
        if (ShiroKit.isEmpty(classModel)) {
            emsg = "你还没有加入任何班级，等待老师分配班级！";
            SecurityUtils.getSubject().getSession().setAttribute("emsg", emsg);
            return "redirect:/choose/course/nochoose";
        }
        logger.debug("班级信息:" + classModel);
        //通过班级查询该班级所有的安排表
        List<ArrangeClass> arrangeClassList = arrangeClassService.findByClassId(classModel.getClassId());
        logger.debug("安排表信息:" + arrangeClassList);
        if (arrangeClassList.size() == 0) {
            emsg = "你所在的班级还没有进行排课，等待老师排课！";
            SecurityUtils.getSubject().getSession().setAttribute("emsg", emsg);
            return "redirect:/choose/course/nochoose";
        }
        //获取所有安排表的id
        List<Integer> arrageClassIds = arrangeClassList.stream().map(ArrangeClass -> ArrangeClass.getId()).collect(Collectors.toList());
        //通过安排表查询该班级的所有课程
        List<CourseInfo> courseInfoList = courseInfoService.findByArrangeClassIds(arrageClassIds);
        logger.debug("所有的考核课程信息:" + courseInfoList);
        model.addAttribute("courseInfoList", courseInfoList);
        return "/kuangjia/select";
    }

    @GetMapping("nochoose")
    public String choosecourseno(Model model) {
        String emsg = (String) SecurityUtils.getSubject().getSession().getAttribute("emsg");
        model.addAttribute("emsg", emsg);
        return "/kuangjia/nocoursechoose";
    }
}

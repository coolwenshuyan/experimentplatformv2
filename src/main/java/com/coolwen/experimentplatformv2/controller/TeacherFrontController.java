/**
 * 文件名：LearningeffectController.java
 * 修改人：xxxx
 * 修改时间：
 * 修改内容：新增
 */

package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.controller.HomepagesettingController.FileUploadController;
import com.coolwen.experimentplatformv2.dao.TeacherRepository;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.Teacher;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.CourseInfoService;
import com.coolwen.experimentplatformv2.service.TeacherService;
import com.coolwen.experimentplatformv2.utils.FileUploadUtil;
import com.coolwen.experimentplatformv2.utils.GetServerRealPathUnit;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
*@Description 首页-->师资队伍页面
*@Author 朱治汶
*@Version 1.0
*@Date 2020/5/30 11:06
*/
@Controller
@RequestMapping(value = "/teachersfront")
public class TeacherFrontController {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseInfoService courseInfoService;

    protected static final Logger logger = LoggerFactory.getLogger(TeacherFrontController.class);

    /**
     * 前端师资队伍页面
     */
//    @GetMapping(value = "/frontList")
//    public String TeacherFrontList(Model model, @RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum){
//        Pageable pageable = PageRequest.of(pageNum,100);
//        Page<Teacher> page = teacherRepository.findAll(pageable);
//        model.addAttribute("teacherPageInfo",page);
//        return "home_page/teachers";
//    }
    @GetMapping(value = "/frontList")
    public String teacherFrontList(Model model){
        List<CourseInfo> courseInfos = courseInfoService.findAll();
        model.addAttribute("courseInfos",courseInfos);
        int id = courseInfos.get(0).getId();

        List<Teacher> teachers = teacherService.findByCourseId(id);
        model.addAttribute("teachers",teachers);

        CourseInfo course = courseInfoService.findById(id);
        model.addAttribute("course",course);
        return "home_page/teachers";
    }

    @GetMapping(value = "/frontList/{id}")
    public String teacherFrontList1(Model model,@PathVariable int id){
        List<CourseInfo> courseInfos = courseInfoService.findAll();
        model.addAttribute("courseInfos",courseInfos);

        List<Teacher> teachers = teacherService.findByCourseId(id);
        model.addAttribute("teachers",teachers);

        CourseInfo course = courseInfoService.findById(id);
        model.addAttribute("course",course);
        return "home_page/teachers";
    }

}

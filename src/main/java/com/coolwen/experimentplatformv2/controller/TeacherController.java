/**
 * 文件名：LearningeffectController.java
 * 修改人：xxxx
 * 修改时间：
 * 修改内容：新增
 */

package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.config.ShiroCasConfiguration;
import com.coolwen.experimentplatformv2.controller.HomepagesettingController.FileUploadController;
import com.coolwen.experimentplatformv2.dao.TeacherRepository;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.DTO.QuestionStudentDto;
import com.coolwen.experimentplatformv2.model.DTO.StuTotalScoreCurrentDTO;
import com.coolwen.experimentplatformv2.model.KaoheModel;
import com.coolwen.experimentplatformv2.model.Teacher;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.CourseInfoService;
import com.coolwen.experimentplatformv2.service.TeacherService;
import com.coolwen.experimentplatformv2.utils.FileUploadUtil;
import com.coolwen.experimentplatformv2.utils.GetServerRealPathUnit;
import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Description 后台管理系统-->首页-->师资队伍页面
 * @Author 王宇
 * @Version 1.0
 * @Date 2020/5/30 11:06
 */
@Controller
@RequestMapping(value = "/teachers")
public class TeacherController {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseInfoService courseInfoService;

    FileUploadController fileUploadController = new FileUploadController();  //上传文件

    protected static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Value("${SimplePageBuilder.pageSize}")
    int size;

    /**
     * 后台管理系统，首页-->师资队伍列表查询
     *
     * @param model
     * @param pageNum
     * @return
     */
    @GetMapping(value = "/list/{courseinfoId}")
    public String TeacherList(Model model, HttpSession session,
                              @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum, @PathVariable int courseinfoId) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("登陆老师信息:" + user);
        model.addAttribute("selected", courseinfoId);
        List<CourseInfo> courseInfoList = courseInfoService.getclassByCharge(user.getId());
        model.addAttribute("courseInfoList", courseInfoList);
        if (courseinfoId == -1) {
//            展示全部老师信息
//            Page<Teacher> page = teacherService.findAllByCourseId(user.getId(), 6);
            logger.debug("courseinfoId信息:" + courseinfoId);
            Pageable pageable = PageRequest.of(pageNum, size);
            Page<Teacher> page = teacherService.findAllByUid(size, pageable);
            model.addAttribute("teacherPageInfo", page);
        } else {
            //查询代老师和课程ID的师资队伍
//        Page<Teacher> page = teacherRepository.findAll(pageable);
            Page<Teacher> page = teacherService.findAllByCourseIdAndTeacherId(size, courseinfoId, user.getId());
            logger.debug("登陆老师信息:" + page.getContent());
            model.addAttribute("teacherPageInfo", page);
        }
        return "shouye/teacher_change";
    }

    @GetMapping("/{courseId}/list")
    public String getTotalScoreCirrentByGroupId(Model model, HttpSession session,
                                                @PathVariable int courseId,
                                                @RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum) {
        Page<Teacher> page = teacherService.findAllByCourseId(pageNum, courseId);
        logger.debug("courseId:>>" + courseId);
        model.addAttribute("teacherPageInfo", page);

        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("user:>>" + user);
        List<CourseInfo> courseInfoList = courseInfoService.getclassByCharge(user.getId());
        model.addAttribute("courseInfoList", courseInfoList);
        return "shouye/teacher_change";
    }

    /**
     * 后台管理系统 师资队伍单条删除
     */
    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable int id) {
        teacherService.delete(id);
        return "redirect:/teachers/list";
    }

    /**
     * 跳转到师资队伍添加页面
     */
    @GetMapping(value = "/add")
    public String TeacherAdd(Model model, HttpSession session) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        List<CourseInfo> courseInfoList = courseInfoService.getclassByCharge(user.getId());
        model.addAttribute("courseInfoList", courseInfoList);
        return "shouye/teacher_add";
    }


    /**
     * 完成师资添加
     *
     * @param teacher 前端返回的参数（person_name，intro）
     *                //     * @param file  前端传回的图片
     * @param req
     * @return 重定向到师资队伍列表
     */
    @PostMapping(value = "/add")
    public String add(Teacher teacher, @RequestParam("attachs") MultipartFile[] attachs, HttpServletRequest req) {
        //储存图片，并将图片路径储存到数据库
        String realpath = GetServerRealPathUnit.getPath("static/upload/");
//       logger.debug("realPath:" + realpath);
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                continue;
            }
            //图片验证重命名
            String picName = FileUploadUtil.picRename(attach.getContentType());
            String path = realpath + "/" + picName;
//            logger.debug(path);
            File f = new File(path);
//            user.setImg(picName);
            teacher.setImage_url(picName);
            try {
                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        teacherService.add(teacher);
        return "redirect:/teachers/list";
    }

//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String add(@Valid User user, BindingResult bindingResult, @RequestParam("attachs") MultipartFile[] attachs) {
//        if (bindingResult.hasErrors()) {
//            throw new UserException(bindingResult.getFieldError().getDefaultMessage());
////            return bindingResult.getFieldError().getDefaultMessage();
//        }
//        String realpath = GetServerRealPathUnit.getPath("static/upload/");
////       logger.debug("realPath:" + realpath);
//        for (MultipartFile attach : attachs) {
//            if (attach.isEmpty()) {
//                continue;
//            }
//            //图片验证重命名
//            String picName = FileUploadUtil.picRename(attach.getContentType());
//            String path = realpath + "/" + picName;
////            logger.debug(path);
//            File f = new File(path);
//            user.setImg(picName);
//            try {
//                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        userService.insert(user);
//        return "redirect:/user/users";
//    }

    //进入修改界面
    @GetMapping(value = "/{id}/update")
    public String update(@PathVariable int id, Model model, HttpSession session) {
        Teacher teacher = teacherService.findById(id);
        model.addAttribute("teacher", teacher);

        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        List<CourseInfo> courseInfoList = courseInfoService.getclassByCharge(user.getId());
        model.addAttribute("courseInfoList", courseInfoList);
        return "shouye/teacher_updata";
    }

    //完成修改操作
    @PostMapping(value = "/{id}/update")
    public String update(@PathVariable int id, Teacher teacher, @RequestParam("attachs") MultipartFile[]
            attachs, HttpServletRequest req) {
        teacher.setId(id);
        //储存图片，并将图片路径储存到数据库
        String realpath = GetServerRealPathUnit.getPath("static/upload/");
//       logger.debug("realPath:" + realpath);
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                teacher.setImage_url(teacherService.findById(id).getImage_url());
                continue;
            }
            //图片验证重命名
            String picName = FileUploadUtil.picRename(attach.getContentType());
            String path = realpath + "/" + picName;
//            logger.debug(path);
            File f = new File(path);
//            user.setImg(picName);
            teacher.setImage_url(picName);
            try {
                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        teacherService.add(teacher);
        logger.debug("修改成功");
        return "redirect:/teachers/list";
    }

}

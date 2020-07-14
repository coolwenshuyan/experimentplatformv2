package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.CourseInfoRepository;
import com.coolwen.experimentplatformv2.dao.KaoHeModelScoreRepository;
import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.service.*;
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
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 朱治汶
 * @version 1.0
 * @date 2020/7/4 23:48
 **/

@Controller
@RequestMapping(value = "/courseinfo")
public class CourseInfoController {
    protected static final Logger logger = LoggerFactory.getLogger(CourseInfoController.class);

    @Autowired
    CourseInfoService courseInfoService;
    @Autowired
    CourseInfoRepository courseInfoRepository;
    @Autowired
    UserService userService;
    @Autowired
    ArrangeClassService arrangeClassService;
    @Autowired
    KaoheModelService kaoheModelService;
    @Autowired
    KaoHeModelScoreRepository kaoHeModelScoreRepository;
    @Autowired
    ModuleTestAnswerStuService moduleTestAnswerStuService;
    @Autowired
    CollegeReportService collegeReportService;
    @Autowired
    ReportAnswerService reportAnswerService;
    @Autowired
    ExpModelService expModelService;
    @Autowired
    ModuleTestQuestService moduleTestQuestService;
    @Autowired
    ModuleTestAnswerService moduleTestAnswerService;
    @Autowired
    ReportService reportService;



    @GetMapping(value = "/list")
    public String courseInfoList(Model model, @RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum){

        Pageable pageable = PageRequest.of(pageNum,10);
        Page<CourseInfo> page = courseInfoRepository.findAll(pageable);
        model.addAttribute("courseInfos",page);

        List<User> userList = userService.list();
        model.addAttribute("userList",userList);
        return "jichu/timetable_management";
    }


    @GetMapping(value = "/add")
    public String courseInfoAdd(Model model){
        List<User> userList = userService.list();
        model.addAttribute("userList",userList);
        return "jichu/timetable_new";
    }


    @PostMapping(value = "/add")
    public String add(CourseInfo courseInfo, @RequestParam("attachs") MultipartFile[] attachs, HttpServletRequest req){
        //储存图片，并将图片路径储存到数据库
        String realpath = GetServerRealPathUnit.getPath("static/upload/");
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                continue;
            }
            //图片验证重命名
            String picName = FileUploadUtil.picRename(attach.getContentType());
            String path = realpath + "/" + picName;
            File f = new File(path);
            courseInfo.setCourseImgurl(picName);
            try {
                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将信息储存到数据库
        courseInfoService.add(courseInfo);
        return "redirect:/courseinfo/list";
    }


    @GetMapping(value = "/{id}/update")
    public String update(@PathVariable int id, Model model){
        //查询到id所对应的整条数据
        CourseInfo courseInfo = courseInfoService.findById(id);
        model.addAttribute("courseInfo",courseInfo);

        List<User> userList = userService.list();
        model.addAttribute("userList",userList);
        return "jichu/timetable_update";
    }


    @PostMapping(value = "/{id}/update")
    public String update(@PathVariable int id, CourseInfo courseInfo, @RequestParam("attachs") MultipartFile[] attachs, HttpServletRequest req){

        //储存图片，并将图片路径储存到数据库
        String realpath = GetServerRealPathUnit.getPath("static/upload/");
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                courseInfo.setCourseImgurl(courseInfoService.findById(id).getCourseImgurl());
                continue;
            }
            //图片验证重命名
            String picName = FileUploadUtil.picRename(attach.getContentType());
            String path = realpath + "/" + picName;
            File f = new File(path);
            courseInfo.setCourseImgurl(picName);
            try {
                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将信息储存到数据库
        courseInfoService.add(courseInfo);
        return "redirect:/courseinfo/list";
    }


    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable int id){
        List<ArrangeClass> arrangeClasses = arrangeClassService.findByCourseID(id);
        logger.debug("根据课程id查询到所有相关课程安排:"+arrangeClasses);

        List<ExpModel> expModels = expModelService.findByCourseId(id);

        for (ExpModel expModel : expModels){
            int mid = expModel.getM_id();
            //删除考核模块测试答案学生记录
            moduleTestAnswerStuService.deleteByModelId(mid);
            //删除学院版报告学生记录
            collegeReportService.deleteByModelId(mid);
            //删除自定义版答题报告学生记录
            reportAnswerService.deleteByModelId(mid);
            //删除学生考核模块记录
            List<KaoHeModelScore> kaoHeModelScores = kaoHeModelScoreRepository.findKaoheModuleScoreByModelId(mid);
            kaoHeModelScoreRepository.deleteAll(kaoHeModelScores);
            //删除实验模块测试题的答案
            moduleTestAnswerService.deleteAnswerByModelId(mid);
            //删除实验模块测试题目
            moduleTestQuestService.deleteQuestByModelId(mid);
            //删除实验模块报告测试题
            reportService.deleteReportByModelId(mid);
        }
        for (ArrangeClass a : arrangeClasses){
            int arrangeId = a.getId();
            //删除学生总评成绩
            arrangeClassService.deleteTotalScoreByArrangeId(arrangeId);
            //删除考核模块
            kaoheModelService.deleteByArrangeId(arrangeId);
            //删除安排表
            arrangeClassService.delete(arrangeId);
        }

        //删除实验模块
        expModelService.deleteAll(expModels);
        //执行删除操作
        courseInfoService.delete(id);
        return "redirect:/courseinfo/list";
    }
}

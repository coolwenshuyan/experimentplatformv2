package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.kit.ShiroKit;
import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto2;
import com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto3;
import com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto4;
import com.coolwen.experimentplatformv2.model.DTO.KaoHeModuleInfo;
import com.coolwen.experimentplatformv2.service.*;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private TotalScorePassService totalScorePassService;

    @Autowired
    private TotalScoreCurrentService totalScoreCurrentService;

    @Autowired
    private KaoHeModelScoreService kaoHeModelScoreService;

    @Autowired
    private KaoheModelService kaoheModelService;

    @GetMapping("list")
    public String choosecourse(Model model) {


        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        if (!ShiroKit.isEmpty(user)) {
            return "redirect:/learning/kuangjia";
        }
        //获取登陆学生的信息
        logger.debug("登陆信息:laile");
        Student student = (Student) SecurityUtils.getSubject().getSession().getAttribute("student");
        logger.debug("登陆信息:" + student);
        ClassModel classModel = classService.findClassById(student.getClassId());
        logger.debug("班级信息:" + classModel);
        //学生还没有分班级
        String emsg = null;
        if (ShiroKit.isEmpty(classModel)) {
            emsg = "你还没有加入任何班级，等待老师分配班级！";
            SecurityUtils.getSubject().getSession().setAttribute("emsg", emsg);
            model.addAttribute("emsg", emsg);
            return "redirect:/choose/course/nochoose";
        }
        logger.debug("班级信息:" + classModel);
//        //通过班级查询该班级所有的安排表
//        List<ArrangeClass> arrangeClassList = arrangeClassService.findByClassId(classModel.getClassId());
//        logger.debug("安排表信息:" + arrangeClassList);
//        if (arrangeClassList.size() == 0) {
//            emsg = "你所在的班级还没有进行排课，等待老师排课！";
//            SecurityUtils.getSubject().getSession().setAttribute("emsg", emsg);
//            return "redirect:/choose/course/nochoose";
//        }
//        //获取所有安排表的id
//        List<Integer> arrageClassIds = arrangeClassList.stream().map(ArrangeClass -> ArrangeClass.getCourseId()).collect(Collectors.toList());
//        logger.debug("过滤的ID："+arrageClassIds);
//        //通过安排表查询该班级的所有课程
//        List<CourseInfo> courseInfoList = courseInfoService.findByArrangeClassIds(arrageClassIds);
//        logger.debug("所有的考核课程信息:" + courseInfoList);
//        model.addAttribute("courseInfoList", courseInfoList);

        List<CourseInfoDto2> courseInfoList = courseInfoService.findByArrangeCourseInfoDto2byClassId(classModel.getClassId());
        if (courseInfoList.size() == 0) {
            emsg = "你所在的班级还没有进行排课，等待老师排课！";
            SecurityUtils.getSubject().getSession().setAttribute("emsg", emsg);
            return "redirect:/choose/course/nochoose";
        }
        logger.debug("所有的考核课程信息:" + courseInfoList);
        List<CourseInfoDto3> courseInfoDto3List = new ArrayList<>();
        for (CourseInfoDto2 courseInfoDto2 : courseInfoList){
            TotalScorePass totalScorePass = totalScorePassService.findTotalScorePassByStuIdAndCourseId(student.getId(),courseInfoDto2.getCourseInfoId());
//            TotalScoreCurrent totalScoreCurrent = totalScoreCurrentService.findTotalScoreCurrentByStuIdAndArrageId(student.getId(),courseInfoDto2.getArrangeId());
            if(totalScorePass != null) {
                CourseInfoDto3 courseInfoDto3 = new CourseInfoDto3(courseInfoDto2, totalScorePass.getKaoheNum(), totalScorePass.getTotalScore());
                courseInfoDto3List.add(courseInfoDto3);
            }
//            }else if(totalScoreCurrent != null){
//                CourseInfoDto3 courseInfoDto3 = new CourseInfoDto3(courseInfoDto2,totalScoreCurrent.getKaoheNum(),totalScoreCurrent.getTotalScore());
//                courseInfoDto3List.add(courseInfoDto3);
//            }
        }
        logger.debug("-----------"+courseInfoDto3List);
        model.addAttribute("courseInfoList", courseInfoDto3List);
        return "kuangjia/yi";
//        return "kuangjia/wei";
    }

    @GetMapping("/noList")
    public String chooseNoFinfishCourse(Model model, HttpSession session) {

        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        if (!ShiroKit.isEmpty(user)) {
            return "redirect:/learning/kuangjia";
        }
        //获取登陆学生的信息
        logger.debug("登陆信息:laile");
        Student student = (Student) SecurityUtils.getSubject().getSession().getAttribute("student");
        logger.debug("登陆信息:" + student);
        ClassModel classModel = classService.findClassById(student.getClassId());
        logger.debug("班级信息:" + classModel);
        //学生还没有分班级
        String emsg = null;
        if (ShiroKit.isEmpty(classModel)) {
            emsg = "你还没有加入任何班级，等待老师分配班级！";
            SecurityUtils.getSubject().getSession().setAttribute("emsg", emsg);
            model.addAttribute("emsg", emsg);
            return "redirect:/choose/course/nochoose";
        }
        logger.debug("班级信息:" + classModel);

//        Student student = (Student) SecurityUtils.getSubject().getSession().getAttribute("student");
        List<CourseInfoDto2> courseInfoList = courseInfoService.findByArrangeCourseInfoDto2byClassId(student.getClassId());
        List<CourseInfoDto2> courseInfoDto2s = new ArrayList<>();
//        if(courseInfoList.size() == 0){
//            logger.debug("12123131231231");
//        }
        for (CourseInfoDto2 courseInfoDto2 : courseInfoList){
            logger.debug(courseInfoDto2.toString());
            TotalScorePass totalScorePass = totalScorePassService.findTotalScorePassByStuIdAndCourseId(student.getId(),courseInfoDto2.getCourseInfoId());
//            TotalScoreCurrent totalScoreCurrent = totalScoreCurrentService.findTotalScoreCurrentByStuIdAndArrageId(student.getId(),courseInfoDto2.getArrangeId());
            if(totalScorePass == null){
                courseInfoDto2s.add(courseInfoDto2);
            }
        }
        if(courseInfoDto2s.size() == 0){
            return "kuangjia/wei";
        }

        long nowDate = new Date().getTime();
        List<CourseInfoDto4> courseInfoDto4List = new ArrayList<>();
        for(CourseInfoDto2 courseInfoDto2 : courseInfoDto2s){
            long willBegin = Long.MAX_VALUE;
            long willFinfish = Long.MAX_VALUE;
            String beginName = "";
            String endName = "";
            TotalScoreCurrent totalScoreCurrent = totalScoreCurrentService.findTotalScoreCurrentByStuId2(student.getId(),courseInfoDto2.getArrangeId());
            int countNotFinishNum = kaoHeModelScoreService.countNotFinishedModule(student.getId(),courseInfoDto2.getArrangeId());
            List<KaoHeModuleInfo> kaoheModelList = kaoheModelService.findKaoheModelByArrange_id(courseInfoDto2.getArrangeId());
            for (KaoHeModuleInfo kh : kaoheModelList){
                long comStart = kh.getKaohe_starttime().getTime();
                long comEnd = kh.getKaohe_endtime().getTime();
                if(( comStart - nowDate) > 0 && comStart < willBegin){
                    willBegin = comStart;
                    beginName = kh.getName();
                }
                if((comEnd - nowDate) > 0 && comEnd < willFinfish){
                    willFinfish = comEnd;
                    endName = kh.getName();
                }
            }
            CourseInfoDto4 courseInfoDto4 = new CourseInfoDto4(courseInfoDto2,countNotFinishNum,beginName,endName);
            courseInfoDto4.setTotalScore(totalScoreCurrent.getTotalScore());
            courseInfoDto4List.add(courseInfoDto4);
        }

        model.addAttribute("info",courseInfoDto4List);
//        session.setAttribute("UnscheduledCourses","您选择的课程未被安排！请联系任课教师安排。");
        try {
            model.addAttribute("UnscheduledCourses",session.getAttribute("UnscheduledCourses"));
            session.removeAttribute("UnscheduledCourses");
        }catch (Exception e){}


        return "kuangjia/wei";
    }




    @GetMapping("nochoose")
    public String choosecourseno(Model model) {
        String emsg = (String) SecurityUtils.getSubject().getSession().getAttribute("emsg");
        logger.debug("emg信息:" + emsg);
        model.addAttribute("emsg", emsg);
        return "kuangjia/nocoursechoose";
    }
}

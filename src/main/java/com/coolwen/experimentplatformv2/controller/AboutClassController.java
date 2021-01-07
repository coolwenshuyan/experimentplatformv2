package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.kit.ShiroKit;
import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.model.DTO.CollegeReportStuExpDto;
import com.coolwen.experimentplatformv2.model.DTO.ExpModelDto;
import com.coolwen.experimentplatformv2.model.DTO.OverallScoreDto;
import com.coolwen.experimentplatformv2.model.DTO.QuestionStudentDto;
import com.coolwen.experimentplatformv2.service.*;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 朱治汶
 * @version 1.0
 * @className AboutClassController
 * @description TODO
 * @date 2020/12/31 9:01
 **/

@Controller
@RequestMapping(value = "/newsinfo/aboutClass")
public class AboutClassController {

    protected static final Logger logger = LoggerFactory.getLogger(AboutClassController.class);

    @Autowired
    CourseInfoService courseInfoService;

    @Autowired
    ArrangeClassService arrangeClassService;

    @Autowired
    ExpModelService expModelService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CollegeReportService collegeReportService;

    @Autowired
    NewsInfoService newsInfoService;

    /**
     * 通过首页课程展示，查看课程详情
     * @param courseId  课程id
     * @param model
     * @return
     */
    @GetMapping(value = "/{courseId}")
    public String courseInfoList(@PathVariable int courseId, Model model,
                                 @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum,
                                 @RequestParam(defaultValue = "0", required = true, value = "isreply") Integer isreply,
                                 @RequestParam(defaultValue = "0", required = true, value = "pageNum1") Integer pageNum1,
                                 HttpSession session) {

        session.setAttribute("question_courseId", courseId);
        session.setAttribute("preciseCourseid", courseId);
        session.setAttribute("CourseDetails","CourseDetails");
        model.addAttribute("courseId",courseId);

        Student student = null;
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        if (ShiroKit.isEmpty(user)) {
            //获取登陆学生的信息
            student = (Student) SecurityUtils.getSubject().getSession().getAttribute("student");
        }

        //查询课程信息
        CourseInfo courseInfo = courseInfoService.findById(courseId);
        model.addAttribute("courseInfo",courseInfo);

        //当前正在参与课程的人数
        int participantsNum = arrangeClassService.findNumberOfParticipants(courseId);
        logger.debug("课程正在参与人数:" + participantsNum);
        model.addAttribute("participantsNum",participantsNum);
        //完成人数
        int completeNum = courseInfoService.findOneCourseInfoPassNum(courseId);
        model.addAttribute("CompleteNum",completeNum);

        //全部实验模块
//        List<ExpModel> expModels = expModelService.findOneCourseModel(courseId);
//        model.addAttribute("expModels",expModels);

        List<ExpModelDto> expModelDtos = expModelService.findExpModelDtoByID(courseId);
        for (ExpModelDto expModelDto : expModelDtos){
            //查询到当前模块的考核人数
            int currentParticipants = expModelService.findKaoheNumByMid(expModelDto.getMid());
            expModelDto.setCurrentParticipants(currentParticipants);
            //累计考核总人数
            int passNum = expModelService.findExpModelStuPassNum(expModelDto.getmName());
            expModelDto.setAllNum(currentParticipants+passNum);

            if (!ShiroKit.isEmpty(student)){
                //是否为考核模块
                KaoheModel kaoheModel = expModelService.findIsKaohe(expModelDto.getMid(),student.getId());
                if (kaoheModel != null){
                    expModelDto.setNeedKaohe(true);
                }
            }
        }
        model.addAttribute("expModels",expModelDtos);

        //课程评论
        logger.debug("接口信息: + /question/list");
        Question question = new Question();
        logger.debug("课程ID:" + courseId);
        //logger.debug("day:" + day);
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
        //model.addAttribute("day", day);
        model.addAttribute("courseId", courseId);
        model.addAttribute("isreply", isreply);
        List<CourseInfo> courseInfoList = courseInfoService.findAll();
        model.addAttribute("courseInfoList", courseInfoList);
        logger.debug("查询信息:" + question);
        Page<QuestionStudentDto> page = questionService.findAllByCourseId(pageNum, question);
//        page.getContent()
        logger.debug("问题信息:" + page.getContent());
        model.addAttribute("questionPageInfo", page);

        //实验报告展示
        //查出当前课程所有的实验报告
        List<CollegeReportStuExpDto> collegeReports1 = collegeReportService.findByCourseId(courseId);
        logger.debug("查出实验报告:" + collegeReports1);
        //返回总数
        model.addAttribute("allnum", collegeReports1.size());
        //分页
        Pageable pageable = PageRequest.of(pageNum1, 12);
        Page<CollegeReportStuExpDto> collegeReports = collegeReportService.findByCourseIdPag(courseId,pageable);
        model.addAttribute("collegeReports", collegeReports);
        //课程学习排行榜
        List list = newsInfoService.findClassScoreRanking(courseId);
        OverallScoreDto[] overallScoreDtos = new OverallScoreDto[list.size()];
        for(int i=0;i<list.size();i++) {
            Object[] obj = (Object[]) list.get(i);
            overallScoreDtos[i] = new OverallScoreDto(
                    String.valueOf(obj[0]),
                    String.valueOf(obj[1]),
                    String.valueOf(obj[2])
            );
        }
        logger.debug("overallScoreDtos:"+overallScoreDtos.toString());
        model.addAttribute("overallScoreDtos",overallScoreDtos);

        return "home_page/aboutClass(1)";
    }

}

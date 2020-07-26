package com.coolwen.experimentplatformv2.controller;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import com.coolwen.experimentplatformv2.config.ShiroConfig;
import com.coolwen.experimentplatformv2.dao.KaoHeModelScoreRepository;
import com.coolwen.experimentplatformv2.kit.ShiroKit;
import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.model.DTO.KaoHeModelStuDTO;
import com.coolwen.experimentplatformv2.model.DTO.KaoheModuleProgressDTO;
import com.coolwen.experimentplatformv2.model.DTO.KaoheProgressMainDTO;
import com.coolwen.experimentplatformv2.model.EasyPOI.CourseAndModelImport;
import com.coolwen.experimentplatformv2.model.EasyPOI.QuestionsImport;
import com.coolwen.experimentplatformv2.service.*;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Description 后台管理系统 模块信息管理
 * @Author 张健银
 * @Version 1.0
 * @Date 2020/5/31
 */


@Controller
@RequestMapping("/expmodel")
public class ExpModelController {

    @Value("${web.template-path}")
    String templatePath;

    @Autowired
    FIleService fIleService;
    @Autowired
    ExpModelService expModelService;
    @Autowired
    KaoheModelService kaoheModelService;
    @Autowired
    ModuleTestAnswerService moduleTestAnswerService;
    @Autowired
    ModuleTestQuestService moduleTestQuestService;
    @Autowired
    ReportService reportService;
    @Autowired
    ReportAnswerService reportAnswerService;
    @Autowired
    KaoHeModelScoreService kaoHeModelScoreService;
    @Autowired
    TotalScoreCurrentService totalScoreCurrentService;
    @Autowired
    ClazzService clazzService;
    @Autowired
    CollegeReportService collegeReportService;
    @Autowired
    DockerService dockerService;
    @Autowired
    CourseInfoService courseInfoService;

    @Autowired
    UserService userService;

    @Autowired
    ArrangeClassService arrangeClassService;
    @Autowired
    ModuleTestAnswerStuService moduleTestAnswerStuService;
    @Autowired
    KaoHeModelScoreRepository kaoHeModelScoreRepository;
    @Autowired
    ScoreUpdateService scoreUpdateService;

    protected static final Logger logger = LoggerFactory.getLogger(ExpModelController.class);


    //查询模块信息页面
    @GetMapping("/list")
    public String expModelList(Model model,
                               @RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum,
                               HttpSession session
    ) {
        try {
            int courseId = (int) session.getAttribute("ExpModelcourseId");
            return "redirect:/expmodel/expModelListByCourseId/" + courseId;
        } catch (Exception e) {
        }
        logger.debug("expModelList>>>>>>>>>>>>");
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("expModelList输出老师账号：" + user);
        List<CourseInfo> courseInfoList = courseInfoService.getclassByCharge(user.getId());
//        List<CourseInfo> courseInfoList = courseInfoService.getclassByCharge(1);
        model.addAttribute("courseInfoList", courseInfoList);
        boolean choose = false;
        model.addAttribute("Choose", choose);
//        model.addAttribute("page", expModelService.findModelList(pageNum));


        return "shiyan/lookExpModel";
    }

    //查询模块信息页面
    @GetMapping("/expModelListByCourseId/{courseId}")
    public String expModelListByCourseId(Model model, @PathVariable("courseId") int courseId, @RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum, HttpSession session) {
        boolean choose = true;
        if (courseId == -1) {
            choose = false;
            model.addAttribute("Choose", choose);
            session.removeAttribute("ExpModelcourseId");
//            model.addAttribute("selected1", "/report/allModule");
            return "redirect:/expmodel/list";
        }
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("expModelListByCourseId输出老师账号：" + user);
//        List<CourseInfo> courseInfoList = courseInfoService.getclassByCharge(user.getId());
        List<CourseInfo> courseInfoList = courseInfoService.getclassByCharge(user.getId());
        model.addAttribute("courseInfoList", courseInfoList);
//        model.addAttribute("page", expModelService.findModelList(pageNum));
        //绑定courseId，以便下拉框选中筛选的课程
        model.addAttribute("selected", courseId);
        //判断是否选择了安排
        model.addAttribute("Choose", choose);
        logger.debug("课程信息为:：" + courseInfoService.findById(courseId).getCourseName());
        Page<ExpModel> expModelPage = expModelService.findOneCourseModelList(courseId, pageNum);
        model.addAttribute("page", expModelPage);
        logger.debug("模块信息:：" + expModelPage.getContent());
        session.setAttribute("ExpModelcourseId", courseId);

        return "shiyan/lookExpModel";
    }

    //添加模块信息页面
    @GetMapping("/addExpModel")
    public String toAdd(Model model, HttpSession session) {
        int courseId = (int) session.getAttribute("ExpModelcourseId");
        CourseInfo courseInfo = courseInfoService.findById(courseId);
        model.addAttribute("CourseInfo", courseInfo);
        return "shiyan/newExpModel";
    }

    //进行模块添加操作
    @PostMapping("/addExpModel")
    public String Add(String m_name,
                      String m_manager,
                      String m_type,
                      int m_classhour,
                      String m_inurl,
                      MultipartFile m_image,
                      boolean report_type,
                      HttpServletRequest request,
                      HttpSession session
    ) {
        ExpModel expModel = new ExpModel();
        String file = fIleService.upload(request, m_image);
        expModel.setM_name(m_name);
        expModel.setM_manager(m_manager);
        expModel.setM_type(m_type);
        expModel.setClasshour(m_classhour);
        expModel.setM_inurl(m_inurl);
        expModel.setImageurl(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ExperimentPlatform/ExpModelImage/" + file);
        report_type = true;
        expModel.setReport_type(report_type);
        int courseId = (int) session.getAttribute("ExpModelcourseId");
        expModel.setCourseId(courseId);
        logger.debug("课程信息为:：" + courseInfoService.findById(courseId).getCourseName());
        logger.debug("添加模块信息为:：" + expModel);
        expModelService.save(expModel);
        session.setAttribute("modelId", expModel.getM_id());
        return "redirect:/expmodel/addTheory";
    }

    //进行模块删除
    @GetMapping("/deleteExpModel/{id}")
    public String delete(@PathVariable("id") int id) {

        ExpModel expModel1 = expModelService.findExpModelByID(id);

        List<ArrangeClass> arrangeClasses = arrangeClassService.findByCourseID(expModel1.getCourseId());
        logger.debug("根据课程id查询到所有相关课程安排:" + arrangeClasses);

        int mid = expModel1.getM_id();
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
        //删除实验模块
        expModelService.deleteExpModelById(mid);

        for (ArrangeClass a : arrangeClasses) {
            scoreUpdateService.allStudentScoreUpdate2(a.getId());
        }


//        //删除考核模块以及更改学生相关成绩
//        KaoheModel kaoheModel = kaoheModelService.findKaoheModelByMid(id);
//        if (kaoheModel != null) {
//            //如果该模块是考核模块
//            //根据学生条件与考核模块找到学生考核模块成绩
//            List<KaoHeModelScore> kaoHeModelScores = kaoHeModelScoreService.findKaoHeModelScoreByTKaohemodleIdAndStuId(kaoheModel.getId());
//            for (KaoHeModelScore k : kaoHeModelScores) {
//                //更新学生在删除该考核模块后的当期总评成绩表信息
//                int arrageId = 0;
//                TotalScoreCurrent totalScoreCurrent = totalScoreCurrentService.findTotalScoreCurrentByStuId2(k.getStuId(), arrageId);
//                totalScoreCurrent.setKaoheNum(totalScoreCurrent.getKaoheNum() - 1);
//                totalScoreCurrent.setmTotalScore(totalScoreCurrent.getmTotalScore() - k.getmScore());
//                totalScoreCurrent.setTotalScore(totalScoreCurrent.getmTotalScore() * kaoheModel.getKaohe_baifenbi() + totalScoreCurrent.getTestScore() * kaoheModel.getTest_baifenbi());
//                totalScoreCurrentService.add(totalScoreCurrent);
//            }
//            //删除该考核模块与模块信息
//            kaoHeModelScoreService.deleteAllKaohe(kaoHeModelScores);
//            kaoheModelService.deleteKaoHeModuleByMid(kaoheModel);
//        }
//        //删除所属的问题以及答案以及相关回复
//        List<ModuleTestQuest> moduleTestQuests = expModelService.findModuleTestQuestByMId(id);
//        for (ModuleTestQuest m : moduleTestQuests) {
//            List<ModuleTestAnswer> moduleTestAnswers = moduleTestAnswerService.findAllByQuestId(m.getQuestId());
//            moduleTestAnswerService.deleteAllAnswer(moduleTestAnswers);
//            expModelService.deleteModuleTestAnswerStuByQuestId(m.getQuestId());
//        }
//        moduleTestQuestService.deleteAllModuleTestQuest(moduleTestQuests);
//        //删除实验模块报告与实验报告回答
//        List<Report> reports = reportService.findReportByMId(id);
//        if (reports != null && !reports.isEmpty()) {
//            for (Report r : reports) {
//                reportAnswerService.deleteReportAnswerByReportId(r.getReportId());
//            }
//        }
//        List<CollegeReport> collegeReportList = collegeReportService.findCollegeReportByMid(id);
//        if (collegeReportList != null && !collegeReportList.isEmpty()) {
//            collegeReportService.deleteCollegeList(collegeReportList);
//        }
//        reportService.deleteReports(reports);
//        expModelService.deleteExpModelById(id);
        return "redirect:/expmodel/list";
    }

    //进入模块更新
    @GetMapping("/updateExpModel/{id}")
    public String toUpdate(@PathVariable("id") int id, Model model, HttpServletRequest request, HttpSession session) {
        ExpModel expModel = expModelService.findExpModelByID(id);
        model.addAttribute("preExpModel", expModel);
        model.addAttribute("image", expModel.getImageurl());
        session.setAttribute("modelId", expModel.getM_id());
        return "shiyan/changeExpModel";
    }

    //进行模块更新
    @PostMapping("/updateExpModel/{id}")
    public String updateExpModedl(String m_name,
                                  String m_manager,
                                  String m_type,
                                  int m_classhour,
                                  String m_inurl,
                                  MultipartFile m_image,
                                  boolean report_type,
                                  HttpServletRequest request,
                                  @PathVariable("id") int id
    ) {
        ExpModel preExpModel = expModelService.findExpModelByID(id);
        preExpModel.setM_name(m_name);
        preExpModel.setM_manager(m_manager);
        preExpModel.setM_type(m_type);
        preExpModel.setClasshour(m_classhour);
        preExpModel.setM_inurl(m_inurl);
        preExpModel.setReport_type(report_type);
        String path = fIleService.upload(request, m_image);
        if (path != null) {
            preExpModel.setImageurl(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ExperimentPlatform/ExpModelImage/" + path);
        }
        if (report_type == false) {
            //修改为自定义版删学院版答题记录
            collegeReportService.deleteCollege(id);
        } else {
            List<Report> reportList = reportService.findReportByMId(id);
            for (Report r : reportList) {
                reportAnswerService.deleteReportAnswerByReportId(r.getReportId());
            }
        }
        expModelService.save(preExpModel);
        return "redirect:/expmodel/list";
    }

    //进入理论添加
    @GetMapping("/addTheory")
    public String toAddTheory(HttpSession session, Model model) {
        model.addAttribute("id", session.getAttribute("modelId"));
        return "shiyan/newTheory";
    }

    //执行理论添加操作
    @PostMapping("/addTheory/{id}")
    public String AddExpTheory(@PathVariable("id") int id,
                               String m_introduce,
                               String m_purpose,
                               String m_principle,
                               String m_content,
                               String m_edata_intro,
                               String m_step
    ) {
        ExpModel expModel = expModelService.findExpModelByID(id);
        expModel.setIntroduce(m_introduce);
        expModel.setPurpose(m_purpose);
        expModel.setPrinciple(m_principle);
        expModel.setM_content(m_content);
        expModel.setM_edata_intro(m_edata_intro);
        expModel.setM_step(m_step);
        expModelService.save(expModel);
        return "redirect:/expmodel/list";
    }

    //理论资料添加上传接口
    @PostMapping("/addTheoryFile/{id}")
    @ResponseBody
    public String AddExpTheory(@PathVariable("id") int id,
                               MultipartFile file,
                               HttpServletRequest request
    ) {
        String pathString = null;
        if (file != null) {
            String filename = file.getOriginalFilename();
            int unixSep = filename.lastIndexOf('/');
            int winSep = filename.lastIndexOf('\\');
            int pos = (winSep > unixSep ? winSep : unixSep);
            if (pos != -1) {
                filename = filename.substring(pos + 1);
            }
            pathString = fIleService.upload(request, file, filename);
        }
        return "{\"code\":0, \"msg\":\"success\", \"fileUrl\":\"" + pathString + "\"}";
    }

    //保存理论资料路径
    @PostMapping("/savePath/{mid}")
    @ResponseBody
    public String savePathn(@PathVariable("mid") int id, @RequestParam("path") String path, HttpServletRequest request) {
        ExpModel expModel = expModelService.findExpModelByID(id);
        String total = "";
        String[] url = path.split(",");
        for (String s : url) {
            total += request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ExperimentPlatform/ExpData/" + s + ",";
        }
        expModel.setM_edataurl(total);
        expModelService.save(expModel);
        return "";
    }


    //理论资料修改上传接口
    @PostMapping("/updateTheoryFile/{id}")
    @ResponseBody
    public String updateExpTheory(@PathVariable("id") int id,
                                  MultipartFile file,
                                  HttpServletRequest request
    ) {
        ExpModel expModel = expModelService.findExpModelByID(id);
        expModel.setM_edataurl("");
        String pathString = null;
        if (file != null) {
            String filename = file.getOriginalFilename();
            int unixSep = filename.lastIndexOf('/');
            int winSep = filename.lastIndexOf('\\');
            int pos = (winSep > unixSep ? winSep : unixSep);
            if (pos != -1) {
                filename = filename.substring(pos + 1);
            }
            pathString = fIleService.upload(request, file);
        }
        return "{\"code\":0, \"msg\":\"success\", \"fileUrl\":\"" + pathString + "\"}";
    }

    //进入理论更新
    @GetMapping("/updateExpTheory/{id}")
    public String toUpdateExpTheory(@PathVariable("id") int id, Model model) {
        ExpModel expModel = expModelService.findExpModelByID(id);
        model.addAttribute("preExpModel", expModel);
        if (expModel.getM_edataurl() != null) {
            String[] path = expModel.getM_edataurl().split(",");
            model.addAttribute("path", path);
        }
        return "shiyan/changeTheory";
    }

    //执行理论更新操作
    @PostMapping("/updateExpTheory/{id}")
    public String updateExpTheory(@PathVariable("id") int id,
                                  String m_introduce,
                                  String m_purpose,
                                  String m_principle,
                                  String m_content,
                                  String m_edata_intro,
                                  String m_step
    ) {

        ExpModel expModel = expModelService.findExpModelByID(id);
        expModel.setIntroduce(m_introduce);
        expModel.setPurpose(m_purpose);
        expModel.setPrinciple(m_principle);
        expModel.setM_content(m_content);
        expModel.setM_edata_intro(m_edata_intro);
        expModel.setM_step(m_step);
        expModelService.save(expModel);
        return "redirect:/expmodel/list";
    }

    //搜索模块
    @GetMapping("/viewExpModel")
    public String viewModel(@RequestParam("m_name") String m_name, Model model) {
        List<ExpModel> list = expModelService.findExpModelsBym_name(m_name);
        if (!list.isEmpty() && list != null) {
            model.addAttribute("list", list);
            return "shiyan/viewExpModel";
        }

        return "redirect:/expmodel/list";
    }


    //实验大厅所有模块信息
    @GetMapping("/alltestModel")
    public String alltest(Model model, @RequestParam(value = "pageNum", required = true, defaultValue = "0") int pageNum, HttpSession session) throws ParseException {
        int arrangeId = 0;
        //对通过SESSION来获取安排ID进行判断
        try {
            arrangeId = (int) session.getAttribute("arrageId_sctudemo");
            if (ShiroKit.isEmpty(arrangeId) || arrangeId <= 0) {
                return "redirect:/choose/course/list";
            }
        } catch (Exception e) {
            return "redirect:/choose/course/list";
        }

        ArrangeClass arrangeClass = arrangeClassService.findById(arrangeId);
        Page<ExpModel> a = expModelService.findOneCourseModelList2(arrangeClass.getCourseId(), pageNum, 6);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("list", a);
        session.setAttribute("modulePageNum", pageNum);
        session.setAttribute("isAllModule", true);
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        Docker docker = dockerService.findDockerByStu_id(student.getId());
        long nowDate = new Date().getTime();
        String flag = "1314-06-21 00:00:00";
        if (docker != null) {
            if (simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                model.addAttribute("docker", docker);
                return "home_shiyan/all-test";
            } else if (!simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                if (nowDate > docker.getDc_start_datetime().getTime()) {
                    model.addAttribute("docker", docker);
                    return "home_shiyan/all-test";
                }
            } else if (simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && !simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                if (nowDate < docker.getDc_end_datetime().getTime()) {
                    model.addAttribute("docker", docker);
                    return "home_shiyan/all-test";
                }
            } else if (!simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && !simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                if (docker.getDc_start_datetime().getTime() < nowDate && nowDate < docker.getDc_end_datetime().getTime()) {
                    model.addAttribute("docker", docker);
                    return "home_shiyan/all-test";
                }
            }
        }
        model.addAttribute("docker", null);
        return "home_shiyan/all-test";
    }

    //实验大厅考核模块
    @GetMapping("/kaoheModel")
    public String kaoModelById(Model model, @RequestParam(value = "pageNum", required = true, defaultValue = "0") int pageNum, HttpSession session) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        int arrangeId = 0;
        //对通过SESSION来获取安排ID进行判断
        try {
            arrangeId = (int) session.getAttribute("arrageId_sctudemo");
            if (ShiroKit.isEmpty(arrangeId) || arrangeId <= 0) {
                return "redirect:/choose/course/list";
            }
        } catch (Exception e) {
            return "redirect:/choose/course/list";
        }


//        Page<KaoHeModelStuDTO> kaohe = kaoheModelService.findKaoheModelStuDto(student.getId(), pageNum);
        Page<KaoHeModelStuDTO> kaohe = kaoheModelService.findKaoheModelStuDto(student.getId(), pageNum, arrangeId);
        model.addAttribute("k", kaohe);
        session.setAttribute("modulePageNum", pageNum);
        session.setAttribute("isAllModule", false);
        Docker docker = dockerService.findDockerByStu_id(student.getId());
        long nowDate = new Date().getTime();
        String flag = "1314-06-21 00:00:00";
        if (docker != null) {
            if (simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                model.addAttribute("docker", docker);
                return "home_shiyan/index";
            } else if (!simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                if (nowDate > docker.getDc_start_datetime().getTime()) {
                    model.addAttribute("docker", docker);
                    return "home_shiyan/index";
                }
            } else if (simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && !simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                if (nowDate < docker.getDc_end_datetime().getTime()) {
                    model.addAttribute("docker", docker);
                    return "home_shiyan/index";
                }
            } else if (!simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && !simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                if (docker.getDc_start_datetime().getTime() < nowDate && nowDate < docker.getDc_end_datetime().getTime()) {
                    model.addAttribute("docker", docker);
                    return "home_shiyan/index";
                }
            }
        }
        model.addAttribute("docker", null);
        return "home_shiyan/index";
    }

    //进入理论学习页面
    @GetMapping("/theoryStudy/{id}")
    public String theoryStudey(@PathVariable("id") int id, Model model) {
        ExpModel expModel = expModelService.findExpModelByID(id);
        model.addAttribute("exp", expModel);
        String path = expModel.getM_edataurl();
        if (path != null) {
            String[] paths = path.split(",");
            model.addAttribute("path", paths);
        }
        return "home_shiyan/study_update";
    }

    //模块测试和实验报告汇总页面
    @GetMapping("/moduleList")
    public String moduleList(Model model, HttpSession session,
                             @RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum) {
//        model.addAttribute("page1",expModelService.findModelList(pageNum));
        session.removeAttribute("msg2020612");
        User user = (User) session.getAttribute("teacher");
        logger.debug("moduleList输出老师账号——————————" + user);
        List<CourseInfo> courseInfoList = courseInfoService.getclassByCharge(user.getId());
        model.addAttribute("courseInfoList", courseInfoList);
//        model.addAttribute("page1", expModelService.findAllByTeacher(pageNum, user.getId()));
        boolean choose = false;
        model.addAttribute("Choose", choose);
        return "shiyan/lookTestAndReport";
    }

    @GetMapping("/moduleList/{courseId}")
    public String chooseCourse(Model model, HttpSession session,
                               @PathVariable("courseId") int courseId,
                               @RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum) {
        boolean choose = true;
        if (courseId == -1) {
            choose = false;
            model.addAttribute("Choose", choose);
//            model.addAttribute("selected1", "/report/allModule");
            return "redirect:/expmodel/moduleList";
        }

        User user = (User) session.getAttribute("teacher");
        logger.debug("chooseCourse输出老师账号——————————" + user);
        List<CourseInfo> courseInfoList = courseInfoService.getclassByCharge(user.getId());
        model.addAttribute("courseInfoList", courseInfoList);

        //绑定courseId，以便下拉框选中筛选的课程
        model.addAttribute("selected", courseId);
        //判断是否选择了课程
        model.addAttribute("Choose", choose);
        logger.debug("课程信息为:：" + courseInfoService.findById(courseId).getCourseName());
        Page<ExpModel> expModelPage = expModelService.findOneCourseModelList(courseId, pageNum);
        model.addAttribute("page1", expModelPage);
        logger.debug("该课程模块信息:" + expModelPage.getContent());
        return "shiyan/lookTestAndReport";
    }

    //精准返回进入模块测试或填写报告，或理论学习模块所在的页面
    @GetMapping("/moduleDispathcher")
    public String moduleDispathcher(HttpSession session, RedirectAttributes redirectAttributes) {

        int pageNum = 0;
        boolean flag = true;
        try {
            pageNum = (int) session.getAttribute("modulePageNum");
        } catch (Exception e) {
            pageNum = 0;
        }
        try {
            flag = (boolean) session.getAttribute("isAllModule");
        } catch (Exception e) {

        }


        redirectAttributes.addAttribute("pageNum", pageNum);
        if (flag == true) {
            return "redirect:/expmodel/alltestModel";
        } else {
            return "redirect:/expmodel/kaoheModel";
        }
    }


    //首页跳转过来的模块
    @GetMapping("/home_exp/{id}")
    public String homeExp(@PathVariable("id") int id, HttpSession session, Model model) throws ParseException {
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Docker docker = dockerService.findDockerByStu_id(student.getId());
        long nowDate = new Date().getTime();
        String flag = "1314-06-21 00:00:00";
        if (student.getClassId() != 0) {
            ClassModel classModel = clazzService.findById(student.getClassId());
            if (classModel.getClassIscurrent() == false) {
                //具备考核资格并且为当期
                if (kaoheModelService.findKaoheModelByMid(id) != null) {
                    //考核模块
                    KaoHeModelStuDTO kaoHeModelStuDTO = kaoheModelService.findKaoHeModelStuDTOByStuId(student.getId(), id);
                    model.addAttribute("k", kaoHeModelStuDTO);
                    if (docker != null) {
                        if (simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                            model.addAttribute("docker", docker);
                            return "home_shiyan/kaohe_copy";
                        } else if (!simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                            if (nowDate > docker.getDc_start_datetime().getTime()) {
                                model.addAttribute("docker", docker);
                                return "home_shiyan/kaohe_copy";
                            }
                        } else if (simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && !simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                            if (nowDate < docker.getDc_end_datetime().getTime()) {
                                model.addAttribute("docker", docker);
                                return "home_shiyan/kaohe_copy";
                            }
                        } else if (!simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && !simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                            if (docker.getDc_start_datetime().getTime() < nowDate && nowDate < docker.getDc_end_datetime().getTime()) {
                                model.addAttribute("docker", docker);
                                return "home_shiyan/kaohe_copy";
                            }
                        }
                    }
                    model.addAttribute("docker", null);
                    return "home_shiyan/kaohe_copy";
                }
            }
        }
        //不具备考核资格
        ExpModel expModel = expModelService.findExpModelByID(id);
        model.addAttribute("exp", expModel);
        if (docker != null) {
            if (simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                model.addAttribute("docker", docker);
                return "home_shiyan/all-test_copy";
            } else if (!simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                if (nowDate > docker.getDc_start_datetime().getTime()) {
                    model.addAttribute("docker", docker);
                    return "home_shiyan/all-test_copy";
                }
            } else if (simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && !simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                if (nowDate < docker.getDc_end_datetime().getTime()) {
                    model.addAttribute("docker", docker);
                    return "home_shiyan/all-test_copy";
                }
            } else if (!simpleDateFormat.format(docker.getDc_start_datetime()).equals(flag) && !simpleDateFormat.format(docker.getDc_end_datetime()).equals(flag)) {
                if (docker.getDc_start_datetime().getTime() < nowDate && nowDate < docker.getDc_end_datetime().getTime()) {
                    model.addAttribute("docker", docker);
                    return "home_shiyan/all-test_copy";
                }
            }
        }
        model.addAttribute("docker", null);
        return "home_shiyan/all-test_copy";

    }

    //继续学习
    @GetMapping("/contiuneStudy/{id}")
    public String contiunrStudy(@PathVariable("id") int id, HttpSession session) {
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        if (student.getClassId() != 0) {
            ClassModel classModel = clazzService.findById(student.getClassId());
            if (classModel.getClassIscurrent() == false) {
                if (kaoheModelService.findKaoheModelByMid(id) != null) {
                    return "redirect:/expmodel/kaoheModel";
                }
            }
        }
        return "redirect:/expmodel/alltestModel";
    }

    //中转站
    @GetMapping("/homeExpDispatcher/{id}")
    public String homeExpDispatcher(@PathVariable("id") int id, Model model, HttpSession session) {

        Student student = (Student) session.getAttribute("student");
        //暂时做了修改，如果没有登录，跳转到登录页
        if (student == null) {
//            return "home_page/login";
            return "redirect:/405";
        }

        model.addAttribute("disMid", id);
        return "kuangjia/shiyan";
    }


    /**
     * 通过课程ID查询进度
     *
     * @param courseId
     * @return
     */
    @GetMapping("/findClassByCourse/{id}")
    @ResponseBody
    public String findClassByCourse(@PathVariable("id") int courseId) {
        logger.debug("接口信息: + /expmodel/findClassByCourse/" + courseId);
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        List<ClassModel> classModels = courseInfoService.getclass_by_arrangecourseid(user.getId(), courseId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", classModels);
        logger.debug("查询进度信息: " + jsonObject);
        return String.valueOf(jsonObject);
    }


    /**
     * @param id
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/kaoheProgressQuery/{id}")
    public String kaoheProgressQuery(@PathVariable("id") int id, @RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum, Model model) {
        model.addAttribute("mid", id);
        logger.debug("接口信息: + /expmodel/kaoheProgressQuery/" + id);
        int course_id = (int) SecurityUtils.getSubject().getSession().getAttribute("proGressCourse_id");
        int class_id = (int) SecurityUtils.getSubject().getSession().getAttribute("class_id");
        Page<KaoheModuleProgressDTO> progressDTO = expModelService.findExpModels(course_id, class_id, id, pageNum);
        model.addAttribute("pro", progressDTO);
        int totalStuNum = clazzService.findStudentNumByClassId(class_id);
        model.addAttribute("totalStuNum", totalStuNum);
        model.addAttribute("testStateFalseNum", clazzService.findStuMTestByClassId(class_id, id));
        model.addAttribute("reportStateFalseNum", clazzService.findStuMReportStateByClassId(class_id, id));
        return "kaohe/progress_management";
    }


    //考核模块进度查询
    @GetMapping("/kaoheProgress")
    public String kaoheProgress(@RequestParam(value = "course", required = true, defaultValue = "0") int courseId,
                                @RequestParam(value = "class", required = true, defaultValue = "0") int classId,
                                @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                Model model
    ) {
        logger.debug("接口信息: + /expmodel/kaoheProgress");
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("登陆老师信息:" + user);
        List<CourseInfo> courseInfoList = courseInfoService.getclass_by_arrangeteacher(user.getId());
        logger.debug("课程信息:" + courseInfoList);
        model.addAttribute("courseList", courseInfoList);
        logger.debug("查询课程ID信息:" + courseId + "查询班级ID信息:" + classId);
        if (courseId == 0 && classId == 0) {
            return "kaohe/progress";
        } else if (courseId == 0 && classId != 0) {
            return "kaohe/progress";
        } else if (courseId != 0 && classId == 0) {
            return "kaohe/progress";
        }
        model.addAttribute("courseId", courseId);
        model.addAttribute("classId", classId);
        SecurityUtils.getSubject().getSession().setAttribute("proGressCourse_id", courseId);
        SecurityUtils.getSubject().getSession().setAttribute("class_id", classId);
        int mTestFalseNum = 0;
        int mReportFalseNum = 0;
        List<KaoheProgressMainDTO> list = new ArrayList<>();
        KaoheProgressMainDTO kaoheProgressMainDTO = null;
        Page<ExpModel> page = expModelService.findKaoheProgressMainByCourseId(courseId, pageNum);
        logger.debug("page进度信息:" + page.getContent());
        int totalNum = clazzService.findStudentNumByClassId(classId);
        for (ExpModel e : page) {
            mTestFalseNum = kaoHeModelScoreService.findmTestFalseByClassIdAndMid(classId, e.getM_id());
            mReportFalseNum = kaoHeModelScoreService.findmReportFalseByClassIdAndMid(classId, e.getM_id());
            kaoheProgressMainDTO = new KaoheProgressMainDTO(e.getM_id(), e.getM_name(), e.getImageurl(), totalNum, mTestFalseNum, mReportFalseNum);
            list.add(kaoheProgressMainDTO);
        }
        model.addAttribute("list", list);
        logger.debug("进度信息:" + user);
        return "kaohe/progress";
    }


    /**
     * Excel导入题目
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @PostMapping("/ExcelInputExpModel")
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        logger.debug("开始导入-------");
        //easyPOI工具
        ImportParams params = new ImportParams();
        //设置Excel中表头占用行数
        params.setHeadRows(1);
        //设置Excel中大标题占用行数,此模板未使用标题
        // params.setTitleRows(0);
        //获得Excel中的所有数据,存放在列表
        List<CourseAndModelImport> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),
                CourseAndModelImport.class, params);

        //遍历行
        for (CourseAndModelImport oneQ : result) {

            List<CourseInfo> courseList = courseInfoService.findAllByCourseCode(oneQ.getCourseCode());

            int courseId = 0;
            if (courseList.size() > 0) {
                logger.debug("此课程已经存在");
                courseId = courseList.get(0).getId();
            } else {
                logger.debug("新建课程");
                CourseInfo courseInfo = new CourseInfo();
                courseInfo.setCourseName(oneQ.getCourseName());
                courseInfo.setCourseCode(oneQ.getCourseCode());
                courseInfo.setCourseIntruduce("由Excel导入,尚未填写介绍");
                courseInfo.setTeacherId(0);
                courseInfoService.add(courseInfo);
                courseId = courseInfo.getId();
            }

            ExpModel expModel = new ExpModel();
            expModel.setCourseId(courseId);
            expModel.setM_name(oneQ.getmName());
            expModel.setM_manager(oneQ.getmManager());
            expModel.setM_type(oneQ.getmType());
            expModel.setClasshour(Integer.parseInt(oneQ.getClassHour()));
            expModel.setIntroduce(oneQ.getIntroduce());
            expModel.setPurpose(oneQ.getPurpose());
            expModel.setPrinciple(oneQ.getPrinciple());
            expModel.setM_content(oneQ.getmContent());
            expModel.setM_edata_intro(oneQ.getmDataIntro());
            expModel.setM_step(oneQ.getmType());
            expModel.setM_inurl(oneQ.getmInurl());
            expModel.setImageurl("none");
            expModel.setNeedKaohe(false);
            expModel.setReport_type(true);

            //保存到数据库
            expModelService.save(expModel);
        }
        logger.debug("导入成功-------" + result);
        return "redirect:/expmodel/list";
    }

    /**
     * 提供模板下载
     * @param response
     */
    @RequestMapping("/downloadTemplate")
    private void downloadFile(HttpServletResponse response) {

        String downloadFilePath = templatePath;//被下载的文件在服务器中的路径,
        String fileName = "CourseAndModelImportTemplate.xlsx";//被下载文件的名称

        File file = new File(downloadFilePath+fileName);
        response.addHeader("Content-Length", file.length()+"");
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开            
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                logger.info("下载成功");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            logger.info("下载失败");
        }
    }
}
package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeInfoDTO;
import com.coolwen.experimentplatformv2.model.DTO.StuTotalScoreCurrentDTO;
import com.coolwen.experimentplatformv2.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 总成绩管理
 * 列出所有的成绩
 *
 * @author 王雨来
 * @version 2020/5/13 12:21
 */

@Controller
@RequestMapping(value = "/totalscore")
public class TotalscoreCurrentController {

    protected static final Logger logger = LoggerFactory.getLogger(TotalscoreCurrentController.class);
    @Autowired
    public TotalScoreCurrentService totalScoreCurrentService;

    @Autowired
    public StudentService studentService;

    @Autowired
    public KaoheModelService kaoheModelService;

    @Autowired
    public ClazzService clazzService;

    @Autowired
    public ExpModelService expModelService;

    @Autowired
    public KaoHeModelScoreService kaoHeModelScoreService;

    @Autowired
    public StudentService studentservice;

    @Autowired
    private ArrangeClassService arrangeClassService;//课程安排表
    @Autowired
    public TotalScorePassService totalScorePassService;
    @Autowired
    public ModuleTestAnswerStuService moduleTestAnswerStuService;
    @Autowired
    public ReportAnswerService reportAnswerService;

    @Autowired
    public ScoreUpdateService scoreUpdateService;

    @Autowired
    public CourseInfoService courseInfoService;

    @Autowired
    public UserService userService;
//    @GetMapping("/test")
//    public String hello() {
//        return "AllModel";
//    }
//
//    /**
//     *
//     *
//     */
//    @GetMapping("/list")
//    public String expModelList(Model model,@RequestParam(value = "pageNum",defaultValue = "0",required = true) int pageNum){
//        model.addAttribute("page",totalScoreCurrentService.listTotalScoreCurrent());
//        return "totalscore/list";
//    }

//    @GetMapping(value = "/score_manage")
//    public String loadAllModel(Model model) {
////        Pageable pageable = PageRequest.of(pageNum, 5);
////        ExpModel expModel = expModelService.findModelList(pageNum);
////        Page<KaoheModel> page = kaoheModelService.findAll(pageable);
////        model.addAttribute("allKaohe", page);
////        List<Integer> check = kaoheModelService.inKaoheList();
////        model.addAttribute("allKaohe",expModelService.findModelList(pageNum));
////        model.addAttribute("checkList",check);
//
//        List<StudentTestScoreDTO> a = studentRepository.listStudentMTestAnswerDTO();
//        model.addAttribute("allInfo",a);
//        return "kaohe/score_manage";
//    }

    /**
     * 列出所有成绩
     *
     * @param model   传值
     * @param pageNum 分页
     * @return 页面
     */
    @GetMapping("/list")
    public String expModelList(Model model,
                               @RequestParam(required = true, defaultValue = "") String select_orderId,
                               @RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum) {
//        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
//        logger.debug("登陆用户信息:" + user);
//        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(user.getId());
        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(1);
        model.addAttribute("arrangeInfoDTOs", arrangeInfoDTOs);
        model.addAttribute("arrageId", -1);
        boolean choose = false;
//        //从数据库得到所有的总成绩
//        Page<StuTotalScoreCurrentDTO> totalScore = null;
//        try {
//            totalScore = studentService.listStuTotalScoreCurrentDTO(pageNum, select_orderId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        model.addAttribute("selectOrderId", select_orderId);
//        //获得所有考核模块的列表
//        List<KaoheModel> toGetBaiFenBi = kaoheModelService.findAll();
//
//        List<ClassModel> classList = clazzService.findCurrentClass();
//        model.addAttribute("classList", classList);
//
//        //初始化 最后权重
//        float kaoheBaifenbi = 0;
//        float testBaifenbi = 0;
//        if (toGetBaiFenBi.size() > 0) {
//            kaoheBaifenbi = toGetBaiFenBi.get(0).getKaohe_baifenbi();
//            testBaifenbi = toGetBaiFenBi.get(0).getTest_baifenbi();
//        }
//        model.addAttribute("pageTotalScore", totalScore);
//        model.addAttribute("kaoheBaifenbi", kaoheBaifenbi);
//        model.addAttribute("testBaifenbi", testBaifenbi);
//        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + totalScore);
        return "kaohe/all_score";
    }

    @GetMapping("/{classId}/list")
    public String getTotalScoreCirrentByGroupId(Model model,
                                                @PathVariable int classId,
                                                @RequestParam(required = true, defaultValue = "") String select_orderId,
                                                @RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum) {
        //从数据库得到所有的总成绩
        Page<StuTotalScoreCurrentDTO> totalScore = studentService.listStuTotalScoreCurrentDTOByClassId(pageNum, select_orderId, classId);
        //获得所有考核模块的列表
        List<KaoheModel> toGetBaiFenBi = kaoheModelService.findAll();

        List<ClassModel> classList = clazzService.findCurrentClass();
        model.addAttribute("classList", classList);

        //初始化 最后权重
        float kaoheBaifenbi = 0;
        float testBaifenbi = 0;
        if (toGetBaiFenBi.size() > 0) {
            kaoheBaifenbi = toGetBaiFenBi.get(0).getKaohe_baifenbi();
            testBaifenbi = toGetBaiFenBi.get(0).getTest_baifenbi();
        }
        model.addAttribute("pageTotalScore", totalScore);
        model.addAttribute("kaoheBaifenbi", kaoheBaifenbi);
        model.addAttribute("testBaifenbi", testBaifenbi);
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + totalScore);
        return "kaohe/all_score";
    }

    @PostMapping(value = "/GreatestWeight")
    public String GreatestWeight(@RequestParam(required = true, defaultValue = "0") float kaoheBaifenbi,
                                 @RequestParam(required = true, defaultValue = "0") float testBaifenbi, @RequestParam(required = true) int arrageId) {

        kaoheModelService.updateAllGreatestWeight(kaoheBaifenbi, testBaifenbi, arrageId);
//        kaoheModelService.updateAllGreatestWeight(kaoheBaifenbi, testBaifenbi);
        logger.debug("设置考核模块所有:" + kaoheBaifenbi + "++++++++++++++" + testBaifenbi + "arrageId" + arrageId);
        //todo 更新学生成绩
        scoreUpdateService.allStudentScoreUpdate2(arrageId);

//        List<TotalScoreCurrent> totalScoreCurrents = totalScoreCurrentService.findAll();
//        for (TotalScoreCurrent i : totalScoreCurrents) {
//            logger.debug("更新前的成绩:" + i);
//            i.setTotalScore(i.getmTotalScore() * kaoheBaifenbi + i.getTestScore() * testBaifenbi);
//            totalScoreCurrentService.update(i);
//            logger.debug("更新后的成绩:" + i);
//        }
        return "redirect:/totalscore/report/" + arrageId;

    }


    @GetMapping(value = "/guhuaall/{arrageId}")
    public String guHuaAll(Model model, @PathVariable int arrageId) {

        ArrangeClass arrangeClass = arrangeClassService.findById(arrageId);
        logger.debug("安排信息为:" + arrangeClass);
        model.addAttribute("arrageId", arrangeClass.getId());
        //获取班级id
        int classId = arrangeClass.getClassId();
        int courseId = arrangeClass.getCourseId();
        ClassModel classModel = clazzService.findById(classId);
        CourseInfo courseInfo = courseInfoService.findById(courseId);
        User user = userService.load(arrangeClass.getTeacherId());
//        int id = i.getClassId();

        TotalScorePass totalScorePass = null;
        //初始化固化成绩信息
        String kaoheModuleName = "";
        String kaohe_mtestscore = "";
        String kaohe_mreportscore = "";
        String kaohe_mtestscore_baifengbi = "";
        String kaohe_mreportscore_baifengbi = "";
        String kaohe_mscale = "";

        float test_baifenbi = 0;
        float kaohe_baifenbi = 0;
//        List<KaoheModel> kaoheModelList = kaoheModelService.findAll();
        List<KaoheModel> kaoheModelList = kaoheModelService.findAllByArrageId(arrageId);
        logger.debug("考核模块信息为:" + kaoheModelList);
        //获得考核项目名字
        for (KaoheModel k : kaoheModelList) {
            ExpModel expModel = expModelService.findExpModelsByKaoheMid(k.getM_id());
            kaoheModuleName += expModel.getM_name() + ";";
            test_baifenbi = k.getTest_baifenbi();
            kaohe_baifenbi = k.getKaohe_baifenbi();
        }

        //拼接之后，如果有数据要去除最后一个分号，
        if (kaoheModuleName.length() > 0) {
            kaoheModuleName = kaoheModuleName.substring(0, kaoheModuleName.length() - 1);
        }
        List<Student> studentList = studentservice.findStudentByClassId(classId);
        for (Student student : studentList) {
            //拼接之前要初始化
            kaohe_mtestscore = "";
            kaohe_mreportscore = "";
            kaohe_mtestscore_baifengbi = "";
            kaohe_mreportscore_baifengbi = "";
            kaohe_mscale = "";

            for (KaoheModel kaoheModel : kaoheModelList) {
                //获取该班级下学生每个考核模块信息
                KaoHeModelScore kaoHeModelScore = kaoHeModelScoreService.findKaoHeModelScoreByStuIdAndId(student.getId(), kaoheModel.getId());
                //进行成绩固化临时存储
                kaohe_mtestscore += kaoHeModelScore.getmTestScore() + ";";
                kaohe_mreportscore += kaoHeModelScore.getmReportScore() + ";";
                kaohe_mtestscore_baifengbi += kaoheModel.getM_test_baifenbi() + ";";
                kaohe_mreportscore_baifengbi += kaoheModel.getM_report_baifenbi() + ";";
                kaohe_mscale += kaoheModel.getM_scale() + ";";
            }

            //拼接之后，如果有数据要去除最后一个分号
            if (kaohe_mtestscore.length() > 0) {
                kaohe_mtestscore = kaohe_mtestscore.substring(0, kaohe_mtestscore.length() - 1);
                kaohe_mreportscore = kaohe_mreportscore.substring(0, kaohe_mreportscore.length() - 1);
                kaohe_mtestscore_baifengbi = kaohe_mtestscore_baifengbi.substring(0, kaohe_mtestscore_baifengbi.length() - 1);
                kaohe_mreportscore_baifengbi = kaohe_mreportscore_baifengbi.substring(0, kaohe_mreportscore_baifengbi.length() - 1);
                kaohe_mscale = kaohe_mscale.substring(0, kaohe_mscale.length() - 1);
            }

            TotalScoreCurrent totalScoreCurrent = totalScoreCurrentService.findTotalScoreCurrentByStuId(student.getId());
            //进行成绩固化操作
            totalScorePass = new TotalScorePass();
            totalScorePass.setStuId(student.getId());
            totalScorePass.setKaoheName(String.valueOf(kaoheModelList.size()));
            totalScorePass.setKaoheName(kaoheModuleName);
            totalScorePass.setKaoheMtestscore(kaohe_mtestscore);
            totalScorePass.setKaoheMreportscore(kaohe_mreportscore);
            totalScorePass.setKaoheMtestscoreBaifengbi(kaohe_mtestscore_baifengbi);
            totalScorePass.setKaoheMreportscoreBaifengbi(kaohe_mreportscore_baifengbi);
            totalScorePass.setKaoheMscale(kaohe_mscale);
            totalScorePass.setmTotalScore(totalScoreCurrent.getmTotalScore());
            totalScorePass.setTestScore(totalScoreCurrent.getTestScore());
            totalScorePass.setTestBaifenbi(test_baifenbi);
            totalScorePass.setKaoheBaifenbi(kaohe_baifenbi);
            totalScorePass.setTotalScore(totalScoreCurrent.getTotalScore());
            totalScorePass.setFinalDatetime(new Date());

            totalScorePass.setCourseName(courseInfo.getCourseName());
            totalScorePass.setTeacherName(user.getNickname());
            totalScorePass.setTeacherGongHao(user.getGonghao());
            totalScorePass.setClassName(classModel.getClassName());
            totalScorePassService.save(totalScorePass);
            logger.debug("固化信息为:" + totalScorePass);
            //删除学生模块回答，报告回答，考核成绩表，以及当期总评成绩表
//            moduleTestAnswerStuService.deleteModuleTestAnswerStuByStuId(student.getId());
//            reportAnswerService.deleteReportAnswerByStuId(student.getId());
//            kaoHeModelScoreService.deleteKaoheModuleScoreByStuId(student.getId());
//            totalScoreCurrentService.deleteTotalScoreCurrentByStuId(student.getId());
        }
//        }


        return "redirect:/passTotalscore/list";
    }

    @GetMapping(value = "/report/{arrangeId}")
    public String loadOneCourseModel(Model model, @PathVariable int arrangeId, @RequestParam(required = true, defaultValue = "") String select_orderId,
                                     @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {
//        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
//        logger.debug("登陆用户信息:" + user);
        //所有的下拉列表数据
//        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(user.getId());
        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(1);
        model.addAttribute("arrangeInfoDTOs", arrangeInfoDTOs);

        //当前选择的安排表Id,用于判断按钮跳转连接,以及下拉列表回显

        //判断是否选择了安排，arrangeId = -1表示没有选择
        boolean choose = true;
        if (arrangeId == -1) {
            choose = false;
            model.addAttribute("Choose", choose);
//            model.addAttribute("selected1", "/report/allModule");
            return "redirect:/totalscore/list";
        }
        model.addAttribute("selected", arrangeId);
        model.addAttribute("Choose", choose);
        model.addAttribute("arrageId", arrangeId);
        //本安排的实验模块
        ArrangeClass arrangeClass = arrangeClassService.findById(arrangeId);
        logger.debug("安排信息为:" + arrangeClass);
        model.addAttribute("arrageId", arrangeClass.getId());
        int classId = arrangeClass.getClassId();
        int courseId = arrangeClass.getCourseId();
        model.addAttribute("selectOrderId", select_orderId);
        ClassModel classModel = clazzService.findById(classId);
        model.addAttribute("selectOrderId", select_orderId);
        //查询当期班级
//        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList", classModel);
        //        //从数据库得到所有的总成绩

        Page<StuTotalScoreCurrentDTO> totalScore = null;
        try {
//            totalScore = studentService.listStuTotalScoreCurrentDTO(pageNum, select_orderId);
            totalScore = studentService.listStuTotalScoreCurrentDTO(pageNum, select_orderId, arrangeId);
            logger.debug("成绩信息为:" + totalScore.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获得当前课程班级下所有考核模块的列表
//        List<KaoheModel> toGetBaiFenBi = kaoheModelService.findAll();
        List<KaoheModel> toGetBaiFenBi = kaoheModelService.findKaoheModelByArrangeId2(arrangeId);
        logger.debug("所有考核模块的列表信息为:" + toGetBaiFenBi);

        //初始化 最后权重
        float kaoheBaifenbi = 0;
        float testBaifenbi = 0;
        if (toGetBaiFenBi.size() > 0) {
            kaoheBaifenbi = toGetBaiFenBi.get(0).getKaohe_baifenbi();
            testBaifenbi = toGetBaiFenBi.get(0).getTest_baifenbi();
        }
        model.addAttribute("pageTotalScore", totalScore);
        model.addAttribute("kaoheBaifenbi", kaoheBaifenbi);
        model.addAttribute("testBaifenbi", testBaifenbi);
        return "kaohe/all_score";
    }
}

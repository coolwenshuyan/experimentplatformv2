package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.KaoheModelRepository;
import com.coolwen.experimentplatformv2.dao.StudentRepository;
import com.coolwen.experimentplatformv2.model.DTO.PScoreDto;
import com.coolwen.experimentplatformv2.model.KaoHeModelScore;
import com.coolwen.experimentplatformv2.model.ReportAnswer;
import com.coolwen.experimentplatformv2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//import com.coolwen.experimentplatformv2.model.StudentTestScoreDTO;

/**
 * 老师评分
 * 老师对学生提交的报告进行评分
 * @author 王雨来
 * @version 2020/5/13 12:21
 */
@Controller
//老师评分
@RequestMapping(value = "/TreportGrade")
public class TreportGradeController {

    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    private KaoheModelRepository kaoheModelRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ReportAnswerService reportAnswerService;

    @Autowired
    private ScoreUpdateService scoreUpdateService;

    @Autowired
    private KaoHeModelScoreService kaoHeModelScoreService;

    @Autowired
    private KaoheModelService kaoheModelService;

    @Autowired
    private TotalScoreCurrentService totalScoreCurrentService;

//    @GetMapping(value = "/add")
//    public String loadAllModel(Model model) {
//        List<StudentTestScoreDTO> page = studentRepository.listStudentMTestAnswerDTO();
//        model.addAttribute("TPageInfo",page);
//        return "kaohe/reportGrade_ma";
//    }
//
//    @GetMapping(value = "/list")
//    public String loadModel(Model model,@RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum) {
////        List<TreportGradeDto> page = studentRepository.ListStudentDto();
//        Page<Student> c = studentService.findAll(pageNum);
//        model.addAttribute("allStu",c);
//        List<StudentTestScoreDTO> a = studentRepository.listStudentMTestAnswerDTO();
//        long modleNum = kaoheModelRepository.count();
//        model.addAttribute("allInfo",a);
//        model.addAttribute("num",modleNum);
//        List<Integer> list = new ArrayList<Integer>();
//        model.addAttribute("TPageInfo",a);
//        return "kaohe/score_management ";
//    }stuId,mid

    /**
     * 获取学生报告
     * @param stuId 学生id
     * @param mid 模块id
     * @return 页面
     */
    @GetMapping(value = "/{stuId}/{mid}/giveMark" )
    public String GiveAmark(Model model,
                            @PathVariable("stuId") int stuId,
                            @PathVariable("mid")int mid
    ) {
        System.out.println(stuId+">>>>>>>>>>"+mid);
        List<PScoreDto> score = scoreService.listScorerDTOBystudentId(stuId,mid);
        System.out.println(score.size());

        model.addAttribute("zjy",score);
        System.out.println(">>>>>>>>>>>>>>>>>>"+score);
        return "kaohe/reportGrade_ma";
    }


    /**
     * 打分
     * @param request 获得学生报告
     * @param stuId 学生id
     * @param mid 模块id
     * @return 页面
     */
    @PostMapping(value = "/{stuId}/{mid}/giveMark" )
    public String giveamark(Model model, HttpServletRequest request,
                            @PathVariable("stuId") int stuId,
                            @PathVariable("mid")int mid

    ) {
        List<PScoreDto> score = scoreService.listScorerDTOBystudentId(stuId,mid);

        //存储教师评分
        for (PScoreDto pScoreDto1:score)
        {
            String value_t = request.getParameter(Integer.toString(pScoreDto1.getReportid()));
            ReportAnswer c = reportAnswerService.findByReportidAndStuID(pScoreDto1.getReportid(),stuId);
            Integer a = Integer.parseInt(value_t);
            c.setScore(a);
            reportAnswerService.updateOne(c);

        }
        //重新计算成绩
        scoreUpdateService.singleStudentScoreUpdate(stuId);

        KaoHeModelScore khs = kaoHeModelScoreService.findKaoheModelScoreByMid(mid ,stuId);
        khs.setmReportteacherstate(true);
        kaoHeModelScoreService.update(khs);

//        model.addAttribute("zjy",score);
//        System.out.println(">>>>>>>>>>>>>>>>>>"+score);
//        Enumeration em = request.getParameterNames();
//        List<String> zyy = new ArrayList<>();
//
//        while (em.hasMoreElements()) {
//        String name = (String) em.nextElement();
//        String value = request.getParameter(name);
//        System.out.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+value);
//        zyy.add(value);
//        }

//        //获得学生的报告
//        Enumeration em = request.getParameterNames();
//        //保存所有请求内容
//        List<String> zyy = new ArrayList<>();
//
//        //保存所有请求名
//        List<String> z = new ArrayList<>();
//
//        //获得所有请求名
//        while (em.hasMoreElements()) {
//            String name = (String) em.nextElement();
//            z.add(name);
//        }
//        //将所有请求名排序，并获取内容添加到
//        Collections.sort(z);
//        for(String a:z){
//            String value = request.getParameter(a);
//            zyy.add(value);
//        }
//
//        //添加老师给学生的评分，fs（当前学生报告模块总分）
//        float fs = 0;
//        for (int i = 0; i <zyy.size() ; i++) {
//            PScoreDto d= score.get(i);
//            ReportAnswer c = reportAnswerService.findByReportidAndStuID(d.getReportid(),stuId);
//            Integer a = Integer.parseInt(zyy.get(i));
//            fs+=a;
//            c.setScore(a);
//            reportAnswerService.updateOne(c);
//        }

//        scoreUpdateService.singleStudentScoreUpdate(stuId);
//        KaoHeModelScore khs = kaoHeModelScoreService.findKaoheModelScoreByMid(mid ,stuId);
//        khs.setmReportstate(true);
//        kaoHeModelScoreService.update(khs);

//        //获取当前考核模块信息
//        KaoheModel kh = kaoheModelService.findKaoheModelByMid(mid);
//        //获取当前学生考核模块分数信息
//        KaoHeModelScore khs = kaoHeModelScoreService.findKaoheModelScoreByMid(mid ,stuId);
//        System.out.println("dddddddddddd"+khs);
//        khs.setmReportScore(fs);
//        //计算模块总分
//        float ms = (fs*kh.getM_report_baifenbi()+khs.getmTestScore()*kh.getM_test_baifenbi())*khs.getmScale();
//        //更新前分数
//        float pkhsScore = khs.getmScore();
//        khs.setmScore(ms);
//        khs.setmReportstate(true);
//        //更新模块总分
//        kaoHeModelScoreService.update(khs);
//        //查询学生总成绩
//        TotalScoreCurrent tsc = totalScoreCurrentService.findTotalScoreCurrentByStuID(stuId);
//        //更新模块成绩
//        tsc.setmTotalScore(tsc.getmTotalScore()+ms-pkhsScore);
//        //更新学生总成绩
////        tsc.setTotalScore(tsc.getmTotalScore()*kh.getKaohe_baifenbi()+tsc.getTestScore()*kh.getTest_baifenbi());
////        System.out.println(tsc.getmTotalScore()*kh.getKaohe_baifenbi()+">>>"+tsc.getTestScore()*kh.getTest_baifenbi());
//        tsc.setTotalScore(tsc.getTotalScore()+ms-pkhsScore);
//        totalScoreCurrentService.update(tsc);

        return "redirect:/TreportGrade/"+stuId+'/'+mid+"/giveMark";
    }
}

package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.kit.ShiroKit;
import com.coolwen.experimentplatformv2.model.CollegeReport;
import com.coolwen.experimentplatformv2.model.DTO.CollegeReportStuExpDto;
import com.coolwen.experimentplatformv2.model.KaoHeModelScore;
import com.coolwen.experimentplatformv2.model.KaoheModel;
import com.coolwen.experimentplatformv2.model.Student;
import com.coolwen.experimentplatformv2.service.CollegeReportService;
import com.coolwen.experimentplatformv2.service.KaoHeModelScoreService;
import com.coolwen.experimentplatformv2.service.KaoheModelService;
import com.coolwen.experimentplatformv2.service.ScoreUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 朱治汶
 * @date 2020/6/13 23:35
 **/
@Controller
@RequestMapping(value = "/collegereport")
public class CollegeReportController {

    protected static final Logger logger = LoggerFactory.getLogger(CollegeReportController.class);
    @Autowired
    CollegeReportService collegeReportService;

    @Autowired
    KaoHeModelScoreService kaoHeModelScoreService;

    @Autowired
    ScoreUpdateService scoreUpdateService;

    @Autowired
    KaoheModelService kaoheModelService;

    /**
     * 进入填写实验目的页面
     *
     * @param mid   模块id号
     * @param model 返回报告信息
     * @return 进入实验报告目的页面
     */
    @GetMapping("/info/{mid}")
    public String info(@PathVariable int mid, Model model, HttpSession session) {
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        CollegeReport collegeReport = collegeReportService.findStuidAndMid(student.getId(), mid);
        //如果没有记录，则创建一条数据
        if (collegeReport == null) {
            CollegeReport collegeReport1 = new CollegeReport();
            collegeReport1.setStuid(student.getId());
            collegeReport1.setMid(mid);
            collegeReportService.addCollegeReport(collegeReport1);
        }
        //如果是考核模块，并教师已经评分，直接跳转到查看页面
        try {
            KaoHeModelScore khs = kaoHeModelScoreService.findKaoheModelScoreByMid(mid, student.getId());
            if (khs.ismReportteacherstate()) {
                return "redirect:/collegereport/allreport/" + mid;
            }
        } catch (Exception e) {
        }
        CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(student.getId(), mid);
        //如果教师已经评分，直接进入查看页面
//        if (collegeReportStuExpDto.getCrTcState()){
//            return "redirect:/collegereport/allreport/"+mid;
//        }
        model.addAttribute("collegeReport", collegeReportStuExpDto);
        return "shiyan_baogao/bg_top";
    }

    /**
     * 存储填写报告的信息
     *
     * @param mid           模块id号
     * @param collegeReport 存储前端返回的填写信息
     * @param model         存储实验报告信息
     * @return 返回到实验报告环境的填写页面
     */
    @PostMapping("/info/{mid}")
    public String info(@PathVariable int mid, HttpSession session, CollegeReport collegeReport, Model model) {
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //添加学生填写的报告
        logger.debug(collegeReport.toString());
        CollegeReport collegeReport1 = collegeReportService.findStuidAndMid(student.getId(), mid);
        collegeReport1.setCrDress(collegeReport.getCrDress());
        collegeReport1.setCrDate(collegeReport.getCrDate());
        collegeReport1.setCrTeacher(collegeReport.getCrTeacher());
        collegeReportService.addCollegeReport(collegeReport1);
        //查询到报告信息
        CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(student.getId(), mid);
        model.addAttribute("collegeReport", collegeReportStuExpDto);
        return "shiyan_baogao/bg_mudi";
    }

    @GetMapping("/purpose/{mid}")
    public String purpose(@PathVariable int mid, Model model, HttpSession session) {
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //查询到报告信息
        CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(student.getId(), mid);
        model.addAttribute("collegeReport", collegeReportStuExpDto);
        return "shiyan_baogao/bg_mudi";
    }

    @PostMapping("/purpose/{mid}")
    public String purpose1(@PathVariable int mid, HttpSession session, CollegeReport collegeReport, Model model) {
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //添加学生填写的报告
        CollegeReport collegeReport1 = collegeReportService.findStuidAndMid(student.getId(), mid);
        collegeReport1.setCrExpPurpose(collegeReport.getCrExpPurpose());
        collegeReportService.addCollegeReport(collegeReport1);
        //查询到报告信息
        CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(student.getId(), mid);
        model.addAttribute("collegeReport", collegeReportStuExpDto);
        return "shiyan_baogao/bg_huanjing";
    }

    @PostMapping("/purpose/{mid}/return")
    public String purpose2(@PathVariable int mid, CollegeReport collegeReport, HttpSession session, Model model) {
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //添加学生填写的报告
        CollegeReport collegeReport1 = collegeReportService.findStuidAndMid(student.getId(), mid);
        collegeReport1.setCrExpPurpose(collegeReport.getCrExpPurpose());
        collegeReportService.addCollegeReport(collegeReport1);
        return "redirect:/collegereport/info/" + mid;
    }

    @GetMapping("/env/{mid}")
    public String addenv(@PathVariable int mid, Model model, HttpSession session) {
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //查询到报告信息
        CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(student.getId(), mid);
        model.addAttribute("collegeReport", collegeReportStuExpDto);
        return "shiyan_baogao/bg_huanjing";
    }

    @PostMapping("/env/{mid}")
    public String addenv1(@PathVariable int mid, CollegeReport collegeReport, HttpSession session, Model model) {
        logger.debug("??????????????????????????????");
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //添加学生填写的报告
        CollegeReport collegeReport1 = collegeReportService.findStuidAndMid(student.getId(), mid);
        collegeReport1.setCrExpEvr(collegeReport.getCrExpEvr());
        collegeReportService.addCollegeReport(collegeReport1);
        //查询到报告信息
        CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(student.getId(), mid);
        model.addAttribute("collegeReport", collegeReportStuExpDto);
        return "shiyan_baogao/bg_neirong";
    }

    @PostMapping("/env/{mid}/return")
    public String addenv2(@PathVariable int mid, CollegeReport collegeReport, HttpSession session, Model model) {
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>return");
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //添加学生填写的报告
        CollegeReport collegeReport1 = collegeReportService.findStuidAndMid(student.getId(), mid);
        collegeReport1.setCrExpEvr(collegeReport.getCrExpEvr());
        collegeReportService.addCollegeReport(collegeReport1);
        return "redirect:/collegereport/purpose/" + mid;
    }

    @GetMapping("/content/{mid}")
    public String content(@PathVariable int mid, Model model, HttpSession session) {
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //查询到报告信息
        CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(student.getId(), mid);
        model.addAttribute("collegeReport", collegeReportStuExpDto);
        return "shiyan_baogao/bg_neirong";
    }

    @PostMapping("/content/{mid}")
    public String content1(@PathVariable int mid, CollegeReport collegeReport, HttpSession session, Model model) {
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //添加学生填写的报告
        CollegeReport collegeReport1 = collegeReportService.findStuidAndMid(student.getId(), mid);
        collegeReport1.setCrExpContent(collegeReport.getCrExpContent());
        collegeReportService.addCollegeReport(collegeReport1);
        //查询到报告信息
        CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(student.getId(), mid);
        model.addAttribute("collegeReport", collegeReportStuExpDto);
        return "shiyan_baogao/bg_xinde";
    }

    @PostMapping("/content/{mid}/return")
    public String content2(@PathVariable int mid, CollegeReport collegeReport, HttpSession session, Model model) {
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //添加学生填写的报告
        CollegeReport collegeReport1 = collegeReportService.findStuidAndMid(student.getId(), mid);
        collegeReport1.setCrExpContent(collegeReport.getCrExpContent());
        collegeReportService.addCollegeReport(collegeReport1);
        return "redirect:/collegereport/env/" + mid;
    }

    @GetMapping("/summary/{mid}")
    public String summary(@PathVariable int mid, Model model, HttpSession session) {
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //查询到报告信息
        CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(student.getId(), mid);
        model.addAttribute("collegeReport", collegeReportStuExpDto);
        return "shiyan_baogao/bg_xinde";
    }

    @PostMapping("/summary/{mid}")
    public String summary1(@PathVariable int mid, HttpSession session, CollegeReport collegeReport, Model model) {
        int arrangeId=0;
        //对通过SESSION来获取安排ID进行判断
        try {
            arrangeId = (int) session.getAttribute("arrageId_sctudemo");
            if(ShiroKit.isEmpty(arrangeId)||arrangeId<=0)
            {
                return "redirect:/choose/course/list";
            }
        }catch (Exception e)
        {
            return "redirect:/choose/course/list";
        }
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //添加学生填写的报告
        CollegeReport collegeReport1 = collegeReportService.findStuidAndMid(student.getId(), mid);
        collegeReport1.setCrExpSummary(collegeReport.getCrExpSummary());
        collegeReportService.addCollegeReport(collegeReport1);

        //如果是考核模块，改变学生填写报告教师评分状态为true
        List<KaoheModel> kaoheModels1 = kaoheModelService.findKaoHeModelByArrangeidAndMid(arrangeId,mid);
        if(kaoheModels1.size()>0) {
            KaoHeModelScore khs = kaoHeModelScoreService.findKaoheModelScoreByMid(kaoheModels1.get(0).getId(), student.getId());
            khs.setmReportstate(true);
            kaoHeModelScoreService.update(khs);
        }


//        //查询到报告信息
//        CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(student.getId(),mid);
//        model.addAttribute("collegeReport",collegeReportStuExpDto);
        return "redirect:/collegereport/allreport/" + mid;
    }

    @PostMapping("/summary/{mid}/return")
    public String summary2(@PathVariable int mid, CollegeReport collegeReport, HttpSession session, Model model) {
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //添加学生填写的报告
        CollegeReport collegeReport1 = collegeReportService.findStuidAndMid(student.getId(), mid);
        collegeReport1.setCrExpSummary(collegeReport.getCrExpSummary());
        collegeReportService.addCollegeReport(collegeReport1);
        return "redirect:/collegereport/content/" + mid;
    }

    @GetMapping("/allreport/{mid}")
    public String allreport(@PathVariable int mid, Model model, HttpSession session) {
        //获取学生的登录信息
//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) session.getAttribute("student");
        //查询到报告信息
        CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(student.getId(), mid);
        model.addAttribute("collegeReport", collegeReportStuExpDto);
        return "shiyan_baogao/bg_student";
    }

}

package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.filter.FileExcelUtil;
import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeInfoDTO;
import com.coolwen.experimentplatformv2.model.DTO.StuTotalScoreCurrentDTO;
import com.coolwen.experimentplatformv2.service.*;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 总成绩管理
 * 列出往期所有的成绩
 *
 * @author 王雨来
 * @version 2020/5/13 12:21
 */

@Controller
@RequestMapping(value = "/passTotalscore")
public class TotalscorePassController {

    protected static final Logger logger = LoggerFactory.getLogger(TotalscorePassController.class);
    @Autowired
    public TotalScoreCurrentService totalScoreCurrentService;

    @Autowired
    public TotalScorePassService totalScorePassService;

    @Autowired
    public StudentService studentService;

    @Autowired
    public KaoheModelService kaoheModelService;

    @Autowired
    public ClazzService clazzService;
    @Autowired
    private ArrangeClassService arrangeClassService;//课程安排表

    /**
     * 列出所有成绩
     *
     * @param model   传值
     * @param pageNum 分页
     * @return 页面
     */
/*    @GetMapping("/list")
    public String expModelList(Model model, @RequestParam(value = "pageNum",defaultValue = "0",required = true) int pageNum){
        Page<TotalScorePass> totalScorePasses = totalScorePassService.findAll(pageNum);
        for(TotalScorePass i:totalScorePasses){
            logger.debug(i);

        Student student = studentService.findStudentById(i.getStuId());
        ClassModel classModel = clazzService.findById(student.getId());

        List<OneModelScoreDTO> oneModelScoreDTOS;
//        PassTotalScoreDTO passTotalScoreDTO = new(student.getStuXuehao(),student.getStuName(),classModel.getClassName(),oneModelScoreDTOS,i.getmTotalScore(),oneModelScoreDTOS,i.get;);


        }
        return null;
    }*/
    @GetMapping("/list")
    public String expModelList(Model model,
                               @RequestParam(required = true, defaultValue = "") String select_orderId,
                               @RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum) {
//        //从数据库得到所有的总成绩
//
//        Page<StuTotalScoreCurrentDTO> totalScore = studentService.listStuTotalScoreCurrentDTOOfPass(pageNum, select_orderId);
//        model.addAttribute("selectOrderId", select_orderId);
//
//        //获得所有往期班级
//        List<ClassModel> classList = clazzService.findPassClass();
//        model.addAttribute("classList", classList);
//
//        model.addAttribute("pageTotalScore", totalScore);
////        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+totalScore);
        //        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
//        logger.debug("登陆用户信息:" + user);
//        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(user.getId());
        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(1);
        model.addAttribute("arrangeInfoDTOs", arrangeInfoDTOs);
        model.addAttribute("arrageId", -1);
        boolean choose = false;
        return "kaohe/all_score_pass";
    }

    @GetMapping("/{classId}/list")
    public String getTotalScoreCirrentByGroupId(Model model,
                                                @PathVariable int classId,
                                                @RequestParam(required = true, defaultValue = "") String select_orderId,
                                                @RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum) {
        //从数据库得到所有的总成绩
        Page<StuTotalScoreCurrentDTO> totalScore = studentService.listStuTotalScoreCurrentDTOOfPassByClassId(pageNum, select_orderId, classId);


        //获得所有往期班级
        List<ClassModel> classList = clazzService.findPassClass();
        model.addAttribute("classList", classList);
        model.addAttribute("classId", classId);

        model.addAttribute("pageTotalScore", totalScore);
//        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+totalScore);
        return "kaohe/all_score_pass";
    }


    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) {
        List<StuTotalScoreCurrentDTO> totalScore = studentService.listAllStuTotalScoreCurrentDTOOfPass();

//        List<Student> b = studentRepository.findAll();
        // 设置响应输出的头类型(设置响应类型)
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称(设置下载文件的默认名称)
        response.setHeader("Content-Disposition", "attachment;filename=address.xls");
        //导出操作
//        try {
//            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("客户地址","1"),User.class,addresses);
//            workbook.write(response.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        FileExcelUtil.exportExcel(totalScore, "往期成绩汇总", "往期成绩", StuTotalScoreCurrentDTO.class, "往期成绩表.xls", response);
    }

    @GetMapping(value = "/report/{arrangeId}")
    public String loadPassScoreByArrageId(Model model, @PathVariable int arrangeId, @RequestParam(required = true, defaultValue = "") String select_orderId,
                                          @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("登陆用户信息:" + user);
//        所有的下拉列表数据
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


        Page<StuTotalScoreCurrentDTO> totalScore = studentService.listStuTotalScoreCurrentDTOOfPass(pageNum, select_orderId);
        model.addAttribute("selectOrderId", select_orderId);


        model.addAttribute("pageTotalScore", totalScore);

        return "kaohe/all_score_pass";
    }
}

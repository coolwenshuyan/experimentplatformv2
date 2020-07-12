package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.KaoheModelRepository;
import com.coolwen.experimentplatformv2.dao.StudentRepository;
import com.coolwen.experimentplatformv2.filter.FileExcelUtil;
import com.coolwen.experimentplatformv2.model.ClassModel;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeInfoDTO;
import com.coolwen.experimentplatformv2.model.DTO.StudentTestScoreDTO;
import com.coolwen.experimentplatformv2.model.Student;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.ArrangeClassService;
import com.coolwen.experimentplatformv2.service.ClazzService;
import com.coolwen.experimentplatformv2.service.StudentService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 学生模块测试成绩管理
 * 列出所有学生的所有的模块的测试成绩
 *
 * @author 王雨来
 * @version 2020/5/13 12:21
 */

@Controller
@RequestMapping(value = "/testScoreManage")
public class ModleTestScoreController {

    protected static final Logger logger = LoggerFactory.getLogger(ModleTestScoreController.class);
    @Autowired
    public StudentRepository studentRepository;
    @Autowired
    public KaoheModelRepository kaoheModelRepository;
    @Autowired
    public StudentService studentService;
    @Autowired
    public ClazzService classService;
    @Autowired
    public ArrangeClassService arrangeClassService;


//    @GetMapping(value = "/list")
//    public String loadAllModel(Model model,@RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum) {
////        List<List<StudentTestScoreDTO>> d=null;
////        List<Student> c = studentRepository.findAll();
//        Page<Student> c = studentService.findAll(pageNum);
//        model.addAttribute("allStu",c);
//
//
////        for (Student b:c){
////            List<StudentTestScoreDTO> a = studentRepository.listStudentMTestAnswerDTO(b.getId());
////            d.add(a);
////        }
//
//
//        List<StudentTestScoreDTO> a = studentRepository.listStudentMTestAnswerDTO();
////        d.add(a);
//
//
////        List<List<StudentTestScoreDTO>> c=null;
////        for (StudentTestScoreDTO b:a){
////            int ones =b.getSid();
////            List<StudentTestScoreDTO> d = null;
////            for ()
////
////
////        }
//        logger.debug(a);
//        long modleNum = kaoheModelRepository.count();
//        model.addAttribute("allInfo",a);
//        model.addAttribute("num",modleNum);
//        List<Integer> list = new ArrayList<Integer>();
//        for(int i=1;i<=modleNum;i++){
//            list.add(i);
//        }
//        logger.debug(list);
//        model.addAttribute("numList",list);
//        return "kaohe/score_manage";
//    }

    /**
     * 列出所有学生的所有的模块的测试成绩
     *
     * @param model
     * @param select_orderId 搜索值
     * @param pageNum        分页
     * @return 页面
     */
    @GetMapping(value = "/list")
    public String loadAllModel(Model model,
                               @RequestParam(required = true, defaultValue = "") String select_orderId,
                               @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {

        if (select_orderId == "" ){
            boolean choose = false;
            model.addAttribute("Choose",choose);
        }else {
            boolean choose = true;
            model.addAttribute("Choose",choose);
        }

        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("登陆用户信息:" + user);
        //所有的下拉列表数据
        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(user.getId());
        model.addAttribute("arrangeInfoDTOs",arrangeInfoDTOs);



//        Page<Student> c = studentService.findAll(pageNum);
        Page<Student> c = studentService.findStudentPageAndXuehao(pageNum, select_orderId);

        model.addAttribute("allStu", c);
        model.addAttribute("selectOrderId", select_orderId);

        //查询当期班级
        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList", classList);
        logger.debug("sssssclass"+String.valueOf(classList));
        List<StudentTestScoreDTO> a = studentRepository.listStudentMTestAnswerDTO();

        logger.debug(String.valueOf(a));
        long modleNum = kaoheModelRepository.count();
        model.addAttribute("allInfo", a);
        model.addAttribute("num", modleNum);
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= modleNum; i++) {
            list.add(i);
        }
        logger.debug(String.valueOf(list));
        model.addAttribute("numList", list);


        return "kaohe/score_manage";
    }


    /**
     * 查询当前的安排表里面的所有学生的模块测试成绩
     * @param model
     * @param arrangeId
     * @param select_orderId
     * @param pageNum
     * @return
     */
    @GetMapping(value = "/list/{arrangeId}")
    public String loadOneClassModel(Model model,
                                    @PathVariable int arrangeId,
                                    @RequestParam(required = true, defaultValue = "") String select_orderId,
                                    @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {

        //判断是否选择安排表
        boolean choose = true;
        model.addAttribute("Choose",choose);

        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("登陆用户信息:" + user);
        //所有的下拉列表数据
        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(user.getId());
        model.addAttribute("arrangeInfoDTOs",arrangeInfoDTOs);

        //当前选择的安排表Id,用于判断按钮跳转连接,以及下拉列表回显
        model.addAttribute("selected",arrangeId);

//        Page<Student> c = studentService.findAll(pageNum);
        Page<Student> c = studentService.pageStudentByArrangeId(pageNum, arrangeId);

        logger.debug(">>>>>>>>>>>>>>>>>>c" + c);
        model.addAttribute("allStu", c);
        model.addAttribute("selectOrderId", select_orderId);

        //查询当期班级
        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList", classList);

//        List<StudentTestScoreDTO> a = studentService.listStudentMTestAnswerDTOByArrangeId(arrangeId);
        List<StudentTestScoreDTO> a = studentService.listStudentMTestAnswerDTOByArrangeId(arrangeId);


        logger.debug("a>>"+a);
        int modleNum = kaoheModelRepository.countByArrangeId(arrangeId);
        logger.debug("modleNum>"+modleNum);
//        int modleNum = 3;
        model.addAttribute("allInfo", a);
        model.addAttribute("num", modleNum);
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= modleNum; i++) {
            list.add(i);
        }
        logger.debug(String.valueOf(list));
        model.addAttribute("numList", list);

        model.addAttribute("path","/testScoreManage/list/"+arrangeId);
        return "kaohe/score_manage";
    }


    /**
     * 半导出
     * @param response
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) {
        List<StudentTestScoreDTO> a = studentRepository.listStudentMTestAnswerDTO();
//        List<Student> b = studentRepository.findAll();
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + a);
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
        FileExcelUtil.exportExcel(a, "用户详细信息", "用户表", StudentTestScoreDTO.class, "用户信息.xls", response);
    }

}

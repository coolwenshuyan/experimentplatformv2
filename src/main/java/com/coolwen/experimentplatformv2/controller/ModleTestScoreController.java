package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.KaoheModelRepository;
import com.coolwen.experimentplatformv2.dao.StudentRepository;
import com.coolwen.experimentplatformv2.filter.FileExcelUtil;
import com.coolwen.experimentplatformv2.model.ClassModel;
import com.coolwen.experimentplatformv2.model.DTO.StudentTestScoreDTO;
import com.coolwen.experimentplatformv2.model.Student;
import com.coolwen.experimentplatformv2.service.ClazzService;
import com.coolwen.experimentplatformv2.service.StudentService;
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

/**
 * 学生模块测试成绩管理
 * 列出所有学生的所有的模块的测试成绩
 * @author 王雨来
 * @version 2020/5/13 12:21
 */

@Controller
@RequestMapping(value = "/testScoreManage")
public class ModleTestScoreController {

    @Autowired
    public StudentRepository studentRepository;
    @Autowired
    public KaoheModelRepository kaoheModelRepository;
    @Autowired
    public StudentService studentService;
    @Autowired
    public ClazzService classService;


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
//        System.out.println(a);
//        long modleNum = kaoheModelRepository.count();
//        model.addAttribute("allInfo",a);
//        model.addAttribute("num",modleNum);
//        List<Integer> list = new ArrayList<Integer>();
//        for(int i=1;i<=modleNum;i++){
//            list.add(i);
//        }
//        System.out.println(list);
//        model.addAttribute("numList",list);
//        return "kaohe/score_manage";
//    }

    /**
     * 列出所有学生的所有的模块的测试成绩
     * @param model
     * @param select_orderId 搜索值
     * @param pageNum 分页
     * @return 页面
     */
    @GetMapping(value = "/list")
    public String loadAllModel(Model model,
                               @RequestParam(required = true, defaultValue = "")String select_orderId ,
                               @RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum) {

        //与[ModleTestReportController]大同小异
//        Page<Student> c = studentService.findAll(pageNum);
        Page<Student> c = studentService.findStudentPageAndXuehao(pageNum, select_orderId);

        model.addAttribute("allStu",c);
        model.addAttribute("selectOrderId",select_orderId);

        //查询当期班级
        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList",classList);

        List<StudentTestScoreDTO> a = studentRepository.listStudentMTestAnswerDTO();

        System.out.println(a);
        long modleNum = kaoheModelRepository.count();
        model.addAttribute("allInfo",a);
        model.addAttribute("num",modleNum);
        List<Integer> list = new ArrayList<Integer>();
        for(int i=1;i<=modleNum;i++){
            list.add(i);
        }
        System.out.println(list);
        model.addAttribute("numList",list);
        return "kaohe/score_manage";
    }

    /**
     *
     * @param model
     * @param classId
     * @param select_orderId
     * @param pageNum
     * @return
     */
    @GetMapping(value = "/{classId}/list")
    public String loadOneClassModel(Model model,
                                    @PathVariable int classId,
                                    @RequestParam(required = true, defaultValue = "")String select_orderId ,
                                    @RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum) {

//        Page<Student> c = studentService.findAll(pageNum);
        Page<Student> c = studentService.pageStudentByClassId(pageNum,classId);

        System.out.println(">>>>>>>>>>>>>>>>>>c"+c);
        model.addAttribute("allStu",c);
        model.addAttribute("selectOrderId",select_orderId);

        //查询当期班级
        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList",classList);

        List<StudentTestScoreDTO> a = studentRepository.listStudentMTestAnswerDTO();


        System.out.println(a);
        long modleNum = kaoheModelRepository.count();
        model.addAttribute("allInfo",a);
        model.addAttribute("num",modleNum);
        List<Integer> list = new ArrayList<Integer>();
        for(int i=1;i<=modleNum;i++){
            list.add(i);
        }
        System.out.println(list);
        model.addAttribute("numList",list);
        return "kaohe/score_manage";
    }

    //测试用导出
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) {
        List<StudentTestScoreDTO> a = studentRepository.listStudentMTestAnswerDTO();
//        List<Student> b = studentRepository.findAll();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+a);
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

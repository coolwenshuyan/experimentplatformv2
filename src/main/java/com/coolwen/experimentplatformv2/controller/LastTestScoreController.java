package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.KaoheModelRepository;
import com.coolwen.experimentplatformv2.dao.StudentRepository;
import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.ClassModel;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeInfoDTO;
import com.coolwen.experimentplatformv2.model.DTO.StudentLastTestScoreDTO;
import com.coolwen.experimentplatformv2.model.DTO.StudentReportScoreDTO;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * 期末理论测试成绩管理
 * 把所有人的期末成绩列到一起
 *
 * @author 王雨来
 * @version 2020/5/13 12:21
 */

@Controller
@RequestMapping(value = "/lastTestScoreManage")
public class LastTestScoreController {

    protected static final Logger logger = LoggerFactory.getLogger(LastTestScoreController.class);
    @Autowired
    public StudentRepository studentRepository;
    @Autowired
    public KaoheModelRepository kaoheModelRepository;
    @Autowired
    public StudentService studentService;
    @Autowired
    public ClazzService classService;


    @Autowired
    private ArrangeClassService arrangeClassService;//课程安排表

    /**
     * 所有列表
     *
     * @param model          传值
     * @param select_orderId 搜索值
     * @param pageNum        分页
     * @return 页面
     */
    @GetMapping(value = "/list")
    public String loadAllScore(Model model,
                               @RequestParam(required = true, defaultValue = "") String select_orderId,
                               @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {

        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("登陆用户信息:" + user);
//        Page<Student> c = studentService.findStudentPageAndXuehao(pageNum, select_orderId);
////        //搜索
//        logger.debug("当前学生信息为:" + c.getContent());
//        model.addAttribute("allStu", c);
//        model.addAttribute("selectOrderId", select_orderId);
//
//        //查询当期班级列表
//        //班级列表
////        List<ClassModel> classList = classService.findAllClass();
//        List<ClassModel> classList = classService.findCurrentClass();
//        model.addAttribute("classList", classList);
//
//        //学生成绩DTO列表
//        Page<StudentLastTestScoreDTO> a = studentService.listStudentLastTestAnswerDTO(pageNum);
//        logger.debug("学生成绩信息为:" + a.getContent());
//        model.addAttribute("allInfo", a);

        boolean choose = false;
        model.addAttribute("Choose", choose);
        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(user.getId());
        model.addAttribute("arrangeInfoDTOs", arrangeInfoDTOs);
        return "kaohe/lastTestScore";
    }

    /**
     * 分班级查看成绩
     *
     * @param model          传值
     * @param classId        班级id
     * @param select_orderId 搜索值
     * @param pageNum        分页
     * @return 页面
     */
    @GetMapping(value = "/{classId}/list")
    public String loadOneClassScore(Model model,
                                    @PathVariable int classId,
                                    @RequestParam(required = true, defaultValue = "") String select_orderId,
                                    @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {
        Page<Student> c = studentService.pageStudentByClassId(pageNum, classId);
        //搜索
        model.addAttribute("allStu", c);
        model.addAttribute("selectOrderId", select_orderId);

        //查询班级列表
        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList", classList);

        //学生成绩DTO列表
        Page<StudentLastTestScoreDTO> a = studentService.listStudentLastTestScoreDTOBYClassID(pageNum, classId);
        logger.debug(String.valueOf(a));
        model.addAttribute("allInfo", a);

        return "kaohe/lastTestScore";
    }
//    @GetMapping(value = "/{classId}/list")
//    public String loadOneClassModel(Model model,
//                               @PathVariable int classId,
//                               @RequestParam(required = true, defaultValue = "")String select_orderId ,
//                               @RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum) {
//
////        Page<Student> c = studentService.findAll(pageNum);
//        Page<Student> c = studentService.pageStudentByClassId(pageNum,classId);
//
//        logger.debug(">>>>>>>>>>>>>>>>>>c"+c);
//        model.addAttribute("allStu",c);
//        model.addAttribute("selectOrderId",select_orderId);
//
//        List<ClassModel> classList = classService.findAllClass();
//        model.addAttribute("classList",classList);
//
//        List<StudentTestScoreDTO> a = studentRepository.listStudentMTestAnswerDTO();
//
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
     * @param model
     * @param arrangeId      安排表id
     * @param select_orderId 学号
     * @param pageNum        页码
     * @return
     */
    @GetMapping(value = "/report/{arrangeId}")
    public String loadOneCourseModel(Model model, @PathVariable int arrangeId, @RequestParam(required = true, defaultValue = "") String select_orderId,
                                     @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {

        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("登陆用户信息:" + user);
        //所有的下拉列表数据
        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(user.getId());
        model.addAttribute("arrangeInfoDTOs", arrangeInfoDTOs);

        //当前选择的安排表Id,用于判断按钮跳转连接,以及下拉列表回显

        //判断是否选择了安排
        boolean choose = true;

        if (arrangeId == -1) {
            choose = false;
            model.addAttribute("Choose", choose);
//            model.addAttribute("selected1", "/report/allModule");
            return "redirect:/lastTestScoreManage/list";
        }
        model.addAttribute("selected", arrangeId);
        model.addAttribute("Choose", choose);
        //本安排的实验模块
        ArrangeClass arrangeClass = arrangeClassService.findById(arrangeId);
        logger.debug("安排信息为:" + arrangeClass);
        int classId = arrangeClass.getClassId();

//        Page<Student> c = studentService.findStudentPageAndXuehao(pageNum, select_orderId);
        Page<Student> studentPage = studentService.findStudentPageAndXuehaoAndClass(pageNum, classId, select_orderId);
        logger.debug("当前班级所有学生:" + studentPage.getContent());
        model.addAttribute("allStu", studentPage);
        model.addAttribute("selectOrderId", select_orderId);
        ClassModel classModel = classService.findById(classId);
        //查询当期班级
//        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList", classModel);

        //学生成绩DTO列表
        //todo 是否需要传递其他参数？？？？
        Page<StudentLastTestScoreDTO> a = studentService.listStudentLastTestAnswerDTO(pageNum, arrangeId);
//        Page<StudentLastTestScoreDTO> a = studentService.listStudentLastTestAnswerDTO(pageNum);
        logger.debug("学生成绩信息:" + a.getContent());
        model.addAttribute("allInfo", a);

        return "kaohe/lastTestScore";
    }

}

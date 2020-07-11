package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.KaoheModelRepository;
import com.coolwen.experimentplatformv2.dao.StudentRepository;
import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.ClassModel;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeInfoDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * 学生模块报告成绩管理
 * 列出所有学生的所有的模块的报告成绩
 *
 * @author 王雨来
 * @version 2020/5/13 12:21
 */

@Controller
@RequestMapping(value = "/reportScoreManage")
public class ModleTestReportController {

    protected static final Logger logger = LoggerFactory.getLogger(ModleTestReportController.class);
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
     * 列出所有学生的所有的模块的报告成绩
     *
     * @param select_orderId 搜索值
     * @param pageNum        分页
     * @return 页面
     */
    @GetMapping(value = "/list")
    public String loadAllModel(Model model,
                               @RequestParam(required = true, defaultValue = "") String select_orderId,
                               @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {

        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("登陆用户信息:" + user);
//        Page<Student> c = studentService.findAll(pageNum);
        //获得所有学生
//        Page<Student> c = studentService.findStudentPageAndXuehao(pageNum, select_orderId);
//        logger.debug(">>>>>>>>>>>>>>>>>>c" + c);
//        model.addAttribute("allStu", c);
//        model.addAttribute("selectOrderId", select_orderId);
//
//        //查询当期班级
//        List<ClassModel> classList = classService.findCurrentClass();
//        model.addAttribute("classList", classList);
//
//        //获得所有学生成绩DTO
//        List<StudentReportScoreDTO> a = studentRepository.listStudentMReportDTO();
////        List<StudentTestScoreDTO> a = studentRepository.listStudentMReportAnswerDTO();
//        logger.debug(String.valueOf(a));
//
//        //统计所以考核模块的个数,生成自增列表,以便thymeleaf生成表头
//        long modleNum = kaoheModelRepository.count();
//        model.addAttribute("allInfo", a);
//        model.addAttribute("num", modleNum);
//        List<Integer> list = new ArrayList<Integer>();
//        for (int i = 1; i <= modleNum; i++) {
//            list.add(i);
//        }
//        logger.debug(list.toString());
//        model.addAttribute("numList", list);

        boolean choose = false;
        model.addAttribute("Choose", choose);
        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(user.getId());
        model.addAttribute("arrangeInfoDTOs", arrangeInfoDTOs);
        return "kaohe/score2_manage";
    }


    /**
     * 列出所有学生的所有的模块的报告成绩
     *
     * @param classId        班级id 用来筛选
     * @param select_orderId 搜索值
     * @param pageNum        分页
     * @return 页面
     */
    @GetMapping(value = "/{classId}/list")
    public String loadOneClassModel(Model model,
                                    @PathVariable int classId,
                                    @RequestParam(required = true, defaultValue = "") String select_orderId,
                                    @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {

//        Page<Student> c = studentService.findAll(pageNum);
        //除了班级筛选,其它和上面的一模一样
        Page<Student> c = studentService.pageStudentByClassId(pageNum, classId);

        logger.debug(">>>>>>>>>>>>>>>>>>c" + c);
        model.addAttribute("allStu", c);
        model.addAttribute("selectOrderId", select_orderId);

        //查询当期班级
        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList", classList);

        List<StudentReportScoreDTO> a = studentRepository.listStudentMReportDTO();
//        List<StudentTestScoreDTO> a = studentRepository.listStudentMReportAnswerDTO();

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
        return "kaohe/score2_manage";
    }


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
            return "redirect:/reportScoreManage/list";
        }
        model.addAttribute("selected", arrangeId);
        model.addAttribute("Choose", choose);
        //本安排的实验模块
        ArrangeClass arrangeClass = arrangeClassService.findById(arrangeId);
        logger.debug("安排信息为:" + arrangeClass);
        int classId = arrangeClass.getClassId();
        int courseId = arrangeClass.getCourseId();
        //        Page<Student> c = studentService.findAll(pageNum);
        //获得当前班级所有学生
//        Page<Student> c = studentService.findStudentPageAndXuehao(pageNum, select_orderId);
        Page<Student> studentPage = studentService.findStudentPageAndXuehaoAndClass(pageNum, classId, select_orderId);
        logger.debug("当前班级所有学生:" + studentPage.getContent());
        model.addAttribute("allStu", studentPage);
        model.addAttribute("selectOrderId", select_orderId);
        ClassModel classModel = classService.findById(classId);
        //查询当期班级
//        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList", classModel);

        //获得所有学生成绩DTO
//        List<StudentReportScoreDTO> a = studentRepository.listStudentMReportDTO();
        List<StudentReportScoreDTO> a = studentRepository.listStudentMReportDTOByArrangeClassId(arrangeId);
//        List<StudentTestScoreDTO> a = studentRepository.listStudentMReportAnswerDTO();
        logger.debug("学生成绩:" + a);

//        a.stream().filter(studentReportScoreDTO -> studentReportScoreDTO.getMid());
        //按模块去重，以便统计不同模块数目
        List<StudentReportScoreDTO> b = a.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(Comparator.comparing(StudentReportScoreDTO::getmName))), ArrayList::new)
        );

        //统计学生成绩当中所有考核模块的个数,生成自增列表,以便thymeleaf生成表头
//        long modleNum = kaoheModelRepository.count();
        long modleNum = b.size();
        logger.debug("modleNum:" + modleNum);
        //加入了班级，但是没有考核模块
        if (modleNum == 0) {
            return "redirect:/reportScoreManage/list";
        }
        model.addAttribute("allInfo", a);
        model.addAttribute("num", modleNum);
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= modleNum; i++) {
            list.add(i);
        }
        logger.debug(list.toString());
        model.addAttribute("numList", list);


        return "kaohe/score2_manage";
    }

}

package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.KaoheModelRepository;
import com.coolwen.experimentplatformv2.dao.StudentRepository;
import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeInfoDTO;
import com.coolwen.experimentplatformv2.model.DTO.CollegeReportStuExpDto;
import com.coolwen.experimentplatformv2.model.DTO.StudentReportScoreDTO;
import com.coolwen.experimentplatformv2.service.*;
import com.coolwen.experimentplatformv2.utils.HtmlToPdf;
import com.coolwen.experimentplatformv2.utils.ZipUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static org.apache.catalina.startup.ExpandWar.deleteDir;

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

    @Autowired
    private CollegeReportService collegeReportService;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private ExpModelService expModelService;

    @Value("${web.pdf-rootPath}")
    private String rootPath;

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
//        int courseId = arrangeClass.getCourseId();
        model.addAttribute("selectOrderId", select_orderId);
        ClassModel classModel = classService.findById(classId);
        //查询当期班级
//        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList", classModel);
        //        Page<Student> c = studentService.findAll(pageNum);
        //获得当前班级所有学生
//        Page<Student> c = studentService.findStudentPageAndXuehao(pageNum, select_orderId);
        Page<Student> studentPage = studentService.findStudentPageAndXuehaoAndClass(pageNum, classId, select_orderId);
        logger.debug("当前班级所有学生:" + studentPage.getContent());
        model.addAttribute("allStu", studentPage);
        

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
/*        if (modleNum == 0) {
            return "redirect:/reportScoreManage/list";
        }*/

        if (studentPage.getTotalPages()>0){
            model.addAttribute("needPaging",true);
        }else {
            model.addAttribute("needPaging",false);
        }

        model.addAttribute("allInfo", a);
        model.addAttribute("num", modleNum);
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= modleNum; i++) {
            list.add(i);
        }
        logger.debug(list.toString());
        model.addAttribute("numList", list);

        model.addAttribute("path","/reportScoreManage/report/"+arrangeId);

        return "kaohe/score2_manage";
    }

    /**
     * 查询到当前课程安排所对应的所有考核模块
     * @param arrangeId
     * @return
     */
    @GetMapping(value = "/kaoheModelList/{arrangeId}")
    public String kaoheModelList(@PathVariable int arrangeId,Model model){
        List<ExpModel> expModels = expModelService.findExpModelByArrangeId(arrangeId);
        logger.debug("实验模块:"+expModels);
//        model.addAttribute("arrangeId",arrangeId);
        model.addAttribute("expModels",expModels);
        return "kaohe/kaohemodlelist";
    }

    @PostMapping(value = "/kaoheModelList/{arrangeId}")
    public void kaoheModelList(@PathVariable int arrangeId, HttpServletRequest request, HttpServletResponse response) throws Exception{
        String[] ids = null;
        ids = request.getParameterValues("mids");
        List<Integer> mids = new ArrayList<Integer>();
        if(ids!=null) {
            for (String mid : ids){
                mids.add(Integer.parseInt(mid));
            }
        }
        List<CollegeReport> collegeReports = collegeReportService.findCollegeReportsByArrangeIdAndMids(arrangeId,mids);
        logger.debug("collegeReports数量:"+collegeReports.size());
        if (collegeReports.size() == 0){
            return;
        }
        //总文件夹名字
        String f = "";
        //子文件夹名
        String fileName = "";
        //pdf文件名
        String pdfName = "";
        //创建总文件夹
        File filef = new File("");
        //创建子文件夹
        File file = new File("");
        List<File> fileList = new ArrayList<>();

        for (CollegeReport collegeReport : collegeReports){
            logger.debug(collegeReport.getMid()+">>>>"+collegeReport.getStuid());
            Student s = studentService.findStudentById(collegeReport.getStuid());
            ExpModel expModel = expModelService.findExpModelsByKaoheMid(collegeReport.getMid());

            fileName = expModel.getM_name();
            pdfName = expModel.getM_name()+"-"+s.getStuXuehao()+"-"+s.getStuName();

            f = collegeReport.getCrClassName();
            filef = new File(rootPath+f);
            //创建文件夹
            file = new File(rootPath+f+"/"+fileName);
            fileList.add(file);
            if (file.mkdirs()) {
                logger.debug("多级层文件夹创建成功！创建后的文件目录为：" + file.getPath() + ",上级文件为:" + file.getParent());
            }

            CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(collegeReport.getStuid(),collegeReport.getMid());
            final Context ctx = new Context();
            //这里图片路径设置成了全路径，要不然在pdf中不显示
            ctx.setVariable("collegeReport",collegeReportStuExpDto);
            //"/contract" 为模板文件，注意路径和“/”
            String htmlcontext = templateEngine.process("bg_student1", ctx);
            HtmlToPdf.topdf(htmlcontext,rootPath+f+"/"+fileName+"/"+pdfName+".pdf");
        }
        logger.debug("f:"+f);
        /** 3.设置response的header */
        response.setContentType("application/zip");
//        response.setHeader("Content-Disposition", "attachment; filename=阿斯蒂芬.zip");
        response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(f+".zip", "UTF-8"));

        /** 4.调用工具类，下载zip压缩包 */
        ZipUtil.toZip(rootPath+f, response.getOutputStream(),true);

        if (filef.isFile()) {
            filef.delete(); // 删除文件
        } else {
            File[] files = filef.listFiles();
            if (files == null) {
                filef.delete(); // 删除空文件夹
            } else {
                for (File fs : files) {
                    deleteDir(fs); // 迭代删除非空文件夹
                }
                filef.delete();
            }
        }

    }


}

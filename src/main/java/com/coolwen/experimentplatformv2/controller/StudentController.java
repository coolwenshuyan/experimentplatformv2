package com.coolwen.experimentplatformv2.controller;

import com.alibaba.fastjson.JSONObject;
import com.coolwen.experimentplatformv2.kit.ShiroKit;
import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.model.ClassModel;
import com.coolwen.experimentplatformv2.model.DTO.StuDocker;
import com.coolwen.experimentplatformv2.model.DTO.StudentListDTO;
import com.coolwen.experimentplatformv2.model.DTO.StudentVo;
import com.coolwen.experimentplatformv2.model.Student;
import com.coolwen.experimentplatformv2.service.*;
import com.coolwen.experimentplatformv2.service.ClazzService;
import com.coolwen.experimentplatformv2.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 后台管理系统 学生管理页面
 * @Author 张健银
 * @Version 1.0
 * @Date 2020/5/31
 */

@Controller
@RequestMapping("/studentManage")
public class StudentController {
    @Autowired
    StudentService studentservice;
    @Autowired
    ClazzService clazzService;
    @Autowired
    KaoheModelService kaoheModelService;
    @Autowired
    KaoHeModelScoreService kaoHeModelScoreService;
    @Autowired
    TotalScoreCurrentService totalScoreCurrentService;
    @Autowired
    ModuleTestAnswerStuService moduleTestAnswerStuService;
    @Autowired
    ReportAnswerService reportAnswerService;
    @Autowired
    TotalScorePassService totalScorePassService;
    @Autowired
    ExpModelService expModelService;
    @Autowired
    CollegeReportService collegeReportService;
    @Autowired
    DockerService dockerService;

    @Autowired
    ArrangeClassService arrangeClassService;

    protected static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    //查询学生列表
    @GetMapping("/list")
    public String studentList(Model model, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        Page<StudentVo> list = studentservice.findStudentsByStuCheckstate(pageNum);
        model.addAttribute("studentList", list);
        return "student/student_list";
    }


    //搜索学生学号
    @GetMapping("/viewStudent")
    public String viewStudent(@RequestParam("stu_xuehao") String stu_xuehao, Model model) {
        StudentVo student = studentservice.findStudentsByStuXuehao(stu_xuehao);
        if (student == null) {
            return "redirect:/student/list";
        }
        model.addAttribute("stu", student);
        return "student/student_view";
    }

    //执行学生删除
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        //删除模块回答
        moduleTestAnswerStuService.deleteModuleTestAnswerStuByStuId(id);
        //删除报告回答
        reportAnswerService.deleteReportAnswerByStuId(id);
        //删除考核模块成绩
        kaoHeModelScoreService.deleteKaoheModuleScoreByStuId(id);
        Student student = studentservice.findStudentById(id);
        //判断学生班级情况进行当期往期操作
        if (student.getClassId() != 0) {
            ClassModel classModel = clazzService.findById(student.getClassId());
            if (classModel.getClassIscurrent() == false) {
                //当期
                totalScoreCurrentService.deleteTotalScoreCurrentByStuId(id);
            } else {
                totalScorePassService.delteTotalScorePassByStuId(id);
            }
        }
        Docker docker = dockerService.findDockerByStu_id(id);
        if (docker != null) {
            docker.setStu_id(0);
            docker.setDc_start_datetime(null);
            docker.setDc_end_datetime(null);
            docker.setDc_state(false);
            dockerService.addDocker(docker);
        }
        studentservice.deleteStudentById(id);
        return "redirect:/studentManage/list";
    }

    //进入学生编辑
    @GetMapping("/editStudent/{id}")
    public String toeditStudent(@PathVariable("id") int id, Model model) {
        Student s = studentservice.findStudentById(id);
        StudentListDTO student = null;
        if (s.getClassId() == 0) {
            student = new StudentListDTO();
            student.setClassid(0);
            student.setClassName(null);
            student.setId(s.getId());
            student.setStuCheckstate(s.isStuCheckstate());
            student.setStuIsinschool(s.isStuIsinschool());
            student.setStuMobile(s.getStuMobile());
            student.setStuName(s.getStuName());
            student.setStuPassword(s.getStuPassword());
            student.setStuUname(s.getStuUname());
            student.setStuXuehao(s.getStuXuehao());
        } else {
            student = studentservice.findStudentDTOById(id);
        }
        List<ClassModel> classModels = clazzService.findAllClass();
        model.addAttribute("stu", student);
        model.addAttribute("class", classModels);
        return "student/student_alter";

    }

    //学生编辑操作
    @PostMapping("/editStudent/{id}")
    public String editStudent(@PathVariable("id") int id,
                              String stu_uname,
                              String stu_name,
                              String stu_xuehao,
                              String classid,
                              Boolean stuIsinschool,
                              String stu_password
    ) {
        logger.debug("进入接口：/editStudent/" + id);
        logger.debug("班级id为：" + classid);
        Student student = studentservice.findStudentById(id);
        student.setStuUname(stu_uname);
        student.setStuName(stu_name);
        if (stuIsinschool == true) {
            student.setStuXuehao(stu_xuehao);
        } else {
            student.setStuXuehao("");
        }
        if (ShiroKit.isEmpty(classid)) {
            classid = "0";
        }
        student.setStuIsinschool(stuIsinschool);
        student.setStuPassword(stu_password);
        student.setClassId(Integer.parseInt(classid));
        logger.debug("修改学生信息为：" + student);
        studentservice.updateStudent(student);
        return "redirect:/studentManage/list";
    }


    @GetMapping("/studentCheck/{stuid}")
    @ResponseBody
    public String studentCheck(String stu_uname, String stu_xuehao, @PathVariable("stuid") int stuid) {
        Student now = studentservice.findStudentById(stuid);
        if (stu_uname != null) {
            Student student = studentservice.findByUname(stu_uname);
            if (student != null) {
                if (!student.getStuUname().equals(now.getStuUname())) {
                    return "该账号已被注册";
                }
            }
        } else if (stu_xuehao != null) {
            Student student = studentservice.findByStuXuehao(stu_xuehao);
            if (stu_xuehao.length() != 10) {
                return "请输入正确的学号";
            } else if (student != null) {
                if (!student.getStuXuehao().equals(now.getStuXuehao())) {
                    return "该学号已被注册";
                }
            }
        }
        return "Metal";
    }

    //返回待审核学生列表
    @GetMapping("/toBeReviewd")
    public String toBeReviewed(@RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum, Model model) {
        model.addAttribute("waitStudent", studentservice.findToBeReviewedStudent(pageNum));
        return "student/student_examine";
    }

    //通过审核操作
    @GetMapping("/passReviewd/{id}")
    public String passReview(@PathVariable("id") int id) {
        Student student = studentservice.findStudentById(id);
        logger.debug("" + student);
        student.setStuCheckstate(true);
        //todo 只修改
        studentservice.updateStudent(student);
        return "redirect:/studentManage/toBeReviewd";
    }

    @GetMapping("/passStuMessage/{id}")
    @ResponseBody
    public String passStuMessage(@PathVariable("id") int id) {
        Student student = studentservice.findStudentById(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stu", student);
        return String.valueOf(jsonObject);
    }

    @GetMapping("/dockerUrl")
    @ResponseBody
    public String dockerUrl() {
        List<Docker> list = dockerService.findDockersByTenData();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", list);
        return String.valueOf(jsonObject);
    }

    @PostMapping("/giveDocker/{id}")
    public String giveDocker(@PathVariable("id") int id, String dc_url, String dc_start_datetime, String dc_end_datetime) {
        if (dc_url.equals("noValue")) {
            return "redirect:/studentManage/toBeReviewd";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date starsDate = null;
        Date endDate = null;
        try {
            if (dc_start_datetime != "") {
                starsDate = simpleDateFormat.parse(dc_start_datetime);
            } else {
                starsDate = simpleDateFormat.parse("1314-06-21 00:00:00");
            }
            if (dc_end_datetime != "") {
                endDate = simpleDateFormat.parse(dc_end_datetime);
            } else {
                endDate = simpleDateFormat.parse("1314-06-21 00:00:00");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Docker docker = dockerService.findDockerByDc_url(dc_url);
        docker.setStu_id(id);
        docker.setDc_state(true);
        docker.setDc_start_datetime(starsDate);
        docker.setDc_end_datetime(endDate);
        dockerService.addDocker(docker);
        return "redirect:/studentManage/toBeReviewd";
    }


    @GetMapping("/updateStuDocker/{id}")
    @ResponseBody
    public String updateStuDocker(@PathVariable("id") int id) {
        Student student = studentservice.findStudentById(id);
        Docker docker = dockerService.findDockerByStu_id(id);
        JSONObject jsonObject = new JSONObject();
        StuDocker stuDocker = new StuDocker();
        stuDocker.setStuName(student.getStuName());
        stuDocker.setStuXuehao(student.getStuXuehao());
        if (docker != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date flag = null;
            try {
                flag = simpleDateFormat.parse("1314-06-21 00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            stuDocker.setDc_url(docker.getDc_url());
            if (flag.equals(docker.getDc_start_datetime())) {
                stuDocker.setDc_start_datetime(null);
            } else {
                stuDocker.setDc_start_datetime(simpleDateFormat.format(docker.getDc_start_datetime()));
            }
            if (flag.equals(docker.getDc_end_datetime())) {
                stuDocker.setDc_end_datetime(null);
            } else {
                stuDocker.setDc_end_datetime(simpleDateFormat.format(docker.getDc_end_datetime()));
            }
        }
        jsonObject.put("docker", stuDocker);
        return String.valueOf(jsonObject);
    }

    @PostMapping("/updateStuDocker/{id}")
    public String doUpdateStuDocker(@PathVariable("id") int id, String dc_url, String dc_start_datetime, String dc_end_datetime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date starsDate = null;
        Date endDate = null;
        try {
            if (dc_start_datetime != "") {
                starsDate = simpleDateFormat.parse(dc_start_datetime);
            } else {
                starsDate = simpleDateFormat.parse("1314-06-21 00:00:00");
            }
            if (dc_end_datetime != "") {
                endDate = simpleDateFormat.parse(dc_end_datetime);
            } else {
                endDate = simpleDateFormat.parse("1314-06-21 00:00:00");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Docker preDocker = dockerService.findDockerByStu_id(id);
        Docker nexDocker = dockerService.findDockerByDc_url(dc_url);
        if (dc_url.equals("duck")) {
            if (preDocker == null) {
                return "redirect:/studentManage/list";
            }
            preDocker.setDc_end_datetime(null);
            preDocker.setDc_start_datetime(null);
            preDocker.setDc_state(false);
            preDocker.setStu_id(0);
            dockerService.addDocker(preDocker);
            return "redirect:/studentManage/list";
        }
        if (dc_url.equals("metal")) {
            if (preDocker == null) {
                return "redirect:/studentManage/list";
            }
            preDocker.setDc_start_datetime(starsDate);
            preDocker.setDc_end_datetime(endDate);
            dockerService.addDocker(preDocker);
            return "redirect:/studentManage/list";
        }
        if (preDocker == null) {
            nexDocker.setDc_state(true);
            nexDocker.setStu_id(id);
            nexDocker.setDc_start_datetime(starsDate);
            nexDocker.setDc_end_datetime(endDate);
            dockerService.addDocker(nexDocker);
            return "redirect:/studentManage/list";
        }

        preDocker.setDc_end_datetime(null);
        preDocker.setDc_start_datetime(null);
        preDocker.setStu_id(0);
        preDocker.setDc_state(false);
        dockerService.addDocker(preDocker);
        nexDocker.setDc_state(true);
        nexDocker.setStu_id(id);
        nexDocker.setDc_start_datetime(starsDate);
        nexDocker.setDc_end_datetime(endDate);
        dockerService.addDocker(nexDocker);
        return "redirect:/studentManage/list";
    }

    @GetMapping("/getDocker/{id}")
    @ResponseBody
    public String getDocker(@PathVariable("id") int id) {
        JSONObject jsonObject = new JSONObject();
        Docker docker = dockerService.findByid(id);
        jsonObject.put("docker", docker);
        return String.valueOf(jsonObject);
    }


    //驳回学生审核
    @GetMapping("/deleteReviewd/{id}")
    public String deleteReviewd(@PathVariable("id") int id) {
        studentservice.deleteStudentById(id);
        return "redirect:/studentManage/toBeReviewd";
    }

    //搜索审核学生列表
    @GetMapping("/viewReviewd")
    public String viewReviewd(@RequestParam("stu_xuehao") String stu_xuehao, Model model) {
        Student student = studentservice.findStudentByStuXuehao(stu_xuehao);
        model.addAttribute("student", student);
        return "student/student_examine_view";
    }

    //返回班级列表
    @GetMapping("/classManage")
    public String classList(Model model, @RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum) {
        model.addAttribute("classMsg", clazzService.findClassList(pageNum));
        return "student/class_administer";
    }

    //进入班级编辑
    @GetMapping("/toEditClass/{id}")
    public String editClass(@PathVariable("id") int id, Model model) {
        model.addAttribute("class", clazzService.findById(id));
        return "student/class_alter";
    }

    //进入班级添加
    @GetMapping("/toAddClass")
    public String toAddClass() {
        return "student/class_add";
    }


    //添加班级操作
    @PostMapping("/addClass")
    public String addClass(
            String class_collage,
            String class_major,
            String class_grade,
            String class_name,
            String class_teacher
    ) {
        ClassModel clazz = new ClassModel();
        clazz.setClassCollage(class_collage);
        clazz.setClassMajor(class_major);
        clazz.setClassGrade(class_grade);
        clazz.setClassName(class_name);
        clazz.setClassTeacher(class_teacher);
        clazzService.saveClazz(clazz);
        return "redirect:/studentManage/classManage";
    }


    //修改班级
    @PostMapping("/editClass/{id}")
    public String editClass(@PathVariable("id") int id,
                            String class_collage,
                            String class_major,
                            String class_grade,
                            String class_name,
                            String class_teacher,
                            Boolean classIscurrent
    ) {
        ClassModel clazz = clazzService.findById(id);
        clazz.setClassCollage(class_collage);
        clazz.setClassMajor(class_major);
        clazz.setClassGrade(class_grade);
        clazz.setClassName(class_name);
        clazz.setClassTeacher(class_teacher);
        clazz.setClassIscurrent(classIscurrent);
        clazzService.saveClazz(clazz);
        //如果进行班级设置往期，则进行成绩固化
        if (classIscurrent == true) {
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
            List<KaoheModel> kaoheModelList = kaoheModelService.findAll();
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
            List<Student> studentList = studentservice.findStudentByClassId(id);
            for (Student s : studentList) {
                //拼接之前要初始化
                kaohe_mtestscore = "";
                kaohe_mreportscore = "";
                kaohe_mtestscore_baifengbi = "";
                kaohe_mreportscore_baifengbi = "";
                kaohe_mscale = "";

                for (KaoheModel k : kaoheModelList) {
                    //获取该班级下学生每个考核模块信息
                    KaoHeModelScore kaoHeModelScore = kaoHeModelScoreService.findKaoHeModelScoreByStuIdAndId(s.getId(), k.getId());
                    //进行成绩固化临时存储
                    kaohe_mtestscore += kaoHeModelScore.getmTestScore() + ";";
                    kaohe_mreportscore += kaoHeModelScore.getmReportScore() + ";";
                    kaohe_mtestscore_baifengbi += k.getM_test_baifenbi() + ";";
                    kaohe_mreportscore_baifengbi += k.getM_report_baifenbi() + ";";
                    kaohe_mscale += k.getM_scale() + ";";
                }

                //拼接之后，如果有数据要去除最后一个分号
                if (kaohe_mtestscore.length() > 0) {
                    kaohe_mtestscore = kaohe_mtestscore.substring(0, kaohe_mtestscore.length() - 1);
                    kaohe_mreportscore = kaohe_mreportscore.substring(0, kaohe_mreportscore.length() - 1);
                    kaohe_mtestscore_baifengbi = kaohe_mtestscore_baifengbi.substring(0, kaohe_mtestscore_baifengbi.length() - 1);
                    kaohe_mreportscore_baifengbi = kaohe_mreportscore_baifengbi.substring(0, kaohe_mreportscore_baifengbi.length() - 1);
                    kaohe_mscale = kaohe_mscale.substring(0, kaohe_mscale.length() - 1);
                }

                //todo 需要知道安排表id
                int arrageId = 0;
                TotalScoreCurrent totalScoreCurrent = totalScoreCurrentService.findTotalScoreCurrentByStuId2(s.getId(), arrageId);
                //进行成绩固化操作
                totalScorePass = new TotalScorePass();
                totalScorePass.setStuId(s.getId());
                //存入考核模块数目
                totalScorePass.setKaoheNum(kaoheModelList.size());
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
                totalScorePassService.save(totalScorePass);
                //删除学生模块回答，报告回答，考核成绩表，以及当期总评成绩表
                moduleTestAnswerStuService.deleteModuleTestAnswerStuByStuId(s.getId());
                reportAnswerService.deleteReportAnswerByStuId(s.getId());
                kaoHeModelScoreService.deleteKaoheModuleScoreByStuId(s.getId());
                totalScoreCurrentService.deleteTotalScoreCurrentByStuId(s.getId());
            }

        }
        return "redirect:/studentManage/classManage";
    }

    @GetMapping("/checkClassName/{classid}")
    @ResponseBody
    public String checkClassName(@PathVariable("classid") int classid, String className) {
        ClassModel now = clazzService.findById(classid);
        if (clazzService.findClassModelByClassName(className) != null) {
            if (!className.equals(now.getClassName())) {
                return "该班级名已存在";
            }
        }
        return "Metal";
    }


    //删除班级操作
    @GetMapping("/deleteClass/{id}")
    public String deleteClass(@PathVariable("id") int id) {
        ClassModel classModel = clazzService.findById(id);
        List<Student> studentList = studentservice.findStudentByClassId(id);
        //根据班级状态对所属学生进行当期或往期成绩删除
        if (classModel.getClassIscurrent() == false) {
            for (Student student : studentList) {
                totalScoreCurrentService.deleteTotalScoreCurrentByStuId(student.getId());
                //删除学生考核模块成绩记录
                kaoHeModelScoreService.deleteKaoheModuleScoreByStuId(student.getId());
            }
        } else {
            for (Student student : studentList) {
                totalScorePassService.delteTotalScorePassByStuId(student.getId());
            }
        }
        //将学生班级重置
        clazzService.deleteClazz(id);
        for (Student s : studentList) {
            s.setClassId(0);
            studentservice.saveStudent(s);
        }
        return "redirect:/studentManage/classManage";
    }

    //班级页面管理学生
    @GetMapping("/addStudent/{id}")
    public String toaddStudent(@PathVariable("id") int id, Model model) {
        model.addAttribute("student", studentservice.findStudentByClassId(id));
        model.addAttribute("classId", id);
        ClassModel classModel = clazzService.findById(id);
        model.addAttribute("class", classModel);
        return "student/class_add_student";
    }

    @RequestMapping(value = "/findxuehao", method = RequestMethod.POST)
    public void findStudentByXuehao(String xuehao, HttpServletResponse resp) throws IOException {
//        logger.debug("ResId:" + resId+"roleId:"+roleId+"c:"+c);
        Student student = studentservice.findByStuXuehao(xuehao);
        logger.debug("输入学生信息：" + student);
        if (student.getClassId() == 0) {
            resp.getWriter().println("1");
        } else {
            resp.getWriter().println("0");
        }
    }

    //为班级进行添加学生操作
    @PostMapping("/viewAddStudent/{id}")
    public String addStudent(@RequestParam("stu_xuehao") String xuehao, @PathVariable("id") int classId) {
        logger.debug("进入接口：/viewAddStudent/" + classId);
        Student student = studentservice.findclassStudentByStuXuehao(xuehao);
        logger.debug("输入学生信息：" + student);
        //分班的学生必须是审核过了
        if (student == null || student.getClassId() != 0) {
            return "redirect:/studentManage/addStudent/" + classId;
        }
        student.setClassId(classId);
//        1、根据班级ID查到这个班对应的所有按排表
        List<ArrangeClass> arrangeClassList = arrangeClassService.findByClassId(classId);
        logger.debug("当前班级所有的安排表信息：" + arrangeClassList);
        //根根据安排表查到对应的所有考核模块
        List<Integer> integerList = arrangeClassList.stream().map(ArrangeClass -> ArrangeClass.getId()).collect(Collectors.toList());
        logger.debug("当前班级所有的安排表ID信息：" + integerList);
        List<KaoheModel> kaoheModels = kaoheModelService.findByArrange_idIn(integerList);
        logger.debug("安排表ID为：" + integerList + "考核模块信息：" + kaoheModels);
        KaoHeModelScore kaoHeModelScore = null;
        //分班后进行学生考核成绩表生成操作
        if (kaoheModels.size() > 0) {
            for (KaoheModel km : kaoheModels) {
                logger.debug("考核模块信息：" + km);
                //学生添加进班级时，删除该学生考核模块相关测试答题记录
                reportAnswerService.deleteByStuIdModelId(km.getM_id(), student.getId());
                moduleTestAnswerStuService.deleteByStuIdModelId(km.getM_id(), student.getId());
                collegeReportService.deleteByStuIdModelId(km.getM_id(), student.getId());
                logger.debug("考核模块ID：" + km.getM_id() + "学生ID:" + student.getId());
                KaoHeModelScore pre = kaoHeModelScoreService.findKaoheModelScoreByMid(km.getM_id(), student.getId());
                logger.debug("考核模块ID：" + km.getM_id() + "学生ID:" + student.getStuXuehao() + "考核成绩" + pre);
                if (pre == null) {
                    kaoHeModelScore = new KaoHeModelScore();
                    kaoHeModelScore.settKaohemodleId(km.getId());
                    kaoHeModelScore.setStuId(student.getId());
                    kaoHeModelScore.setmOrder(km.getM_order());
                    kaoHeModelScore.setmScale(km.getM_scale());
                    logger.debug("生成该模块新考核成绩:" + kaoHeModelScore);
                    kaoHeModelScoreService.add(kaoHeModelScore);
                }
            }
        }
        //生成当期总评成绩表
        if (arrangeClassList.size() > 0) {
            for (ArrangeClass arrangeClass : arrangeClassList) {
                TotalScoreCurrent t = totalScoreCurrentService.findTotalScoreCurrentByStuId2(student.getId(), arrangeClass.getId());
                logger.debug("查询当期成绩:" + t);
                if (t == null) {
                    TotalScoreCurrent totalScoreCurrent = new TotalScoreCurrent();
                    totalScoreCurrent.setStuId(student.getId());
                    //安排表id相同，就应该属于同一个课程下的考核模块
                    int kaoheNum = kaoheModelService.findAllByArrageId(arrangeClass.getId()).size();
                    totalScoreCurrent.setKaoheNum(kaoheNum);
                    totalScoreCurrent.setArrageId(arrangeClass.getId());
                    logger.debug("生成当期成绩:" + totalScoreCurrent);
                    totalScoreCurrentService.add(totalScoreCurrent);
                }
            }
        }

        studentservice.saveStudent(student);
        return "redirect:/studentManage/addStudent/" + classId;
    }

    //班级学生移除操作
    @GetMapping("/deleteStuClass/{id}")
    public String deleteStuClass(@PathVariable("id") int stuid) {
        logger.debug("进入接口：/deleteStuClass/" + stuid);
        //查询该学生总评成绩表中记着加安排表ID
        List<TotalScoreCurrent> totalScoreCurrentList = totalScoreCurrentService.findeAllBystuid(stuid);
        //获取安排表ID
        List<Integer> integerList = totalScoreCurrentList.stream().map(TotalScoreCurrent -> TotalScoreCurrent.getArrageId()).collect(Collectors.toList());
        logger.debug("当前班级所有的安排表ID信息：" + integerList);
        List<KaoheModel> kaoheModels = kaoheModelService.findByArrange_idIn(integerList);
        logger.debug("安排表ID为：" + integerList + "考核模块信息：" + kaoheModels);
        for (KaoheModel km : kaoheModels) {
            logger.debug("考核模块信息：" + km);
            //删除该学生考核模块相关测试答题记录
            reportAnswerService.deleteByStuIdModelId(km.getM_id(), stuid);
            moduleTestAnswerStuService.deleteByStuIdModelId(km.getM_id(), stuid);
            collegeReportService.deleteByStuIdModelId(km.getM_id(), stuid);
            logger.debug("考核模块ID：" + km.getM_id() + "学生ID:" + stuid);
            KaoHeModelScore pre = kaoHeModelScoreService.findKaoheModelScoreByMid(km.getM_id(), stuid);
            logger.debug("考核模块ID：" + km.getM_id() + "学生ID:" + stuid + "考核成绩" + pre);
        }

        totalScoreCurrentService.deleteTotalScoreCurrentByStuId(stuid);

        List<KaoHeModelScore> kaoHeModelScores = kaoHeModelScoreService.findKaoheModuleScoreByStuId(stuid);
        //删除学生考核信息
        kaoHeModelScoreService.deleteAllKaohe(kaoHeModelScores);


        Student student = studentservice.findStudentById(stuid);
        int preClassId = student.getClassId();
        student.setClassId(0);
        studentservice.saveStudent(student);
        return "redirect:/studentManage/addStudent/" + preClassId;
    }

    //搜索班级操作
    @GetMapping("/viewClass")
    public String viewClass(@RequestParam("class_name") String class_name, Model model) {
        ClassModel clazz = studentservice.findClazzByClassName(class_name);
        if (clazz != null) {
            model.addAttribute("class", clazz);
            return "student/class_view";
        }
        return "redirect:/studentManage/classManage";
    }

    @GetMapping("/dockerList")
    public String dockerList(@RequestParam(value = "pageNum", required = true, defaultValue = "0") int pageNum, Model model) {
        model.addAttribute("dockerList", dockerService.findAll(pageNum));
        return "student/docker_list";
    }

    @PostMapping("/addDocker")
    public String addDocker(String dc_url) {
        Docker docker = dockerService.findDockerByDc_url(dc_url);
        if (docker == null) {
            Docker d = new Docker();
            d.setDc_url(dc_url);
            dockerService.addDocker(d);
        }
        return "redirect:/studentManage/dockerList";
    }

    @PostMapping("/updateDocker/{id}")
    public String updateDocker(@PathVariable("id") int id, String dc_url) {
        Docker docker = dockerService.findByid(id);
        Docker docker1 = dockerService.findDockerByDc_url(dc_url);
        if (docker1 == null) {
            if (!docker.getDc_url().equals(dc_url)) {
                docker.setDc_url(dc_url);
                dockerService.addDocker(docker);
            }
        }
        return "redirect:/studentManage/dockerList";
    }

    @PostMapping("/delDocker/{id}")
    public String delDocker(@PathVariable("id") int id) {
        dockerService.delDocker(id);
        return "redirect:/studentManage/dockerList";
    }


}
















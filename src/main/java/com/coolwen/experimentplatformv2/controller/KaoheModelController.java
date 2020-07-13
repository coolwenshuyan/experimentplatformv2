package com.coolwen.experimentplatformv2.controller;


import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeInfoDTO;
import com.coolwen.experimentplatformv2.model.DTO.KaoheModelAndExpInfoDTO;
import com.coolwen.experimentplatformv2.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 2020/6/3
 * 王雨来
 * 新增总成绩考核中考试与模块总成绩权重初始化 /GreatestWeight
 */

/**
 * 对考核进行编辑管理
 * 列出所有模块/所有考核模块,将实验模块移入/移出考核,修改/添加考核信息
 *
 * @author 王雨来
 * @version 2020/5/13 12:21
 */

@Controller
@RequestMapping("kaohemodel")
public class KaoheModelController {
    @Autowired
    private KaoheModelService kaoheModelService;
    @Autowired
    private ExpModelService expModelService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private KaoHeModelScoreService kaoHeModelScoreService;
    @Autowired
    private TotalScoreCurrentService totalScoreCurrentService; //处理表13中考核模块数量
    @Autowired
    private ScoreUpdateService scoreUpdateService;   //移除模块后，批量更新学生成绩
    @Autowired
    private CollegeReportService collegeReportService;  //学院报告服务
    @Autowired
    private ReportAnswerService reportAnswerService;   //自定义报告
    @Autowired
    private CourseInfoService courseInfoService;//课程信息
    @Autowired
    private ArrangeClassService arrangeClassService;//课程安排表
    @Autowired
    private ModuleTestAnswerStuService moduleTestAnswerStuService;//模块测试和学生信息处理

    protected static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    /**
     * 列出所有模块
     *
     * @param model   传值
     * @param pageNum 分页
     * @return 列表页面
     */
    @RequestMapping(value = "/allModule", method = RequestMethod.GET)
    public String loadAllModel(Model model, HttpSession session,
                               @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {
        // 这是一个整数列表,用来存放所有的考核模块的实验模块id,在视图上用来判断此实验是否已经在考核中
//        List<Integer> check = kaoheModelService.inKaoheList();
//        model.addAttribute("checkList", check);

//        //这个是所有的实验模块
//        Page<ExpModel> a = expModelService.findModelList(pageNum);
//
//
////        List<ExpModel> b= null;
//        model.addAttribute("allKaohe", a);


//        User user = (User) session.getAttribute("admin");
//        logger.debug("user:>>"+user);

//        List<CourseInfo> courseInfoList =  courseInfoService.getclass_by_arrangeteacher(user.getId());
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("登陆用户信息:" + user);
        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(user.getId());
        model.addAttribute("arrangeInfoDTOs", arrangeInfoDTOs);

//        if(arrangeInfoDTOs.size()>0){
//            return "redirect:/kaohemodel/Module/"+arrangeInfoDTOs.get(0);
////            return "redirect:/kaohemodel/Module/1";
//        }else {
//            return "redirect:/Module/"+-1;
//        }
        boolean choose = false;
        model.addAttribute("Choose", choose);
        model.addAttribute("selected1", "/kaohemodel/allModule");
        model.addAttribute("selected2", "/kaohemodel/checkModule");
//        List <ExpModels> b = null;
//        for (ExpModel x:a){
//            ExpModels c = new ExpModels(x.getM_id(),
//                    x.getM_name(),
//                    x.getM_manager(),
//                    x.getM_type(),
//                    x.getClasshour(),
//                    x.getImageurl(),
//                    x.getIntroduce(),
//                    x.getPurpose(),
//                    x.getPrinciple(),
//                    x.getM_content(),
//                    x.getM_edata_intro(),
//                    x.getM_edataurl(),
//                    x.getM_step(),
//                    x.getM_inurl());
//
//            if (check.contains(x.getM_id())){
//                c.setStatus(true);
//            }else {
//                c.setStatus(false);
//            }
//            b.add(c);
//        }
        model.addAttribute("needPaging",false);
        model.addAttribute("path","/kaohemodel/allModule");


        return "kaohe/allModule";
    }


    @RequestMapping(value = "/Module/{arrangeId}", method = RequestMethod.GET)
    public String loadOneCourseModel(Model model,
                                     HttpSession session,
                                     @PathVariable int arrangeId,
                                     @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {
        model.addAttribute("path","/kaohemodel/Module/"+arrangeId);
        //所有的下拉列表数据
        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(1);
        model.addAttribute("arrangeInfoDTOs", arrangeInfoDTOs);

        //当前选择的安排表Id,用于判断按钮跳转连接,以及下拉列表回显
        model.addAttribute("selected", arrangeId);

        //判断是否选择了安排
        boolean choose = true;

        if (arrangeId == -1) {
            choose = false;
            model.addAttribute("Choose", choose);
            model.addAttribute("selected1", "/kaohemodel/allModule");
            model.addAttribute("selected2", "/kaohemodel/checkModule");
            return "kaohe/checkModule";
        }

        model.addAttribute("Choose", choose);
        model.addAttribute("selected1", "/kaohemodel/Module/" + arrangeId);
        model.addAttribute("selected2", "/kaohemodel/checkModule/" + arrangeId);
        // 这是一个整数列表,用来存放所有的考核模块的实验模块id,用来判断此实验是否已经在考核中
        List<Integer> check = kaoheModelService.inKaoheList(arrangeId);
//        model.addAttribute("checkList", check);

        //本安排的实验模块
        ArrangeClass arrangeClass = arrangeClassService.findById(arrangeId);
        Page<ExpModel> a = expModelService.findOneCourseModelList(arrangeClass.getCourseId(), pageNum);
        //设置是否已经移入了考核
        List<ExpModel> tempts = a.getContent();
        for (int i = 0; i < tempts.size(); i++) {
            ExpModel temp_exp = tempts.get(i);
            int temp_m_id = temp_exp.getM_id();
            boolean temp_flag = false;
            for (int j = 0; j < check.size(); j++) {
                if (check.get(j).equals(temp_m_id)) {
                    temp_flag = true;
                    break;
                }
            }
            temp_exp.setNeedKaohe(temp_flag);
        }
        model.addAttribute("allKaohe", a);


//        //查询此课程安排下的所有考核模块,用于判断每个模块是否在考核中
//        List<Integer> kaoHeModelId =kaoheModelService.findKaoheModelByArrangeId(arrangeId);
//        model.addAttribute("allKaoHeModelId",kaoHeModelId);


//        System.out.println("准备好了");

        if (a.getTotalPages()>0){
            model.addAttribute("needPaging",true);
        }else {
            model.addAttribute("needPaging",false);
        }

        return "kaohe/allModule";
    }

    /**
     * 列出所有考核模块
     *
     * @param model   传值
     * @param pageNum 分页
     * @return 页面
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/checkModule", method = RequestMethod.GET)
    public String list(Model model,
                       @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) throws JsonProcessingException {
        model.addAttribute("path","/kaohemodel/checkModule");
        // 所有的考核模块
//        Pageable pageable = PageRequest.of(pageNum, 5);
//        Page<KaoheModel> page = kaoheModelService.findAll(pageable);
        //判断是否选择了安排
        boolean choose = false;
        model.addAttribute("Choose", choose);

//        Page<KaoheModelAndExpInfoDTO> page = kaoheModelService.findAllKaoheModelAndExpInfoDTO(pageNum);
//
//        model.addAttribute("kaoheModelPageInfo", page);
//        System.out.println("page:" + page.getTotalElements());
//
//        //分页
//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println("json:" + mapper.writeValueAsString(page));

        //所有的下拉列表数据
        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(1);
        model.addAttribute("arrangeInfoDTOs", arrangeInfoDTOs);
        logger.debug("下拉列表数据>>>>>:" + arrangeInfoDTOs);

        model.addAttribute("selected1", "/kaohemodel/allModule");
        model.addAttribute("selected2", "/kaohemodel/checkModule");
        model.addAttribute("needPaging",false);
        return "kaohe/checkModule";
    }

    /**
     * 筛选考核模块
     *
     * @param model
     * @param arrangeId
     * @param pageNum
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/checkModule/{arrangeId}", method = RequestMethod.GET)
    public String listCheckModule(Model model,
                                  @PathVariable int arrangeId,
                                  @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) throws JsonProcessingException {
        model.addAttribute("path","/kaohemodel/checkModule/"+arrangeId);
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("登陆用户信息:" + user);
        //所有的下拉列表数据
        List<ArrangeInfoDTO> arrangeInfoDTOs = arrangeClassService.findArrangeInfoDTOByTeacherId(user.getId());
        model.addAttribute("arrangeInfoDTOs", arrangeInfoDTOs);
        logger.debug("下拉列表数据>>>>>:" + arrangeInfoDTOs);

        //回显当前所选的安排
        model.addAttribute("selected", arrangeId);


        //判断是否选择了安排
        boolean choose = true;

        if (arrangeId == -1) {
            choose = false;
            model.addAttribute("Choose", choose);
            model.addAttribute("selected1", "/kaohemodel/allModule");
            model.addAttribute("selected2", "/kaohemodel/checkModule");
            return "kaohe/checkModule";
        }

        model.addAttribute("Choose", choose);
        model.addAttribute("selected1", "/kaohemodel/Module/" + arrangeId);
        model.addAttribute("selected2", "/kaohemodel/checkModule/" + arrangeId);
        //本安排的考核实验模块
        Page<KaoheModelAndExpInfoDTO> page = kaoheModelService.findAllKaoheModelAndExpInfoDTOByArrangeId(arrangeId, pageNum);
        model.addAttribute("kaoheModelPageInfo", page);


        if (page.getTotalPages()>0){
            model.addAttribute("needPaging",true);
        }else {
            model.addAttribute("needPaging",false);
        }
        return "kaohe/checkModule";
    }


    /**
     * 移入考核
     */
    @RequestMapping(value = {"/{mid}/{arrangeId}/moveIn"}, method = RequestMethod.GET)
    public String add(@PathVariable int mid,
                      @PathVariable int arrangeId,
                      Model model) {

        // 获得此模块的信息
        ExpModel expModel = expModelService.findExpModelByID(mid);
        model.addAttribute("expInfo", expModel);
        KaoheModel kaoheModel = new KaoheModel();
        kaoheModel.setArrange_id(arrangeId);
        model.addAttribute("moveIn", kaoheModel);
        return "kaohe/moveIn";


    }

    /**
     * 移入考核
     */
    @RequestMapping(value = {"/{mid}/{arrangeId}/moveIn"}, method = RequestMethod.POST)
    public String add(@PathVariable int mid, @PathVariable int arrangeId, KaoheModel moveIn, String arrangeStart, String arrangeEnd) {

        logger.debug("arrangeStart>>>>>>>>>>>>" + arrangeStart);
        KaoheModel u = new KaoheModel();
//        ExpModel expModel = expModelService.findExpModelByID(mid);
        u.setArrange_id(arrangeId);
        u.setM_id(mid);
//        u.setM_id(expModel.getM_id());
//        u.setExperiment_name(expModel.getM_name());
//        u.setClass_hour(expModel.getClasshour());
        logger.debug(">>>>>>>>>>>>>>>>>>>>" + moveIn);
        u.setM_order(moveIn.getM_order());
        u.setM_scale(moveIn.getM_scale());
//        u.setShiyan_Purpose(expModel.getPurpose());
//        u.setShiyan_Types(expModel.getM_type());
        u.setM_test_baifenbi(moveIn.getM_test_baifenbi());
        u.setM_report_baifenbi(moveIn.getM_report_baifenbi());
        //增加时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date starsDate = null;
        Date endDate = null;
        try {
            if (arrangeStart != "") {
                starsDate = simpleDateFormat.parse(arrangeStart);
            }
            if (arrangeEnd != "") {
                endDate = simpleDateFormat.parse(arrangeEnd);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        u.setKaohe_starttime(starsDate);
        u.setKaohe_endtime(endDate);

        //从考核模块中取出整体测试百分比
        List<KaoheModel> kaoheModels = kaoheModelService.findAll();
        if (kaoheModels.size() > 0) {
            u.setKaohe_baifenbi(kaoheModels.get(0).getKaohe_baifenbi());
            u.setTest_baifenbi(kaoheModels.get(0).getTest_baifenbi());
        } else {
            u.setKaohe_baifenbi(0);
            u.setTest_baifenbi(0);
        }
//        logger.debug(u);
        kaoheModelService.add(u);
//        expModel.setNeedKaohe(true);
        //学生考核模块成绩记录表，只处理当期有考核权限的学生
        List<Student> studentslist = arrangeClassService.findStudentByarrangeID(arrangeId);
        for (Student i : studentslist) {
            logger.debug(String.valueOf(i));
            kaoHeModelScoreService.add(new KaoHeModelScore(u.getId(), i.getId(), 0, 0, u.getM_order(), u.getM_scale()));
            //更新表13中学生总表记录中考核模块数
            //todo 需要知道安排表id
            int arrageId = 0;
            TotalScoreCurrent totalScoreCurrent = totalScoreCurrentService.findTotalScoreCurrentByStuId2(i.getId(), arrageId);
            totalScoreCurrent.setKaoheNum(totalScoreCurrent.getKaoheNum() + 1);
            totalScoreCurrentService.add(totalScoreCurrent);
        }
        // 当期限定
        // 表13 考核项目数增加

//        expModelService.save(expModel);
//        logger.debug(">>>>>>>>>>>>add");

        //studentService.findStudentby

        for (Student temp_st : studentslist) {
            int studentid = temp_st.getId();
            //删除考核模块测试答案
            moduleTestAnswerStuService.deleteByStuIdModelId(mid, studentid);
            //删除学院版报告
            collegeReportService.deleteByStuIdModelId(mid, studentid);
            //删除自定义版答题报告
            reportAnswerService.deleteByStuIdModelId(mid, studentid);
        }

//旧版删除方式
//        kaoheModelService.deleteMTestAnswerByMid(mid);
//        //删除学院版报告
//        collegeReportService.deleteCollege(mid);
//        //删除自定义版答题报告
//        reportAnswerService.deleteReportAnswerByMid(mid);
        return "redirect:/kaohemodel/Module/" + arrangeId + "/";
    }

    /**
     * 修改考核配置
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String update(@PathVariable int id, Model model) {
        logger.debug("id:>>>>>>>>>>>>>>>>>>>>>>>" + id);
        KaoheModelAndExpInfoDTO kaoheModelAndExpInfoDTO = kaoheModelService.findKaoheModelAndExpInfoDTOByKaoheid(id);
//        KaoheModel kaoheModel = kaoheModelService.findById(id);
//        logger.debug(kaoheModel.toString());
        model.addAttribute("kaohemodel", kaoheModelAndExpInfoDTO);

        return "kaohe/kaoheupdate";
    }

    /**
     * 修改考核配置
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(@PathVariable int id, KaoheModel kaoheModel, String arrangeStart, String arrangeEnd) {
        KaoheModel u = kaoheModelService.findById(id);
//        u.setClass_hour(kaoheModel.getClass_hour());
        //模块id，从查询记录中得到
        u.setM_id(u.getM_id());
//        u.setExperiment_name(kaoheModel.getExperiment_name());
        u.setM_order(kaoheModel.getM_order());
        u.setM_scale(kaoheModel.getM_scale());
        u.setM_test_baifenbi(kaoheModel.getM_test_baifenbi());
        u.setM_report_baifenbi(kaoheModel.getM_report_baifenbi());
        u.setKaohe_baifenbi(kaoheModel.getKaohe_baifenbi());
        u.setTest_baifenbi(kaoheModel.getTest_baifenbi());

        //增加了修改时间
//        u.setKaohe_starttime(kaoheModel.getKaohe_starttime());
//        u.setKaohe_endtime(kaoheModel.getKaohe_endtime());
        //增加时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date starsDate = null;
        Date endDate = null;
        try {
            if (arrangeStart != "") {
                starsDate = simpleDateFormat.parse(arrangeStart);
            }
            if (arrangeEnd != "") {
                endDate = simpleDateFormat.parse(arrangeEnd);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        u.setKaohe_starttime(starsDate);
        u.setKaohe_endtime(endDate);

        kaoheModelService.add(u);
        //批量更新学生成绩
        scoreUpdateService.allStudentScoreUpdate2(u.getArrange_id());
        return "redirect:/kaohemodel/checkModule/" + u.getArrange_id();
    }

    /**
     * 移出考核
     */
    @RequestMapping(value = "/{id}/{arrangeId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable int id, @PathVariable int arrangeId) {

        // 遍历当期需要参加考核的学生 ,查到这个学生的,这个考核模块的成绩
        // 更新考核额数

        //新版不需要更新考核状态了
//        // 获得此考核模块的,实验模块
        int mid = kaoheModelService.findById(id).getM_id();
//        ExpModel expModel = expModelService.findExpModelByID(mid);
//        // 将实验模块的考核状态设置为不考核
//        expModel.setNeedKaohe(false);
//        expModelService.save(expModel);

//        //删除所有学生此模块的成绩
//        kaoheModelService.deleteByMid(mid);
        List<Student> studentslist = arrangeClassService.findStudentByarrangeID(arrangeId);
        for (Student temp_st : studentslist) {
            int studentid = temp_st.getId();
            //删除考核模块测试答案
            moduleTestAnswerStuService.deleteByStuIdModelId(mid, studentid);
            //删除学院版报告
            collegeReportService.deleteByStuIdModelId(mid, studentid);
            //删除自定义版答题报告
            reportAnswerService.deleteByStuIdModelId(mid, studentid);
        }
        // 删除表11中该考核模块
        kaoheModelService.delete(id);
        //批量更新学生成绩
        scoreUpdateService.allStudentScoreUpdate2(arrangeId);
//        kaoHeModelScoreService.deleteAllByKaohemId(id);
//        System.out.println("移出成功");
        return "redirect:/kaohemodel/checkModule/" + arrangeId;
    }


}


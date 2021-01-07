/**
 * 文件名：NewsInfoController.java
 * 修改人：xxxx
 * 修改时间：
 * 修改内容：新增
 */

package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.*;
import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto;
import com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto5;
import com.coolwen.experimentplatformv2.model.DTO.OverallScoreDto;
import com.coolwen.experimentplatformv2.service.*;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 首页显示的数据，后台管理系统中 首页-->平台公告页面的增删改查
 * @Author 朱治汶
 * @Version 1.0
 * @Date 2020/5/29 19:12
 */
@Controller
@RequestMapping(value = "/newsinfo")
@SessionAttributes("arrageId_sctudemo")
public class NewsInfoController {
    protected static final Logger logger = LoggerFactory.getLogger(NewsInfoController.class);
    @Autowired
    NewsInfoRepository newsInfoRepository;
    @Autowired
    ExpModelRepository expModelRepository;
    @Autowired
    KaoheModelRepository kaoheModelRepository;
    @Autowired
    NewsInfoService newsInfoService;
    @Autowired
    SetInfoService setInfoService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    private ClassService classService;
    @Autowired
    private CourseInfoService courseInfoService;
    @Autowired
    private EffectService effectService;
    @Autowired
    private CourseInfoRepository courseInfoRepository;
    @Autowired
    ArrangeClassService arrangeClassService;

    @Value("${web.count-path}")
    private String count;

    /**
     * 进入前端首页的接口
     *
     * @param model   存储需要展示在首页上的数据（虚拟仿真实验列表，推荐实验项目，虚拟实验平台公告，平台统计）
     * @param pageNum 分页
     * @return 进入首前端页
     */
    @GetMapping(value = "/newslist")
    public String newslist(Model model, @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {
        //查询所有公告信息
        Pageable pageable = PageRequest.of(pageNum, 10);
        Page<NewsInfo> page = newsInfoRepository.findAllorderby(pageable);
        model.addAttribute("newsPageInfo", page);
        //查询所有实验列表
        Pageable pageable1 = PageRequest.of(pageNum, 9);
        Page<ExpModel> page1 = expModelRepository.findAllexp(pageable1);
        model.addAttribute("allexp", page1);
        //轮播展示
        SetInfo setInfo = setInfoService.findById(1);
        String ids = setInfo.getSet_rotateimg();
        //数据库中存储为拼接（例：1,2,3,4），拆分后，查询图片存储路径并存入model
        String[] sid = ids.split(",");
        logger.debug("轮播数量:" + sid.length);

//        for (int i = 0; i < sid.length; i++) {
////            String imgurl = setInfoService.findexpimg(Integer.parseInt(sid[i]));
////            String imgurl = expModelRepository.findexpimg(Integer.parseInt(sid[i]));
//            try {
////                ExpModel expModel = expModelRepository.findById(Integer.parseInt(sid[i])).get();
////                model.addAttribute("img" + String.valueOf(i), expModel.getImageurl());
////                model.addAttribute("mid" + String.valueOf(i), expModel.getM_id());
//                Effect effect = effectService.findById(Integer.parseInt(sid[i]));
//                model.addAttribute("img" + String.valueOf(i), effect.getEffect_imgurl());
//                model.addAttribute("mid" + String.valueOf(i), effect.getId());
//
//            } catch (Exception e) {
//
//            }
//
//        }

//        List<ExpModel> expModels = new ArrayList<>();
//        for (int i = 0; i < sid.length; i++) {
//            try {
//                expModels.add(expModelRepository.findById(Integer.parseInt(sid[i])).get());
//            } catch (Exception e) {
//
//            }
//        }
//        model.addAttribute("expModels", expModels);
        List<Effect> effects = new ArrayList<>();
        for (int i = 0; i < sid.length; i++) {
            try {
                Effect effect = effectService.findById(Integer.parseInt(sid[i]));
                if (effect != null){
                    effects.add(effect);
                }
            } catch (Exception e) {

            }
        }
        logger.debug(">>"+effects);
        model.addAttribute("effects", effects);

        //课程展示
//        List<CourseInfo> courseInfos = courseInfoService.findAll();
        List<CourseInfoDto5> courseInfos = courseInfoRepository.findByCourseInfoDto3();
        for (CourseInfoDto5 courseInfo : courseInfos){
            int participantsNum = arrangeClassService.findNumberOfParticipants(courseInfo.getCourseInfoId());
            int completeNum = courseInfoService.findOneCourseInfoPassNum(courseInfo.getCourseInfoId());
            courseInfo.setNumberOfLearning(participantsNum+completeNum);
        }
        model.addAttribute("courseInfos",courseInfos);

        //平台统计
        //查询实验模块总数
        int modenum = (int) expModelRepository.count();
        //查询考核模块数量
        int kaohenum = (int) kaoheModelRepository.count();
        model.addAttribute("modenum",modenum);
        model.addAttribute("kaohenum",kaohenum);
        System.out.println(modenum);
        //查询平台总用户数
        int studentnum = (int) studentRepository.count();
        int teachernum = (int) teacherRepository.count();
        //校外人数
        int xiaowainum = (int) studentRepository.xiaowainum();
        model.addAttribute("xiaowainum",xiaowainum);
        model.addAttribute("usernum",studentnum+teachernum);
        //查询参与考核人数
        int studentmodel = newsInfoService.findAllmodelpeople();
        model.addAttribute("studentmodel",studentmodel);
        System.out.println(studentmodel);
        //查询通过考核人数
        int passpeople = newsInfoService.findAllPass();
        model.addAttribute("passpeople",passpeople);
        System.out.println(passpeople);

        //实验成绩统计
        //往期参与考核的全部学生
        int allpasspeople = newsInfoService.findAllpasspeople();
        //往期参与考核的优秀学生（85分以上）
        int excellent = newsInfoService.findExcellentpeople();
        //往期参与考核的中等学生（60分-85分）
        int qualified = newsInfoService.findQualifiedpeople();
        //往期参与考核的学生（60分以下）
        int unqualified = newsInfoService.findUnqualifiedpeople();
        model.addAttribute("allpasspeople",allpasspeople);
        model.addAttribute("excellentstu",excellent);
        model.addAttribute("qualifiedstu",qualified);
        model.addAttribute("unqualifiedstu",unqualified);

        //排行榜
        //前端页面展示10个要错位，修复后  删除两个 ”-2“ 即可
        List list = newsInfoService.findScoreRanking();
        OverallScoreDto[] overallScoreDtos = new OverallScoreDto[list.size()-2];
        for(int i=0;i<list.size()-2;i++) {
            Object[] obj = (Object[]) list.get(i);
            overallScoreDtos[i] = new OverallScoreDto(
                    String.valueOf(obj[0]),
                    String.valueOf(obj[1]),
                    String.valueOf(obj[2])
            );
        }

        logger.debug("overallScoreDtos:"+overallScoreDtos.toString());
        model.addAttribute("overallScoreDtos",overallScoreDtos);
        //访问量
        // 获取访问量信息
        String txtFilePath = count;
        Long count = Get_Visit_Count(txtFilePath);
        model.addAttribute("count", count);
        return "home_page/index_new";
    }


    //前端实验大厅入口
    @GetMapping(value = "/shiyan/{courInforId}")
//    @ResponseBody
    public String model(Model model, @PathVariable int courInforId, HttpSession session) {
        logger.debug("shiyan接口进入");
//        Map<Object, Object> map = CasUtils.getUserInfo(SecurityUtils.getSubject().getSession());
//        String comsys_role = (String) map.get("comsys_role");
//        logger.debug("角色为:" + comsys_role);
//        String number = (String) map.get("comsys_student_number");
//        logger.debug("学号为:" + number);
//        Student student = studentRepository.findclassStudentByStuXuehao(number);
//        logger.debug("student信息:" + student);
//        session.setAttribute("student", student);
////        Student student = (Student) session.getAttribute("student");
//        //暂时做了修改，如果没有登录，跳转到登录页
//        if (comsys_role.contains("ROLE_STUDENT")) {
        //获取登陆学生的信息
        Student student = (Student) SecurityUtils.getSubject().getSession().getAttribute("student");
        logger.debug("登陆信息:" + student);
        ClassModel classModel = classService.findClassById(student.getClassId());
        CourseInfoDto courseInfoDto = courseInfoService.findByCourseInfoIdAndClassId(courInforId, classModel.getClassId());
        logger.debug("课程信息:" + courseInfoDto);
        session.setAttribute("arrageId_sctudemo", courseInfoDto.getArrageClassId());
        model.addAttribute("disMid", false);
        model.addAttribute("fdsaf", "false");
        model.addAttribute("jiekou","/expmodel/alltestModel");
        //-----------------------------------------------------------------------------------------------------------------

//        return String.valueOf(modelList);
        return "kuangjia/shiyan";
//        } else {
//            //是老师就跳转到实验管理界面
//            return "redirect:/learning/kuangjia";
//        }

    }

    //前端首页，点击公告，查看详情
    @GetMapping(value = "/noticeDetails/{id}")
    public String noticedetails(@PathVariable int id, Model model) {
        //查询对应id的整条数据
        NewsInfo newsInfo = newsInfoService.findById(id);
        model.addAttribute("newsInfo", newsInfo);
        //设置点击次数+1
        newsInfo.setDic_num(newsInfo.getDic_num() + 1);
        //更新数据
        newsInfoService.add(newsInfo);
        return "home_page/noticeDetails";
    }

    //前端首页 点击查看更多
    @GetMapping(value = "more")
    public String more(Model model, @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 12);
        Page<NewsInfo> page = newsInfoRepository.findAllorderby(pageable);
        model.addAttribute("newsPageInfo", page);
        return "home_page/notice_more";
    }


    public static Long Get_Visit_Count(String txtFilePath) {
        try {
            File file = new File(txtFilePath);
            if (!file.exists()) {
                file.createNewFile();
                //写入相应的文件
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(txtFilePath), "UTF-8"));
                Long count = Long.valueOf("0");
                out.write(String.valueOf(count));
                //清楚缓存
                out.flush();
                //关闭流
                out.close();
            }
            //读取文件(字符流)
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(txtFilePath), "UTF-8"));
            //循环读取数据
            String str = null;
            StringBuffer content = new StringBuffer();
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            //关闭流
            in.close();
            //logger.debug(content);
            // 解析获取的数据
            Long count = Long.valueOf(content.toString());
            count++; // 访问量加1
            //写入相应的文件
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(txtFilePath), "UTF-8"));
            out.write(String.valueOf(count));
            //清楚缓存
            out.flush();
            //关闭流
            out.close();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}

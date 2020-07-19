package com.coolwen.experimentplatformv2.controller.HomepagesettingController;

import com.coolwen.experimentplatformv2.dao.EffectRepository;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.Effect;
import com.coolwen.experimentplatformv2.service.CourseInfoService;
import com.coolwen.experimentplatformv2.service.EffectService;
import com.coolwen.experimentplatformv2.service.NewsInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 朱治汶
 * @version 1.0
 * @className LearningeffectrontController
 * @description TODO
 * @date 2020/7/17 22:07
 **/
@Controller
@RequestMapping(value = "/learningfront")
public class LearningeffectfrontController {
    @Autowired
    EffectRepository effectRepository;   //学习效果的dao层
    @Autowired
    EffectService effectService;        //学习效果的servi层
    @Autowired
    NewsInfoService newsInfoService;
    @Autowired
    CourseInfoService courseInfoService;

    FileUploadController fileUploadController =new FileUploadController();  //上传文件

    protected static final Logger logger = LoggerFactory.getLogger(LearningeffectController.class);

    /**
     * 进入前端首页的界面
     * @param model 存储前端学习效果页面的优秀实验报告展示的数据
     * @param pageNum 分页页数
     * @return 进入到前端前端学习效果页面
     */
//    @GetMapping(value = "/learningList")
//    public String learningList(Model model, @RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum){
//        //查询全部优秀实验报告数据
//        Pageable pageable = PageRequest.of(pageNum,500);
//        Page<Effect> page = effectRepository.findAll(pageable);
//        model.addAttribute("learningPageInfo",page);
//        //往期参与考核的全部学生
//        int allpasspeople = newsInfoService.findAllpasspeople();
//        //往期参与考核的优秀学生（85分以上）
//        int excellent = newsInfoService.findExcellentpeople();
//        //往期参与考核的优秀学生（60分-85分）
//        int qualified = newsInfoService.findQualifiedpeople();
//        //往期参与考核的优秀学生（60分以下）
//        int unqualified = newsInfoService.findUnqualifiedpeople();
//
//        if (allpasspeople == 0){
//            allpasspeople = 1;
//        }
//        // 创建一个数值格式化对象
//        NumberFormat numberFormat = NumberFormat.getInstance();
//        // 设置精确到小数点后2位
//        numberFormat.setMaximumFractionDigits(2);
//        String excellentstu = numberFormat.format((float) excellent / (float) allpasspeople * 100) +"%";
//        String qualifiedstu = numberFormat.format((float) qualified / (float) allpasspeople * 100) +"%";
//        String unqualifiedstu = numberFormat.format((float) unqualified / (float) allpasspeople * 100) +"%";
//
//        model.addAttribute("excellentstu",excellentstu);
//        model.addAttribute("qualifiedstu",qualifiedstu);
//        model.addAttribute("unqualifiedstu",unqualifiedstu);
//        return "home_page/study_situation";
//    }

    @GetMapping(value = "/learningList")
    public String learningList(Model model){

        List<CourseInfo> courseInfos = courseInfoService.findAll();
        model.addAttribute("courseInfos",courseInfos);
        boolean visble = true;
        if (courseInfos.size() == 0){
            visble = false;
            model.addAttribute("visble",visble);
            return "home_page/teachers";
        }
        model.addAttribute("visble",visble);
        int id = courseInfos.get(0).getId();

        List<Effect> effects = effectService.findByCourseId(id);
        model.addAttribute("effects",effects);

        model.addAttribute("courseId",id);

        //往期参与考核的全部学生
        int allpasspeople = newsInfoService.findAllpasspeopleByCourseId(id);
        //往期参与考核的优秀学生（85分以上）
        int excellent = newsInfoService.findExcellentpeopleByCourseId(id);
        //往期参与考核的优秀学生（60分-85分）
        int qualified = newsInfoService.findQualifiedpeopleByCourseId(id);
        //往期参与考核的优秀学生（60分以下）
        int unqualified = newsInfoService.findUnqualifiedpeopleByCourseId(id);
        model.addAttribute("allpasspeople",allpasspeople);
        model.addAttribute("excellentstu",excellent);
        model.addAttribute("qualifiedstu",qualified);
        model.addAttribute("unqualifiedstu",unqualified);
        return "home_page/study_situation";
    }

    @GetMapping(value = "/learningList/{id}")
    public String learningList1(Model model,@PathVariable int id){
        boolean visble = true;
        model.addAttribute("visble",visble);

        List<CourseInfo> courseInfos = courseInfoService.findAll();
        model.addAttribute("courseInfos",courseInfos);

        List<Effect> effects = effectService.findByCourseId(id);
        model.addAttribute("effects",effects);

        model.addAttribute("courseId",id);

        //往期参与考核的全部学生
        int allpasspeople = newsInfoService.findAllpasspeopleByCourseId(id);
        //往期参与考核的优秀学生（85分以上）
        int excellent = newsInfoService.findExcellentpeopleByCourseId(id);
        //往期参与考核的优秀学生（60分-85分）
        int qualified = newsInfoService.findQualifiedpeopleByCourseId(id);
        //往期参与考核的优秀学生（60分以下）
        int unqualified = newsInfoService.findUnqualifiedpeopleByCourseId(id);
        model.addAttribute("allpasspeople",allpasspeople);
        model.addAttribute("excellentstu",excellent);
        model.addAttribute("qualifiedstu",qualified);
        model.addAttribute("unqualifiedstu",unqualified);
        return "home_page/study_situation";
    }

    /**
     * 查看优秀实验报告（详情页）
     * @param id 对应优秀实验报告的id号
     * @param model 存储查询到对应id号的优秀实验报告的内容
     * @return 返回到优秀实验报告的详情页
     */
    @GetMapping(value = "/effectDetails/{id}")
    public String effectDetails(@PathVariable int id, Model model){
        //查询对应id的优秀实验报告的整条数据
        Effect effect = effectService.findById(id);
        effect.setDic_num(effect.getDic_num()+1);
        effectService.add(effect);
        model.addAttribute("effect",effect);
        return "home_page/effectDetails";
    }
}

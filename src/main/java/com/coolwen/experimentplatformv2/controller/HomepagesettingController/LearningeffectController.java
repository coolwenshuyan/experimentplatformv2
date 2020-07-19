/**
 * 文件名：LearningeffectController.java
 * 修改人：xxxx
 * 修改时间：
 * 修改内容：新增
 */

package com.coolwen.experimentplatformv2.controller.HomepagesettingController;

import com.coolwen.experimentplatformv2.controller.TeacherController;
import com.coolwen.experimentplatformv2.dao.EffectRepository;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.Effect;
import com.coolwen.experimentplatformv2.model.Teacher;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.CourseInfoService;
import com.coolwen.experimentplatformv2.service.EffectService;
import com.coolwen.experimentplatformv2.service.NewsInfoService;
import com.coolwen.experimentplatformv2.utils.FileUploadUtil;
import com.coolwen.experimentplatformv2.utils.GetServerRealPathUnit;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

/**
 *@Description 主要为后台管理系统中首页-->学习效果 的增删改查，前端学习效果页面优秀实验报告展示
 *@Author 朱治汶
 *@Version 1.0
 *@Date 2020/5/29 16:11
 */
@Controller
@RequestMapping(value = "/learning")
public class LearningeffectController {

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
     * 进入后台管理系统的接口
     * @return 返回到后台框架界面
     */
    @GetMapping(value = "kuangjia")
    public String kuangjia(){
        return "kuangjia/frame";
    }


    /**
     * 后台管理系统页面-->首页-->学习效果页面
     * @param model 储存优秀实验报告所有数据
     * @param pageNum 分页
     * @return 返回到学习效果列表展示页面
     */
    @GetMapping(value = "/list")
    public String LearningeffectList(Model model, HttpSession session,
                                     @RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("user:>>"+user);
        //查询优秀实验报告所有数据
        Pageable pageable = PageRequest.of(pageNum,10);
//        Page<Effect> page = effectRepository.findAll(pageable);
        Page<Effect> page = effectService.findAllByUid(user.getId(),pageable);
        model.addAttribute("learningPageInfo",page);

        List<CourseInfo> courseInfoList =  courseInfoService.getclassByCharge(user.getId());
        model.addAttribute("courseInfoList",courseInfoList);
        return "shouye/student_level";
    }


    @GetMapping("/{courseId}/list")
    public String LearningeffectList1(Model model,
                                      HttpSession session,
                                      @PathVariable int courseId,
                                      @RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum){
        Page<Effect> page = effectService.findAllByCourseId(pageNum,courseId);
        logger.debug("courseId:>>>>>>>>>>>>>>"+courseId);
        model.addAttribute("learningPageInfo",page);

        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        logger.debug("user:>>"+user);
        List<CourseInfo> courseInfoList =  courseInfoService.getclassByCharge(user.getId());
        model.addAttribute("courseInfoList",courseInfoList);
        return "shouye/student_level";
    }

    /**
     * 点击学习效果页面添加按钮，来到添加页面
     * @return 返回到学习效果添加页面
     */
    @GetMapping(value = "/add")
    public String LearningeffectAdd(HttpSession session,Model model){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        List<CourseInfo> courseInfoList =  courseInfoService.getclassByCharge(user.getId());
        model.addAttribute("courseInfoList",courseInfoList);
        return "shouye/study_add";
    }


    /**
     * 学习效果添加页面，完成添加操作
     * @param effect 从前端返回的参数（effect_content,effect_name,effect_person）
     * @param attachs  从前端返回的图片文件
     * @param req  返回的文件信息
     * @return 重定向到学习效果页面
     */
    @PostMapping(value = "/add")
    public String add(Effect effect, @RequestParam("attachs") MultipartFile[] attachs, HttpServletRequest req){
        //设置点击次数和创建时间
        effect.setDic_num(0);
        effect.setDic_datetime(new Date());
        //储存图片，并将图片路径储存到数据库
        String realpath = GetServerRealPathUnit.getPath("static/upload/");
//       logger.debug("realPath:" + realpath);
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                continue;
            }
            //图片验证重命名
            String picName = FileUploadUtil.picRename(attach.getContentType());
            String path = realpath + "/" + picName;
//            logger.debug(path);
            File f = new File(path);
//            user.setImg(picName);
            effect.setEffect_imgurl(picName);
            try {
                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将信息储存到数据库
        effectService.add(effect);
        return "redirect:/learning/list";
    }

    /**
     * 点击学习效果中修改按钮，进入修改页面
     * @param id 对应需要修改数据的id
     * @param model 储存需要回显到修改页面的数据
     * @return 返回到修改页面
     */
    @GetMapping(value = "/{id}/update")
    public String update(@PathVariable int id, Model model,HttpSession session){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        List<CourseInfo> courseInfoList =  courseInfoService.getclassByCharge(user.getId());
        model.addAttribute("courseInfoList",courseInfoList);

        //查询到id所对应的整条数据
        Effect effect = effectService.findById(id);
        model.addAttribute("effect",effect);
        return "shouye/study_update";
    }

    /**
     * 完成修改操作
     * @param id 被修改数据的id
     * @param effect 被修改的数据参数（effect_content,effect_name,effect_person）
     * @param attachs 修改的图片文件
     * @param req 返回的文件信息
     * @return 重定向到学习效果页面
     */
    @PostMapping(value = "/{id}/update")
    public String update(@PathVariable int id, Effect effect, @RequestParam("attachs") MultipartFile[] attachs, HttpServletRequest req){
        effect.setId(id);
        effect.setDic_datetime(new Date());
        //储存图片，并将图片路径储存到数据库
        String realpath = GetServerRealPathUnit.getPath("static/upload/");
//       logger.debug("realPath:" + realpath);
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                effect.setEffect_imgurl(effectService.findById(id).getEffect_imgurl());
                continue;
            }
            //图片验证重命名
            String picName = FileUploadUtil.picRename(attach.getContentType());
            String path = realpath + "/" + picName;
//            logger.debug(path);
            File f = new File(path);
//            user.setImg(picName);
            effect.setEffect_imgurl(picName);
            try {
                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将信息储存到数据库
        effectService.add(effect);
        logger.debug(effectService.findById(id).toString());
        logger.debug("修改成功");
        return "redirect:/learning/list";
    }

    /**
     * 删除单条优秀实验报告信息
     * @param id 对应优秀实验报告id
     * @return 重定向到学习效果页面
     */
    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable int id){
        //执行删除操作
        effectService.delete(id);
        return "redirect:/learning/list";
    }
}

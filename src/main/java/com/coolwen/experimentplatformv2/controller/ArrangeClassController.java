package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.ArrangeClassRepository;
import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.ClassModel;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 朱治汶
 * @version 1.0
 * @date 2020/7/4 23:50
 **/
@Controller
@RequestMapping(value = "/arrangeclass")
public class ArrangeClassController {

    @Autowired
    ArrangeClassService arrangeClassService;
    @Autowired
    ArrangeClassRepository arrangeClassRepository;
    @Autowired
    UserService userService;
    @Autowired
    CourseInfoService courseInfoService;
    @Autowired
    ClazzService classService;


    @GetMapping(value = "/list")
    public String courseInfoList(Model model, @RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum){
        //查询课程安排表所有数据
        Pageable pageable = PageRequest.of(pageNum,10);
        Page<ArrangeClassDto> page = arrangeClassService.findByAll(pageable);
        model.addAttribute("ArrangeClassDto",page);


//        List<CourseInfo> courseInfoList1 = courseInfoService.getclass_by_arrangeteacher(4);
//
//        for (int i = 0; i <courseInfoList1.size() ; i++) {
//            logger.debug(">>>>>>>>>>>>>>>>>>>>>>"+courseInfoList1.get(i).getTeacherId());
//        }

//        List<ClassModel> courseInfoList1 = courseInfoService.getclass_by_arrangecourseid(1,1);
//
//        for (int i = 0; i <courseInfoList1.size() ; i++) {
//            logger.debug(">>>>>>>>>>>>>>>>>>>>>>"+courseInfoList1.get(i).getClassName());
//        }

        List<CourseInfo> courseInfoList = courseInfoService.list();
        model.addAttribute("courseInfoList",courseInfoList);

        List<User> userList = userService.list();
        model.addAttribute("userList",userList);

        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList",classList);
        return "jichu/timePlan_management";
    }

    @PostMapping(value = "/mhlist")
    public String courseInfoList1(Model model, String courseName,String teacherName,String className,
                                  @RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum){
        //查询课程安排表所有数据
//        Pageable pageable = PageRequest.of(pageNum,10);
        Page<ArrangeClassDto> page = arrangeClassService.findBycidAndtidAndclaidLike(pageNum,courseName,teacherName,className);
        model.addAttribute("ArrangeClassDto",page);

        List<CourseInfo> courseInfoList = courseInfoService.list();
        model.addAttribute("courseInfoList",courseInfoList);

        List<User> userList = userService.list();
        model.addAttribute("userList",userList);

        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList",classList);
        return "jichu/timePlan_management";
    }


    @GetMapping(value = "/add")
    public String courseInfoAdd(Model model){
        List<CourseInfo> courseInfoList = courseInfoService.list();
        model.addAttribute("courseInfoList",courseInfoList);

        List<User> userList = userService.list();
        model.addAttribute("userList",userList);

        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList",classList);
        return "jichu/timePlan_new";
    }


    @PostMapping(value = "/add")
    public String add(int courseId,int teacherId,int classId,String arrangeStart,String arrangeEnd,String skAddress,Model model){

        ArrangeClass a = arrangeClassService.findByCourseIdAndTeacherIdAndClassId(courseId,teacherId,classId);
        if (a != null){
            List<CourseInfo> courseInfoList = courseInfoService.list();
            model.addAttribute("courseInfoList",courseInfoList);

            List<User> userList = userService.list();
            model.addAttribute("userList",userList);

            List<ClassModel> classList = classService.findCurrentClass();
            model.addAttribute("classList",classList);
            model.addAttribute("msg","添加课程安排失败，已经有该课程安排!!!");
            return "jichu/timePlan_new";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date starsDate = null;
        Date endDate = null;
        try {
            if(arrangeStart != ""){
                starsDate = simpleDateFormat.parse(arrangeStart);
            }
            if(arrangeEnd != ""){
                endDate = simpleDateFormat.parse(arrangeEnd);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrangeClass arrangeClass = new ArrangeClass();
        arrangeClass.setCourseId(courseId);
        arrangeClass.setTeacherId(teacherId);
        arrangeClass.setClassId(classId);
        arrangeClass.setArrangeStart(starsDate);
        arrangeClass.setArrangeEnd(endDate);
        arrangeClass.setSkAddress(skAddress);

        //将信息储存到数据库
        arrangeClassService.add(arrangeClass);
        return "redirect:/arrangeclass/list";
    }


    @GetMapping(value = "/{id}/update")
    public String update(@PathVariable int id, Model model){
        //查询到id所对应的整条数据
        ArrangeClass arrangeClass = arrangeClassService.findById(id);
        model.addAttribute("arrangeClass",arrangeClass);

        List<CourseInfo> courseInfoList = courseInfoService.list();
        model.addAttribute("courseInfoList",courseInfoList);

        List<User> userList = userService.list();
        model.addAttribute("userList",userList);

        List<ClassModel> classList = classService.findCurrentClass();
        model.addAttribute("classList",classList);
        return "jichu/timePlan_update";
    }


    @PostMapping(value = "/{id}/update")
    public String update(@PathVariable int id,int courseId,int teacherId,int classId,String arrangeStart,String arrangeEnd,String skAddress,Model model){

        ArrangeClass a = arrangeClassService.findByCourseIdAndTeacherIdAndClassId(courseId,teacherId,classId);
        //查询到id所对应的整条数据
        ArrangeClass arrangeClass1 = arrangeClassService.findById(id);
      if (a != null){
          if (!(arrangeClass1.getCourseId() == a.getCourseId() && arrangeClass1.getTeacherId()==a.getTeacherId() && arrangeClass1.getClassId()==a.getClassId())){
              model.addAttribute("arrangeClass",arrangeClass1);

                List<CourseInfo> courseInfoList = courseInfoService.list();
                model.addAttribute("courseInfoList",courseInfoList);

                List<User> userList = userService.list();
                model.addAttribute("userList",userList);

                List<ClassModel> classList = classService.findCurrentClass();
                model.addAttribute("classList",classList);
                model.addAttribute("msg","添加课程安排失败，已经有该课程安排!!!");
                return "jichu/timePlan_update";
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date starsDate = null;
        Date endDate = null;
        try {
            if(arrangeStart != ""){
                starsDate = simpleDateFormat.parse(arrangeStart);
            }
            if(arrangeEnd != ""){
                endDate = simpleDateFormat.parse(arrangeEnd);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrangeClass arrangeClass = new ArrangeClass();
        arrangeClass.setId(id);
        arrangeClass.setCourseId(courseId);
        arrangeClass.setTeacherId(teacherId);
        arrangeClass.setClassId(classId);
        arrangeClass.setArrangeStart(starsDate);
        arrangeClass.setArrangeEnd(endDate);
        arrangeClass.setSkAddress(skAddress);

        //将信息储存到数据库
        arrangeClassService.add(arrangeClass);
        return "redirect:/arrangeclass/list";
    }


    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable int id){
        //执行删除操作
        arrangeClassService.delete(id);
        return "redirect:/arrangeclass/list";
    }
}

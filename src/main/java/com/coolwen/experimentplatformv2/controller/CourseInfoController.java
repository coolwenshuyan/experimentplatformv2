package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.CourseInfoRepository;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.CourseInfoService;
import com.coolwen.experimentplatformv2.service.UserService;
import com.coolwen.experimentplatformv2.utils.FileUploadUtil;
import com.coolwen.experimentplatformv2.utils.GetServerRealPathUnit;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 朱治汶
 * @version 1.0
 * @date 2020/7/4 23:48
 **/

@Controller
@RequestMapping(value = "/courseinfo")
public class CourseInfoController {

    @Autowired
    CourseInfoService courseInfoService;
    @Autowired
    CourseInfoRepository courseInfoRepository;
    @Autowired
    UserService userService;



    @GetMapping(value = "/list")
    public String courseInfoList(Model model, @RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum){

        Pageable pageable = PageRequest.of(pageNum,10);
        Page<CourseInfo> page = courseInfoRepository.findAll(pageable);
        model.addAttribute("courseInfos",page);

        List<User> userList = userService.list();
        model.addAttribute("userList",userList);
        return "jichu/timetable_management";
    }


    @GetMapping(value = "/add")
    public String courseInfoAdd(Model model){
        List<User> userList = userService.list();
        model.addAttribute("userList",userList);
        return "jichu/timetable_new";
    }


    @PostMapping(value = "/add")
    public String add(CourseInfo courseInfo, @RequestParam("attachs") MultipartFile[] attachs, HttpServletRequest req){
        //储存图片，并将图片路径储存到数据库
        String realpath = GetServerRealPathUnit.getPath("static/upload/");
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                continue;
            }
            //图片验证重命名
            String picName = FileUploadUtil.picRename(attach.getContentType());
            String path = realpath + "/" + picName;
            File f = new File(path);
            courseInfo.setCourseImgurl(picName);
            try {
                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将信息储存到数据库
        courseInfoService.add(courseInfo);
        return "redirect:/courseinfo/list";
    }


    @GetMapping(value = "/{id}/update")
    public String update(@PathVariable int id, Model model){
        //查询到id所对应的整条数据
        CourseInfo courseInfo = courseInfoService.findById(id);
        model.addAttribute("courseInfo",courseInfo);

        List<User> userList = userService.list();
        model.addAttribute("userList",userList);
        return "jichu/timetable_update";
    }


    @PostMapping(value = "/{id}/update")
    public String update(@PathVariable int id, CourseInfo courseInfo, @RequestParam("attachs") MultipartFile[] attachs, HttpServletRequest req){

        //储存图片，并将图片路径储存到数据库
        String realpath = GetServerRealPathUnit.getPath("static/upload/");
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                courseInfo.setCourseImgurl(courseInfoService.findById(id).getCourseImgurl());
                continue;
            }
            //图片验证重命名
            String picName = FileUploadUtil.picRename(attach.getContentType());
            String path = realpath + "/" + picName;
            File f = new File(path);
            courseInfo.setCourseImgurl(picName);
            try {
                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将信息储存到数据库
        courseInfoService.add(courseInfo);
        return "redirect:/courseinfo/list";
    }


    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable int id){
        //执行删除操作
        courseInfoService.delete(id);
        return "redirect:/courseinfo/list";
    }
}

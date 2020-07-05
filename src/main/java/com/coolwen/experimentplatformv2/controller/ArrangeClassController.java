package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.ArrangeClassRepository;
import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.ArrangeClassService;
import com.coolwen.experimentplatformv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping(value = "/list")
    public String courseInfoList(Model model, @RequestParam(defaultValue = "0", required=true,value = "pageNum")  Integer pageNum){
        //查询优秀实验报告所有数据
        Pageable pageable = PageRequest.of(pageNum,10);
        Page<ArrangeClassDto> page = arrangeClassService.findByAll(pageable);
        model.addAttribute("arrangeclass",page);
        return "jichu/timePlan_management";
    }


    @GetMapping(value = "/add")
    public String courseInfoAdd(Model model){
        List<User> userList = userService.list();
        model.addAttribute("userList",userList);
        return "jichu/timePlan_new";
    }


    @PostMapping(value = "/add")
    public String add(ArrangeClass arrangeClass){

        //将信息储存到数据库
        arrangeClassService.add(arrangeClass);
        return "redirect:/arrangeclass/list";
    }


    @GetMapping(value = "/{id}/update")
    public String update(@PathVariable int id, Model model){
        //查询到id所对应的整条数据
        ArrangeClass arrangeClass = arrangeClassService.findById(id);
        model.addAttribute("arrangeClass",arrangeClass);

        List<User> userList = userService.list();
        model.addAttribute("userList",userList);
        return "jichu/timePlan_update";
    }


    @PostMapping(value = "/{id}/update")
    public String update(@PathVariable int id, ArrangeClass arrangeClass){

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

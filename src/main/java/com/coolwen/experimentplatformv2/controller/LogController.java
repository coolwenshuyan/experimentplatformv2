package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.ExpLogDao;
import com.coolwen.experimentplatformv2.model.DTO.StudentLogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Artell
 * @version 2021/1/6 19:05
 */

@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    public ExpLogDao expLogDao;
    protected static final Logger logger = LoggerFactory.getLogger(LogController.class);
    @GetMapping("/list")
    public String logList(Model model,
     @RequestParam(value = "pageNum", required = true, defaultValue = "0") int pageNum){
        Pageable pageable = PageRequest.of(pageNum,30);
        Page<StudentLogDTO> loglist = expLogDao.pageStudentLogDTO(pageable);
        model.addAttribute("logList",loglist);
        model.addAttribute("pageNum",pageNum);
        return "student/log_list";
    }
}

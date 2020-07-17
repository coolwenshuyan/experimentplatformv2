package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.dao.*;
import com.coolwen.experimentplatformv2.model.NewsInfo;
import com.coolwen.experimentplatformv2.service.NewsInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author 朱治汶
 * @version 1.0
 * @className NewsInfobackController
 * @description TODO
 * @date 2020/7/17 22:42
 **/
@Controller
@RequestMapping(value = "/newsinfoback")
public class NewsInfoBackController {

    protected static final Logger logger = LoggerFactory.getLogger(NewsInfoBackController.class);
    @Autowired
    NewsInfoRepository newsInfoRepository;
    @Autowired
    NewsInfoService newsInfoService;


    //后台管理系统 首页-->平台公告列表
    @GetMapping(value = "/list")
    public String list(Model model, @RequestParam(defaultValue = "0", required = true, value = "pageNum") Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 6);
        Page<NewsInfo> page = newsInfoRepository.findAll(pageable);
        model.addAttribute("newsPageInfo", page);
        return "shouye/notice";
    }

    //后台管理系统页面 进入公告添加
    @GetMapping(value = "/add")
    public String add() {
        return "shouye/notice_add";
    }

    /**
     * 完成公告添加
     *
     * @param newsInfo 存储前端的返回的传值（content，news_name，news_person）
     * @return 重定向到平台公告页面
     */
    @PostMapping(value = "/add")
    public String add(NewsInfo newsInfo) {
        //设置时间和点击次数
        newsInfo.setDic_datetime(new Date());
        newsInfo.setDic_num(0);
        //存储到数据库中
        newsInfoService.add(newsInfo);
        return "redirect:/newsinfoback/list";
    }

    //进入公告修改
    @GetMapping(value = "/{id}/update")
    public String update(@PathVariable int id, Model model) {
        //查询id对应的整条数据，存入model，返回给前端
        NewsInfo newsInfo = newsInfoService.findById(id);
        model.addAttribute("newsInfo", newsInfo);
        return "shouye/notice_update";
    }

    /**
     * 完成公告修改
     *
     * @param id       公告id
     * @param newsInfo 存储前端的返回的传值（content，news_name，news_person）
     * @return 重定向到平台公告页面
     */
    @PostMapping(value = "/{id}/update")
    public String update(@PathVariable int id, NewsInfo newsInfo) {
        NewsInfo newsInfo1 = newsInfoService.findById(id);
        newsInfo.setId(id);
        newsInfo.setDic_datetime(newsInfo1.getDic_datetime());
        newsInfo.setDic_num(newsInfo1.getDic_num());
        newsInfoService.add(newsInfo);
        return "redirect:/newsinfoback/list";
    }

    //公告删除
    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable int id) {
        newsInfoService.delete(id);
        return "redirect:/newsinfoback/list";
    }
}

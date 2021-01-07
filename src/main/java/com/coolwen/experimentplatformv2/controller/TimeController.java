package com.coolwen.experimentplatformv2.controller;

/**
 * @author Artell
 * @version 2020/11/5 20:07
 */

import com.coolwen.experimentplatformv2.dao.LearningDao;
import com.coolwen.experimentplatformv2.model.LearningTime;
import com.coolwen.experimentplatformv2.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Description
 * @Author sgl
 * @Date 2018-05-15 14:04
 */
@Controller
public class TimeController {
    @Autowired
    public LearningDao learningDao;

    @PostMapping("/learningend")
    @ResponseBody
    public void upload(HttpSession session){
    System.out.println("用户已经关闭页面");

        int sid = 0;
        try {
            sid = ((Student) session.getAttribute("student")).getId();
            LearningTime learningTime = learningDao.findBySid(sid);
            if (learningTime.getOnline()){
                learningTime.setOnline(false);
                learningTime.setPostSetTime(new Date());

                int minutes = (int) ((new Date().getTime() - learningTime.getLogInTime().getTime()) / (1000 * 60));
                if (minutes>300){
                    minutes = 300;
                }
                learningTime.setTotalTime(minutes+learningTime.getTotalTime());
                learningDao.save(learningTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        session.invalidate();
    }
}



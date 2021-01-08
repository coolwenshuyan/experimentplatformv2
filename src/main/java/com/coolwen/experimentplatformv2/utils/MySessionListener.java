package com.coolwen.experimentplatformv2.utils;

/**
 * @author Artell
 * @version 2021/1/3 14:44
 */


import com.coolwen.experimentplatformv2.dao.LearningDao;
import com.coolwen.experimentplatformv2.model.LearningTime;
import com.coolwen.experimentplatformv2.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.Date;

@WebListener
public class MySessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    public static final Logger logger= LoggerFactory.getLogger(MySessionListener.class);

    @Autowired
    public LearningDao learningDao;

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        logger.info("--attributeAdded--");
        HttpSession session=httpSessionBindingEvent.getSession();
        logger.info("key----:"+httpSessionBindingEvent.getName());
        logger.info("value---:"+httpSessionBindingEvent.getValue());
        if (httpSessionBindingEvent.getName().equals("student")){
            logger.info("Session监听到学生登录,开始计算学习时长" + (Student)httpSessionBindingEvent.getSession().getAttribute("student"));
            int sid = ((Student)httpSessionBindingEvent.getValue()).getId();
            logger.info("sid>>>"+sid);
            LearningTime learningTime = learningDao.findBySid(sid);
            if (learningTime == null){
                logger.info("新建此学生学习时长记录");
                learningTime = new LearningTime();
                learningTime.setOnline(false);
            }
            logger.info("learningTime>>>"+learningTime);
            if (learningTime.getOnline()){
                logger.info("上次退出异常");
            }
            logger.info("上次退出正常");
            Date logInTime = new Date();
            learningTime.setLogInTime(logInTime);
            learningTime.setSid(sid);
            learningTime.setOnline(true);
            learningTime.setPostSetTime(null);
            learningDao.save(learningTime);
        }
    }
    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        
        logger.info("Session创建getAttributeNames+" + arg0.getSession().getAttributeNames());
        logger.info("Session创建getSessionTimeout+" + arg0.getSession().getServletContext().getSessionTimeout());
        logger.info("Session创建getCreationTime+" + arg0.getSession().getCreationTime());
        logger.info("Session创建getLastAccessedTime+" + arg0.getSession().getLastAccessedTime());
        logger.info("Session创建getMaxInactiveInterval+" + arg0.getSession().getMaxInactiveInterval());
        logger.info("Session创建getAttribute+]" + (Student)arg0.getSession().getAttribute("student"));

    }
    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        logger.info("Session销毁+" + arg0.getSession().getId());
        logger.info("--sessionDestroyed--");
        HttpSession session=arg0.getSession();
        logger.info("Session销毁getAttribute+" + (Student)arg0.getSession().getAttribute("student"));

        int sid = 0;
        try {
            sid = ((Student) session.getAttribute("student")).getId();
            LearningTime learningTime = learningDao.findBySid(sid);
            if (learningTime.getOnline()){
                learningTime.setOnline(false);
                learningTime.setPostSetTime(new Date());

                int minutes = (int) ((new Date().getTime() - learningTime.getLogInTime().getTime()) / (1000 * 60));
                //减掉session设置的过期时间
                minutes -= 19;
//                if (minutes>300){
//                    minutes = 300;
//                }
               if(minutes > 0){
                   learningTime.setTotalTime(minutes+learningTime.getTotalTime());
               }
                learningDao.save(learningTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
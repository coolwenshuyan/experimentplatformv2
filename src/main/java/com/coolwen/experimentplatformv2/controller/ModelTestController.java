package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.model.ExpModel;
import com.coolwen.experimentplatformv2.model.ModuleTestAnswer;
import com.coolwen.experimentplatformv2.model.ModuleTestAnswerStu;
import com.coolwen.experimentplatformv2.model.ModuleTestQuest;
import com.coolwen.experimentplatformv2.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author CoolWen
 * @version 2020-07-19 12:22
 */
@Controller
@RequestMapping("/courseleader/modeltest")
public class ModelTestController {

    protected static final Logger logger = LoggerFactory.getLogger(ModelTestController.class);
    @Autowired
    ExpModelService expModelService;

    /**
     * 注入模块测试题、测试题的选项、实验报告的service、学生成绩更新的和学生答题表的service、学生实验报告答题表
     */
    @Autowired
    private ModuleTestQuestService questService;

    @Autowired
    private ModuleTestAnswerService answerService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ScoreUpdateService scoreUpdateService;

    @Autowired
    private ModuleTestAnswerStuService moduleTestAnswerStuService;

    @Autowired
    private ReportAnswerService reportAnswerService;

    @Autowired
    private ArrangeClassService arrangeClassService;

    @GetMapping("addQuest")
    public String addQuest(Model model, HttpSession session) {
//        int id = (int) session.getAttribute("mId");
        int id = 1;
        logger.debug("模块id信息为:" + id);
        ExpModel expModel = expModelService.findExpModelByID(id);
        logger.debug("模块信息为:" + expModel);
        model.addAttribute("expMedel", expModel);
        model.addAttribute(new ModuleTestQuest());
        return "shiyan/addTestnew";
    }

    @PostMapping("addQuest")
    public String addQuest(ModuleTestQuest moduleTestQuest, HttpSession session, Model model,
                           int modelid) {
        logger.debug("添加题目信息为:" + moduleTestQuest);
        logger.debug("添加题目modelid:" + modelid);
        moduleTestQuest.setmId(modelid);
        String a = moduleTestQuest.getQuestType();
        String b = moduleTestQuest.getQuestAnswer();
        if (a.equals("单选")) {
            try {
                Integer.parseInt(b);
                questService.addModuleTestQuest(moduleTestQuest);
            } catch (Exception e) {
                session.setAttribute("errorInformation", "单选答案必须是数字");
                return "redirect:/shiyan/addQuest";
            }
        } else {
//            利用questService里的保存方法，将数据存到数据库
            questService.addModuleTestQuest(moduleTestQuest);
//            /控制台打印看添加进去的问题id是多少
            logger.debug("添加测试题里面的questID~~~~~~" + moduleTestQuest.getQuestId());
//            model.addAttribute("moduleId", moduleTestQuest.getQuestId());
            session.setAttribute("questID", moduleTestQuest.getQuestId());
        }
        //        返回当前添加试题页面
        return "redirect:/shiyan/list/" + modelid;
    }


    /**
     * 在添加模块测试题中添加选项
     *
     * @return 返回静态资源下的shiyan/addAnswer.html
     */
    @GetMapping("addAnswer")
    public String addAnswer(HttpSession session, Model model) {
//        int id = (int) session.getAttribute("questID");
        int id = 1;
        ModuleTestQuest moduleTestQuest = questService.findQuestByQuestId(id);
        logger.debug("要添加测试题目信息为:" + moduleTestQuest);
        String answerRight = moduleTestQuest.getQuestAnswer();
        model.addAttribute("answerRight", answerRight);
        logger.debug("正确答案:" + answerRight);
        //todo 判断可能有的错误
//        List<String> answerRights = Arrays.asList(answerRight.split(","));
//        logger.debug("正确答案的选项有:" + answerRights);
//        model.addAttribute("answerRights", answerRights);
        model.addAttribute("moduleTestQuest", moduleTestQuest);
        model.addAttribute(new ModuleTestAnswer());
        List<ModuleTestAnswer> moduleTestAnswers = answerService.findAllByQuestId(id);
        logger.debug("已有题目选项:" + moduleTestAnswers);
        model.addAttribute("moduleTestAnswers", moduleTestAnswers);
//       返回静态资源下的shiyan/addAnswer.html
        return "shiyan/addAnswerNew";
    }

    @PostMapping("addAnswer")
    public String addAnswer(ModuleTestAnswer moduleTestAnswer, HttpSession session, int questionId, String questAnswer) {
//        从添加模块测试题post方法中存入的问题id取出来，并赋值给qId
//        int qId = (int) session.getAttribute("questId");
//        控制台打印获取的问题id
        logger.debug("qId:-------" + questionId);
//        将问题id存入moduleTestAnswer对象，以便每次添加选项的问题id都是该问题的问题id
        moduleTestAnswer.setQuestId(questionId);
//        将添加的ModuleTestAnswer数据存入数据库
        logger.debug("已有题目选项:" + moduleTestAnswer);

        answerService.addAnswers(moduleTestAnswer);
        logger.debug("答案:" + questAnswer);
        if (0 != Integer.parseInt(questAnswer)) {
            ModuleTestQuest moduleTestQuest = questService.findQuestByQuestId(questionId);
            moduleTestQuest.setQuestAnswer(moduleTestQuest.getQuestAnswer() + ',' + moduleTestAnswer.getAnswerOrder());
            logger.debug("多添加了正确选项:" + moduleTestQuest);
            questService.addModuleTestQuest(moduleTestQuest);
        }

//        questService.
//        返回添加模块测试题页面
        return "redirect:/courseleader/modeltest/addQuest";
    }
}

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
import org.springframework.web.bind.annotation.*;

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
//设置数据回显
@SessionAttributes(value = {"questDescribe", "questType", "questScore", "questOrder"})
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
//        先清除添加选项中的提示缓存
        session.removeAttribute("EF");

        int id = (int) session.getAttribute("mId");
//        int id = 1;
        logger.debug("模块id信息为:" + id);
        ExpModel expModel = expModelService.findExpModelByID(id);
        logger.debug("模块信息为:" + expModel);
        model.addAttribute("expMedel", expModel);

//        从缓存中取到questDescribe，即题目的信息
        String questDescribe = (String) session.getAttribute("questDescribe");
//        开始拦截，即学生已作答的模块不允许添加试题
//        找到当前模块的所有试题
        List<ModuleTestQuest> questList = questService.find(id);
        List<ModuleTestAnswerStu> stuList = new ArrayList();
//        遍历试题
        for (ModuleTestQuest q : questList) {
//            找到对应问题的学生答题记录
            stuList = moduleTestAnswerStuService.findByQuest_id(q.getQuestId());
        }
//        如果stuList列表为空
        if (stuList == null || stuList.isEmpty() || CollectionUtils.isEmpty(stuList)) {
//            判断题目是否为空，如果为空就允许添加对象
            if (questDescribe == null || questDescribe.isEmpty() || questDescribe == "") {
                model.addAttribute("quest", new ModuleTestQuest());

            } else {

//                如果题目不为空，先从session中取到在添加试题的post方法里存入的questId，即问题id
                int qId = (int) session.getAttribute("questID");
//                定义一个quest对象，并以当前缓存的questId查找这个题目的所有信息赋值给quest
                model.addAttribute("quest", questService.findQuestByQuestId(qId));

            }
            return "shiyan/addTestNew";
        } else {
//            如果列表不为空，就返回信息
            logger.debug("不允许作答————————");
            String message = "学生已作答，不允许添加试题";
            session.setAttribute("msg2020612", message);
            return "redirect:/shiyan/list/" + id;

        }
    }

    @PostMapping("addQuest")
    public String addQuest(ModuleTestQuest moduleTestQuest, HttpSession session, Model model,
                           int modelid,
                           String questDescribe, String questType, float questScore,
                           int questOrder) {
        logger.debug("添加题目信息为:" + moduleTestQuest);
        logger.debug("添加题目modelid:" + modelid);
        int id = (int) session.getAttribute("mId");
//        moduleTestQuest.setmId(modelid);
        moduleTestQuest.setmId(id);
//        利用questService里的保存方法，将数据存到数据库
        questService.addModuleTestQuest(moduleTestQuest);
//        控制台打印看添加进去的问题id是多少
        logger.debug("添加测试题里面的questID~~~~~~" + moduleTestQuest.getQuestId());
        session.setAttribute("questID", moduleTestQuest.getQuestId());

//        利用model绑定数据到前端，实现数据回显
        model.addAttribute("questDescribe", questDescribe);
        model.addAttribute("questType", questType);
        model.addAttribute("questScore", questScore);
        model.addAttribute("questOrder", questOrder);
        //        返回当前添加试题页面
        return "redirect:/courseleader/modeltest/addQuest";
    }


    /**
     * 在添加模块测试题中添加选项
     *
     * @return 返回静态资源下的shiyan/addAnswer.html
     */
    @GetMapping("addAnswer")
    public String addAnswer(HttpSession session, Model model) {
        int id = (int) session.getAttribute("questID");
//        int id = 1;
        ModuleTestQuest moduleTestQuest = questService.findQuestByQuestId(id);
        logger.debug("要添加测试题目信息为:" + moduleTestQuest);
//        从缓存中取到mId
        int mId = (int) session.getAttribute("mId");
//
        String answerRight = moduleTestQuest.getQuestAnswer();
        model.addAttribute("answerRight", answerRight);
        logger.debug("正确答案:" + answerRight);
        //todo 判断可能有的错误
        List<ModuleTestAnswer> moduleTestAnswers = answerService.findAllByQuestId(id);

        if (moduleTestAnswers == null) {
            model.addAttribute("moduleTestAnswers", new ModuleTestAnswer());
        } else {
//            List<String> answerRights = Arrays.asList(answerRight.split(","));
//            logger.debug("正确答案的选项有:" + answerRights);
//            model.addAttribute("answerRights", answerRights);

            logger.debug("已有题目选项:" + moduleTestAnswers);
            model.addAttribute("moduleTestAnswers", moduleTestAnswers);
            model.addAttribute("mId", mId);

        }
        model.addAttribute("moduleTestQuest", moduleTestQuest);
//       返回静态资源下的shiyan/addAnswer.html
        return "shiyan/addAnswerNew";
    }

    @PostMapping("addAnswer")
    public String addAnswer(ModuleTestAnswer moduleTestAnswer, HttpSession session, int questionId, String questAnswer) {

//        控制台打印获取的问题id
        logger.debug("qId:-------" + questionId);
//        将问题id存入moduleTestAnswer对象，以便每次添加选项的问题id都是该问题的问题id
        moduleTestAnswer.setQuestId(questionId);
//        将添加的ModuleTestAnswer数据存入数据库
        logger.debug("已有题目选项____:" + moduleTestAnswer);

        answerService.addAnswers(moduleTestAnswer);
        logger.debug("答案:" + questAnswer);

        ModuleTestQuest moduleTestQuest = questService.findQuestByQuestId(questionId);
        String a = moduleTestQuest.getQuestType();
        if (0 != Integer.parseInt(questAnswer)) {
            if (a.equals("单选")) {
                if (moduleTestQuest.getQuestAnswer() == null) {
                    moduleTestQuest.setQuestAnswer(String.valueOf(moduleTestAnswer.getAnswerOrder()));
                } else {
                    session.setAttribute("EF", "单选题目只能有一个正确答案");
                }
            } else {
                moduleTestQuest.setQuestAnswer(moduleTestQuest.getQuestAnswer() + ',' + moduleTestAnswer.getAnswerOrder());
            }
            logger.debug("多添加了正确选项:" + moduleTestQuest);
            questService.addModuleTestQuest(moduleTestQuest);
        }

//        返回添加模块测试题页面
        return "redirect:/courseleader/modeltest/addAnswer";
    }


    //        ——————————整体测试的试题添加————————

    @GetMapping("addLastQuest/{arrangeId}")
    public String addQuest(Model model, HttpSession session, @PathVariable("arrangeId") int arrangeId) {

        session.removeAttribute("EInfo");

        logger.debug("arrangeId" + arrangeId);
//        从缓存中取到questDescribe，即题目的信息
        String questDescribe = (String) session.getAttribute("questDescribe");
        logger.debug("打印题目信息~~~~~~" + questDescribe);

//        logger.debug("模块id信息为:" + arrangeId);
//        ExpModel expModel = expModelService.findExpModelByID(arrangeId);
//        logger.debug("模块信息为:" + expModel);
//        model.addAttribute("expModel", expModel);

//        开始拦截，即学生已作答的模块不允许添加试题
//        找到当前模块的所有试题
        List<ModuleTestQuest> questList = questService.find(arrangeId);
        List<ModuleTestAnswerStu> stuList = new ArrayList();
//        遍历试题
        for (ModuleTestQuest q : questList) {
//            找到对应问题的学生答题记录
            stuList = moduleTestAnswerStuService.findByQuest_id(q.getQuestId());
        }

//        如果stuList列表为空
        if (stuList == null || stuList.isEmpty() || CollectionUtils.isEmpty(stuList)) {
//            判断题目是否为空，如果为空就允许添加对象
            if (questDescribe == null || questDescribe.isEmpty() || questDescribe == "") {
                model.addAttribute("Lastquest", new ModuleTestQuest());

            } else {
//                如果题目不为空，先从session中取到在添加试题的post方法里存入的questId，即问题id
                int qId = (int) session.getAttribute("QID");

                model.addAttribute("Lastquest", questService.findQuestByQuestId(qId));
            }
//            model.addAttribute("mId", arrangeId);
//            返回到静态资源下的shiyan/addTest.html
            return "shiyan/addLastTest";
        } else {
//            如果列表不为空，就返回信息
            logger.debug("不允许作答————————");
            String message = "学生已作答，不允许添加试题";
            session.setAttribute("msg2020612", message);
            return "redirect:/shiyan/lastTestList";
        }
    }


    /**
     * 添加整体模块测试题的post方法
     *
     * @param moduleTestQuest 问题对象
     * @param session         数据的缓存
     * @param questType       整体测试题的类型
     * @param questScore      整体测试题的分数
     * @param questAnswer     整体测试题的答案
     * @param questOrder      整体测试题的序号
     * @return 返回整体测试题列表
     */
    @PostMapping("addLastQuest/{arrangeId}")
    public String addQuest(ModuleTestQuest moduleTestQuest, HttpSession session, Model model,
                           @PathVariable("arrangeId") int arrangeId,
                           String questDescribe,
                           String questType, float questScore,
                           String questAnswer, int questOrder) {
        logger.debug("开始添加++++++——————————");
//        在试题表添加试题信息
//        通过模块测试题的题目找到整条试题的信息
//        moduleTestQuest.setQuestDescribe(questDescribe);
//        moduleTestQuest.setQuestType(questType);
//        moduleTestQuest.setQuestScore(questScore);
//        moduleTestQuest.setQuestAnswer(questAnswer);
        moduleTestQuest.setQuestOrder(questOrder);
//        模块id默认为-1，即整体测试的模块id为-1
        logger.debug("arrangeId_____——————:" + arrangeId);
        moduleTestQuest.setmId(arrangeId);
//        控制台打印ModuleTestQuest对象
        logger.debug(moduleTestQuest.toString());

//            利用questService里的保存方法，将数据存到数据库
        questService.addModuleTestQuest(moduleTestQuest);
//        控制台打印看添加进去的问题id是多少
        logger.debug("添加测试题里面的questID~~~~~~" + moduleTestQuest.getQuestId());

//        将这个问题id存入session
        session.setAttribute("QID", moduleTestQuest.getQuestId());

//        利用model绑定数据到前端，实现数据回显
        model.addAttribute("questDescribe", questDescribe);
        model.addAttribute("questType", questType);
        model.addAttribute("questScore", questScore);
        model.addAttribute("questOrder", questOrder);

        return "redirect:/courseleader/modeltest/addLastQuest/" + arrangeId;
    }

    /**
     * 在添加模块测试题中添加选项
     *
     * @return 返回静态资源下的shiyan/addAnswer.html
     */
    @GetMapping("addLastAnswer/{arrangeId}")
    public String addAnswer(Model model,@PathVariable("arrangeId") int arrangeId, HttpSession session) {
        int id = (int) session.getAttribute("QID");
        model.addAttribute("selected", arrangeId);

//        int aID = (int) session.getAttribute("selected");
        ModuleTestQuest moduleTestQuest = questService.findQuestByQuestId(id);
        logger.debug("要添加测试题目信息为:" + moduleTestQuest);
//        从缓存中取到mId
//        int mId = (int) session.getAttribute("mId");
//
        String answerRight = moduleTestQuest.getQuestAnswer();
        model.addAttribute("answerRight", answerRight);
        logger.debug("正确答案:" + answerRight);
        //todo 判断可能有的错误
        List<ModuleTestAnswer> moduleTestAnswers = answerService.findAllByQuestId(id);

        if (moduleTestAnswers == null) {
            model.addAttribute("lastAnswers", new ModuleTestAnswer());
        } else {

            logger.debug("已有题目选项:" + moduleTestAnswers);
            model.addAttribute("lastAnswers", moduleTestAnswers);
            model.addAttribute("arrangeId", arrangeId);

        }
        model.addAttribute("moduleTestQuest", moduleTestQuest);
//        model.addAttribute("arrangeId",arrangeId);

//       返回静态资源下的shiyan/addLastAnswer.html
        return "shiyan/addLastAnswer";
    }

    /**
     * 在添加模块测试题中添加选项的post方法
     *
     * @param moduleTestAnswer 模块测试题的选项对象
     * @param session          数据的缓存空间
     * @return 返回添加模块测试题页面
     */
    @PostMapping("addLastAnswer/{arrangeId}")
    public String addAnswer(ModuleTestAnswer moduleTestAnswer, HttpSession session,
                            @PathVariable("arrangeId") int arrangeId, String questAnswer,Model model) {
//        从添加模块测试题post方法中存入的问题id取出来，并赋值给qId
        int QID = (int) session.getAttribute("QID");
//        控制台打印获取的问题id
        logger.debug("qId:-------" + QID);
//        将问题id存入moduleTestAnswer对象，以便每次添加选项的问题id都是该问题的问题id
        moduleTestAnswer.setQuestId(QID);
//        将添加的ModuleTestAnswer数据存入数据库
        answerService.addAnswers(moduleTestAnswer);

        ModuleTestQuest moduleTestQuest = questService.findQuestByQuestId(QID);
        String a = moduleTestQuest.getQuestType();
        if (0 != Integer.parseInt(questAnswer)) {
            if (a.equals("单选")) {
                if (moduleTestQuest.getQuestAnswer() == null) {
                    moduleTestQuest.setQuestAnswer(String.valueOf(moduleTestAnswer.getAnswerOrder()));
                } else {
                    session.setAttribute("EInfo", "单选题目只能有一个正确答案");
                }
            } else {
                moduleTestQuest.setQuestAnswer(moduleTestQuest.getQuestAnswer() + ',' + moduleTestAnswer.getAnswerOrder());
            }
            logger.debug("多添加了正确选项:" + moduleTestQuest);
            questService.addModuleTestQuest(moduleTestQuest);
        }

//        返回添加模块测试题页面
        return "redirect:/courseleader/modeltest/addLastAnswer/" + arrangeId;
    }


}

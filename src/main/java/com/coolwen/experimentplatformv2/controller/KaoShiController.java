/**
 * 文件名：KaoshiController.java
 * 修改人：王雨来
 * 修改时间：2020-05-17
 * 修改内容：新增
 */
package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.model.DTO.QuestListAnswerAndStuScoreDto;
import com.coolwen.experimentplatformv2.model.DTO.QuestListAnswerDto;
import com.coolwen.experimentplatformv2.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 考试/选择题
 * 学生模块检测选择题答题
 * @author CoolWen
 * @version 2020-05-17 17:57
 */


@Controller
@RequestMapping("/kaoshi")
public class KaoShiController {

    protected static final Logger logger = LoggerFactory.getLogger(KaoShiController.class);
    @Autowired
    private ModuleTestQuestService moduleTestQuestService;  //模块测试问题

    @Autowired
    private ModuleTestAnswerStuService moduleTestAnswerStuService; //学生答案

    @Autowired
    private ModuleTestAnswerService moduleTestAnswerService; //模块测试答案

    @Autowired
    private KaoHeModelScoreService kaoHeModelScoreService; //模块考核分数

    @Autowired
    private KaoheModelService kaoheModelService; //模块考核

    @Autowired
    private TotalScoreCurrentService totalScoreCurrentService; //总成绩

    @Autowired
    private ExpModelService expModelService;//实验模块

    @Autowired
    private StudentService studentService;//学生信息

    @Autowired
    private ScoreUpdateService scoreUpdateService;


    @Autowired
    private ClazzService clazzService;


    /**
     *返回考试题目
     * @param mid 筛选模块
     * @param model 传题目
     * @param session 获得学生id
     * @return 返回到考试页面
     */

    @RequestMapping("/{mid}/toExamPageList")
    public String toExamPage(@PathVariable("mid") Integer mid,
                             Model model, HttpSession session) {


        //获得学生id
        Student student= (Student) session.getAttribute("student");
        int stuId = student.getId();

        //获取此学生此模块已经提交的答案
        List<ModuleTestAnswerStu> moduleTestAnswerStus = moduleTestAnswerStuService.findStudentAnswbyStuidAndMid(stuId ,mid);

        //统计此学生此题的答题记录条数,如果>0 表示此学生以前做过此模块的题
        if (moduleTestAnswerStus.size()>0){
            System.out.println("此学生已经做过此模块");
            return "redirect:/kaoshi/"+mid+"/ViewTheScore";
        }
        //获得此模块的所有单选题
        List<QuestListAnswerDto> questionsList = moduleTestQuestService.listQuestAnswerDto("单选", mid);
        for (QuestListAnswerDto i:questionsList){
            System.out.println(i);
        }

        //将单选题目和模块id传入
        model.addAttribute("radioQuestionsList", questionsList);
        model.addAttribute("midd", mid);
        //返回多选题目
        List<QuestListAnswerDto> questionsList2 = moduleTestQuestService.listQuestAnswerDto("多选", mid);

        //将多选题目传入
        model.addAttribute("checkboxQuestionsList", questionsList2);

        return "home_shiyan/CanKaoceshitest";

    }




    /**
     * 计算学生考试成绩并存储到数据库
     * 将表单传输的name和value以map形式接受然后遍历它
     * @param map 暂时无用
     * @param mid 模块id
     * @param model 暂时无用
     * @param session 获得学生信息
     * @param request 学生的答案
     * @return 返回到成绩查看页面或者其他页面
     */
    @RequestMapping("/{mid}/postExam")
    public String postExam(Map<String,String> map,
                           @PathVariable("mid") Integer mid,
                           Model model, HttpSession session, HttpServletRequest request ) {

        Student student= (Student) session.getAttribute("student");
        int stuId = student.getId();

        //判断是否需要进行后续更新成绩
        boolean expModelNeedKaohe = false;
        if (mid == -1){
            expModelNeedKaohe = true;
        }else {
            //检查次模块是不是考核模块
            expModelNeedKaohe = expModelService.findExpModelByID(mid).isNeedKaohe();
        }

        //检查此学生有没有考核资格
        List<Student> studentOne = studentService.findStudentIsCurrentkaoheByStuid(stuId);

        //获得学生提交的试卷
        Enumeration em = request.getParameterNames();

        //检查此学生有没有考核资格
        //获得所有需要考核的学生
        //这里没用就删除了
//        List<Student> studentList = studentService.findStudentByNotClassId();
//
//        boolean stuNeedkaohe = studentList.contains(student);

        //创建学生的分数
        //这个不需要了
        //float fs = 0;

        //遍历所有request
        while (em.hasMoreElements()) {
            //得到题目编号
            String name = (String) em.nextElement();
            System.out.println("name<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+name);

            //通过题目获得学生的所有答案
            String[] value = request.getParameterValues(name);

            //得到数据库此模块中此学生答案表
            List<ModuleTestAnswerStu> pd = moduleTestAnswerStuService.findAllModuleTestAnswerStuByStuidAndQuestId(stuId, Integer.parseInt(name));

            //初始化一个学生答案
            ModuleTestAnswerStu moduleTestAnswerStu = new ModuleTestAnswerStu();

            //如果数据库此模块学生答案表已经存在,获得这个东西的id,并且将之前初始化的id设置为此id
            if (pd.size()>0){
                Integer zgdxdid = pd.get(0).getId();
                moduleTestAnswerStu.setId(zgdxdid);
            }

            //将之前初始化的学生id和问题id设置为对应的值
            moduleTestAnswerStu.setStu_id(stuId);
            moduleTestAnswerStu.setQuest_id(Integer.parseInt(name));

            //初始化一个答案
            String daAn = "";
            //通过遍历得到学生答案,并且保存
            for(String c : value){
                System.out.println("学生答案cccccccccccccccccccccc"+c);
                ModuleTestAnswer moduleTestAnswer = moduleTestAnswerService.findByAnswerId(Integer.parseInt(c));
                daAn += moduleTestAnswer.getAnswerOrder()+",";
            }

            if (daAn.length()>0){
                daAn = daAn.substring(0, daAn.length()-1);
            }
            System.out.println("答案"+daAn);

            moduleTestAnswerStu.setStu_quest_answer(daAn);

            //得到正确答案
            ModuleTestQuest moduleTestQuest = moduleTestQuestService.findQuestByQuestId(Integer.parseInt(name));
            String rightDaAn = moduleTestQuest.getQuestAnswer();

            //判断学生答案是否正确,并且保存
            if (daAn.equals(rightDaAn)){
                Integer fs1 = (int) moduleTestQuest.getQuestScore();
                moduleTestAnswerStu.setScore(fs1);
                //这里不需要了
                //fs+=fs1;
            }
            moduleTestAnswerStuService.add(moduleTestAnswerStu);
        }



//        更新此学生成绩,此操作现在整合下面这一大段，这里又修改了
        if(studentOne.size()>0 & expModelNeedKaohe){
            //更新学生测试状态
            if(mid>0) {
                KaoHeModelScore khs = kaoHeModelScoreService.findKaoheModelScoreByMid(mid, stuId);
                khs.setmTeststate(true);
                kaoHeModelScoreService.update(khs);
            }
            scoreUpdateService.singleStudentScoreUpdate(stuId);
        }


//        if(mid == -1){
//            System.out.println("这是一次期末考试");
//            KaoheModel kh = kaoheModelService.findKaoheModelByMid(mid);
//            List<TotalScoreCurrent> altsc = totalScoreCurrentService.findeAllBystuid(stuId);
//            if (altsc.size()==0){
//                System.out.println("出现错误!!总成绩表中没有此学生信息");
//            }
//            TotalScoreCurrent atsc = altsc.get(0);
//
//            //将考试的值存入总分表
//            atsc.setTotalScore(fs);
//            atsc.setTotalScore(atsc.getmTotalScore() * kh.getKaohe_baifenbi() + atsc.getTestScore() * kh.getTest_baifenbi());
//            totalScoreCurrentService.update(atsc);
//        }else {
//
//            //通过模块id查询考核模块
//            KaoheModel kh = kaoheModelService.findKaoheModelByMid(mid);
//            //通过模块id和学生id查询考核模块分数表
//            KaoHeModelScore khs = kaoHeModelScoreService.findKaoheModelScoreByMid(mid, stuId);
//            System.out.println("dddddddddddd" + khs + "," + khs.getId());
//            //将分数存入
//            khs.setmTestScore(fs);
//
//            //更新考核模块分数
//            float ms = (khs.getmReportScore() * kh.getM_report_baifenbi() + fs * kh.getM_test_baifenbi()) * khs.getmScale();
//            khs.setmScore(ms);
//            khs.setmTeststate(true);
//            kaoHeModelScoreService.update(khs);
//
//            //获取原来总分
//            TotalScoreCurrent tsc = totalScoreCurrentService.findTotalScoreCurrentByStuID(stuId);
//
//            //更新总分
//            tsc.setmTotalScore(tsc.getmTotalScore() + ms);
//            tsc.setTotalScore(tsc.getmTotalScore() * kh.getKaohe_baifenbi() + tsc.getTestScore() * kh.getTest_baifenbi());
//            totalScoreCurrentService.update(tsc);
//
//        }

        //返回此次得分,这里不需要了
        //model.addAttribute("fs",fs);
        //回到成绩查看页面
        return "redirect:/kaoshi/"+mid+"/ViewTheScore";
    }

    @RequestMapping("/{mid}/ViewTheScore")
    public String ViewTheScore(@PathVariable("mid") Integer mid,
                               Model model, HttpSession session) {

        //获得学生id
        Student student= (Student) session.getAttribute("student");
        int stuId = student.getId();

        //计算此模块总得分
        float fs2 = 0;
        //获得此模块的所有单选题
        List<QuestListAnswerAndStuScoreDto> questionsList = moduleTestQuestService.listQuestListAnswerAndStuScoreDto("单选", mid,stuId);
//        List<ModuleTestAnswerStu> moduleTestAnswerStus;

        //计算单选题得分
        for (QuestListAnswerAndStuScoreDto i:questionsList){
            fs2+=i.getOneQuestScore();
        }

        //将单选题目和模块id传入
        model.addAttribute("radioQuestionsList", questionsList);
        model.addAttribute("midd", mid);

        //返回多选题目
        List<QuestListAnswerAndStuScoreDto> questionsList2 = moduleTestQuestService.listQuestListAnswerAndStuScoreDto("多选", mid,stuId);

        //计算多选题得分
        for (QuestListAnswerAndStuScoreDto i:questionsList2){
            fs2+=i.getOneQuestScore();
        }

        //将多选题目传入
        model.addAttribute("checkboxQuestionsList", questionsList2);

        //此模块总得分
        model.addAttribute("fs2",fs2);
        return "home_shiyan/ViewTheScore";
    }



/*    @RequestMapping("/{mid}/viewTheScore")
    public String viewTheScore(Map<String,String> map,
                           @PathVariable("mid") Integer mid,
                           Model model, HttpSession session, HttpServletRequest request ) {

        String username = (String) session.getAttribute("username");
        Integer taotiId = null;

        //学生id,临时测试值为 1(需要从session中获得)
//        int stuId = 1;

        Student student= (Student) session.getAttribute("student");
        int stuId = student.getId();
        //获得学生提交的试卷
        Enumeration em = request.getParameterNames();

        //创建学生的分数
        float fs = 0;

        //遍历所有request
        while (em.hasMoreElements()) {
            //得到题目编号
            String name = (String) em.nextElement();
            System.out.println("name<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+name);

            //通过题目获得学生的所有答案
            String[] value = request.getParameterValues(name);

            //得到数据库此模块中此学生答案表
            List<ModuleTestAnswerStu> pd = moduleTestAnswerStuService.findAllModuleTestAnswerStuByStuidAndQuestId(stuId, Integer.parseInt(name));

            //初始化一个学生答案
            ModuleTestAnswerStu moduleTestAnswerStu = new ModuleTestAnswerStu();

            //如果数据库此模块学生答案表已经存在,获得这个东西的id,并且将之前初始化的id设置为此id
            if (pd.size()>0){
                Integer zgdxdid = pd.get(0).getId();
                moduleTestAnswerStu.setId(zgdxdid);
            }

            //将之前初始化的学生id和问题id设置为对应的值
            moduleTestAnswerStu.setStu_id(stuId);
            moduleTestAnswerStu.setQuest_id(Integer.parseInt(name));

            //初始化一个答案
            String daAn = "";
            //通过遍历得到学生答案,并且保存
            for(String c : value){
                ModuleTestAnswer moduleTestAnswer = moduleTestAnswerService.findByAnswerId(Integer.parseInt(c));
                daAn+=moduleTestAnswer.getAnswerOrder();
            }
            moduleTestAnswerStu.setStu_quest_answer(daAn);

            //得到正确答案
            ModuleTestQuest moduleTestQuest = moduleTestQuestService.findQuestByQuestId(Integer.parseInt(name));
            String rightDaAn = moduleTestQuest.getQuestAnswer();

            //判断学生答案是否正确,并且保存
            if (daAn.equals(rightDaAn)){
                Integer fs1 = (int) moduleTestQuest.getQuestScore();
                moduleTestAnswerStu.setScore(fs1);
                fs+=fs1;
            }
            moduleTestAnswerStuService.add(moduleTestAnswerStu);
        }

        model.addAttribute("fs",fs);
        //回到成绩查看页面或者其他页面
        return "home_shiyan/viewTheScore";
    }*/

}

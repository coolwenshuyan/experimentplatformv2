package com.coolwen.experimentplatformv2.controller;

import com.coolwen.experimentplatformv2.model.CollegeReport;
import com.coolwen.experimentplatformv2.model.DTO.CollegeReportStuExpDto;
import com.coolwen.experimentplatformv2.model.KaoHeModelScore;
import com.coolwen.experimentplatformv2.model.KaoheModel;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.CollegeReportService;
import com.coolwen.experimentplatformv2.service.KaoHeModelScoreService;
import com.coolwen.experimentplatformv2.service.KaoheModelService;
import com.coolwen.experimentplatformv2.service.ScoreUpdateService;
import com.coolwen.experimentplatformv2.utils.FileUploadUtil;
import com.coolwen.experimentplatformv2.utils.GetServerRealPathUnit;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author 朱治汶
 * @version 1.0
 * @className CollegeReportMarkController
 * @description TODO
 * @date 2020/7/17 21:47
 **/
@Controller
@RequestMapping(value = "/collegereportmark")
public class CollegeReportMarkController {

    protected static final Logger logger = LoggerFactory.getLogger(CollegeReportMarkController.class);
    @Autowired
    CollegeReportService collegeReportService;

    @Autowired
    KaoHeModelScoreService kaoHeModelScoreService;

    @Autowired
    ScoreUpdateService scoreUpdateService;

    @Autowired
    KaoheModelService kaoheModelService;

    @GetMapping("/{mid}/{stuid}/{arrangeId}")
    public String mark(@PathVariable("mid") int mid,
                       @PathVariable("stuid") int stuid,
                       @PathVariable("arrangeId") int arrangeId, Model model) {

        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
        model.addAttribute("attachs", user.getSignature());

        //查询到报告信息
        CollegeReportStuExpDto collegeReportStuExpDto = collegeReportService.findByStuidMid(stuid, mid);
        model.addAttribute("collegeReport", collegeReportStuExpDto);
        model.addAttribute("stuid", stuid);
        model.addAttribute("arrangeId", arrangeId);
        return "shiyan_baogao/bg_teacher";
    }

    @PostMapping("/{mid}/{stuid}/{arrangeId}")
    public String mark(@PathVariable("mid") int mid,
                       @PathVariable("stuid") int stuid,
                       @PathVariable("arrangeId") int arrangeId,
                       CollegeReport collegeReport,
                       @RequestParam("attachs") MultipartFile[] attachs,
                       HttpSession session) {

        CollegeReport collegeReport1 = collegeReportService.findStuidAndMid(stuid, mid);

        logger.debug("collegeReport1:"+collegeReport1);

        collegeReport1.setCrTcComment(collegeReport.getCrTcComment());
        logger.debug("CrTcComment:"+collegeReport.getCrTcComment());
        collegeReport1.setCrScore(collegeReport.getCrScore());
        collegeReport1.setCrTcState(true);
        collegeReport1.setCrSigningTime(new Date());

        //储存图片，并将图片路径储存到数据库
        String realpath = GetServerRealPathUnit.getPath("static/upload/");
//       logger.debug("realPath:" + realpath);
        for (MultipartFile attach : attachs) {
            if (attach.isEmpty()) {
                continue;
            }
            //图片验证重命名
            String picName = FileUploadUtil.picRename(attach.getContentType());
            String path = realpath + "/" + picName;
//            logger.debug(path);
            File f = new File(path);
//            user.setImg(picName);
            collegeReport1.setCrImgurl(picName);
            try {
                FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (collegeReport1.getCrImgurl()==null){
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
            collegeReport1.setCrImgurl(user.getSignature());
        }

        collegeReportService.addCollegeReport(collegeReport1);

        //如果是考核模块，改变学生填写报告教师评分状态为true
        List<KaoheModel> kaoheModels1 = kaoheModelService.findKaoHeModelByArrangeidAndMid(arrangeId,mid);
        if(kaoheModels1.size()>0) {
            KaoHeModelScore khs = kaoHeModelScoreService.findKaoheModelScoreByMid(kaoheModels1.get(0).getId(), stuid);
            khs.setmReportteacherstate(true);
            kaoHeModelScoreService.update(khs);
        }
        //更新成绩
        scoreUpdateService.singleStudentScoreUpdate2(stuid,arrangeId);
        return "redirect:/reportScoreManage/report/" + arrangeId;
    }
}

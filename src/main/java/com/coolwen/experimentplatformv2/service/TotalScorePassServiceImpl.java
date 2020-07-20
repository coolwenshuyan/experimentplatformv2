package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.TotalScorePassRepository;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.DTO.CourseClassInfo;
import com.coolwen.experimentplatformv2.model.DTO.StuTotalScoreCurrentDTO;
import com.coolwen.experimentplatformv2.model.Teacher;
import com.coolwen.experimentplatformv2.model.TotalScorePass;
import com.coolwen.experimentplatformv2.specification.SimpleSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TotalScorePassServiceImpl implements TotalScorePassService {
    protected static final Logger logger = LoggerFactory.getLogger(TotalScorePassServiceImpl.class);
    @Autowired
    TotalScorePassRepository totalScorePassRepository;

    @Value("10")
    int size;

    @Override
    public void delteTotalScorePassByStuId(int id) {
        TotalScorePass totalScorePass = totalScorePassRepository.findTotalScorePassByStuId(id);
        if (totalScorePass != null) {
            totalScorePassRepository.delete(totalScorePass);

        }
    }

    @Override
    public void save(TotalScorePass totalScorePass) {
        totalScorePassRepository.save(totalScorePass);
    }

    @Override
    public Page<TotalScorePass> findAll(int pageNum) {
        logger.debug("成功进入");
        Pageable pageable = PageRequest.of(pageNum, 10);

        return totalScorePassRepository.findAll(pageable);
    }


    @Override
    public Page<TotalScorePass> findAllByClassId(int classId) {
        return null;
    }

    @Override
    public List<TotalScorePass> findByStuId(int stuId) {
        return totalScorePassRepository.findByStuId(stuId);
    }

    @Override
    public List<CourseClassInfo> findClassAndCoursebyGongHao(String gonghao) {
        return totalScorePassRepository.findClassAndCoursebyGongHao(gonghao);
    }

    @Override
    public List<CourseInfo> findCoursebyGongHao(String gonghao) {
        return totalScorePassRepository.findCoursebyGongHao(gonghao);
    }

    @Override
    public Page<StuTotalScoreCurrentDTO> findTotalScorePassbyCourseIdClassId(Integer pageNum, String select_orderId, int courseId, int classId) {
        Pageable pager = PageRequest.of(pageNum, size);
        return totalScorePassRepository.findTotalScorePassbyCourseIdClassId(pager, courseId, classId, select_orderId);
    }

    @Override
    public List<StuTotalScoreCurrentDTO> findallTotalScorePassbyCourseIdClassId(int courseId, int classId) {
        return totalScorePassRepository.findallTotalScorePassbyCourseIdClassId(courseId, classId);
    }

    @Override
    public Page<TotalScorePass> findByStudentIdAndCourseId(int pageNum, int studentId, int CourseId) {
        Pageable pager = PageRequest.of(pageNum, size);
        Page<TotalScorePass> teacherPage = totalScorePassRepository.findAll(new SimpleSpecificationBuilder<TotalScorePass>(
                "stuId", "=", studentId)
                .add("courseId", "=", CourseId)
                .generateSpecification(), pager);
        return teacherPage;
    }

    @Override
    public Page<TotalScorePass> findByStudentId(int pageNum, int studentId) {
        Pageable pager = PageRequest.of(pageNum, size);
        Page<TotalScorePass> teacherPage = totalScorePassRepository.findAll(new SimpleSpecificationBuilder<TotalScorePass>(
                "stuId", "=", studentId)
                .generateSpecification(), pager);
        return teacherPage;
    }
}

package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.DTO.CourseClassInfo;
import com.coolwen.experimentplatformv2.model.DTO.StuTotalScoreCurrentDTO;
import com.coolwen.experimentplatformv2.model.TotalScorePass;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TotalScorePassService {
    void delteTotalScorePassByStuId(int id);

    void save(TotalScorePass totalScorePass);

    Page<TotalScorePass> findAll(int pageNum);

    Page<TotalScorePass> findAllByClassId(int classId);


    List<TotalScorePass> findByStuId(int stuId);

    List<CourseClassInfo> findClassAndCoursebyGongHao(String gonghao);

    List<CourseInfo> findCoursebyGongHao(String gonghao);

    Page<StuTotalScoreCurrentDTO> findTotalScorePassbyCourseIdClassId(Integer pageNum, String select_orderId, int courseId, int classId);

    List<StuTotalScoreCurrentDTO> findallTotalScorePassbyCourseIdClassId(int courseId, int classId);

    Page<TotalScorePass> findByStudentIdAndCourseId(int pageNum, int studentId, int CourseId);

    Page<TotalScorePass> findByStudentId(int pageNum, int studentId);
}

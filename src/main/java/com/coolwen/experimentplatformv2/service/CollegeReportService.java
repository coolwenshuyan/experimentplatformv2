package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.CollegeReport;
import com.coolwen.experimentplatformv2.model.DTO.CollegeReportStuExpDto;

import java.util.List;

/**
 * @author 朱治汶
 */
public interface CollegeReportService {
    CollegeReport findStuidAndMid(int id, int mid);

    void addCollegeReport(CollegeReport collegeReport);

    CollegeReportStuExpDto findByStuidMid(int id, int mid);

    List<CollegeReport> findCollegeReportByMid(int mid);

    void deleteCollege(int mid);

    void deleteCollegeList(List<CollegeReport> list);

    void deleteByStuIdModelId(int m_id, int id);

    void deleteByModelId(int mid);
}

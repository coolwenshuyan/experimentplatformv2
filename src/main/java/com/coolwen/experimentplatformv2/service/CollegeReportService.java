package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.CollegeReport;
import com.coolwen.experimentplatformv2.model.DTO.CollegeReportStuExpDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    List<CollegeReport> findCollegeReportsByArrangeIdAndMids(int arrangeId, List<Integer> mids);

    List<CollegeReportStuExpDto> findByCourseId(int courseId);

    Page<CollegeReportStuExpDto> findByCourseIdPag(int courseId,Pageable pageable);
}

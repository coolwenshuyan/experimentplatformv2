package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.ReportAnswer;

import java.util.List;

/**
 * @author 淮南
 * @date 2020/5/13 21:57
 */
public interface ReportAnswerService {

    public void addReportAnswer(ReportAnswer reportAnswer);

    public void deleteReportAnswer(int id);

    public ReportAnswer findByReportByreportid(int reportid);

    public ReportAnswer updateReportAnswer(int id);

    public void updateOne(ReportAnswer reportAnswer);

    public List<ReportAnswer> loadReportAnswer();

    ReportAnswer findByReportAnswerId(int id);

    public ReportAnswer findByReportidAndStuID(int reportid, int stuID);

    List<ReportAnswer> findByStuId(int stuid);

    List<ReportAnswer> listByReportidAndStuID(int reportid, int stuId);

    void deleteReportAnswerByReportId(int reportid);

    void deleteReportAnswerByStuId(int id);

    int findByStuIdModelId(int mid, int stuId);

    void deleteReportAnswerByMid(int mid);

    void deleteByStuIdModelId(int m_id, int id);
}

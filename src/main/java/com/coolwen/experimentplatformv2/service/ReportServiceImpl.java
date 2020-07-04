package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.ReportRepository;
import com.coolwen.experimentplatformv2.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 淮南
 * @date 2020/5/13 20:15
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public void addReport(Report report) {
        reportRepository.save(report);
    }

    @Override
    public int deleteReport(int reportId) {
        return reportRepository.deleteByReportId(reportId);
    }

    @Override
    public Report updateReport(int reportId) {
        Report report = reportRepository.findByReportId(reportId);
        return report;
    }

//    @Override
//    public List<Report> loadReport() {
//        return reportRepository.findAll();
//    }

    @Override
    public Report findByReportId(int reportId) {
        return reportRepository.findByReportId(reportId);
    }

//    @Override
//    public List<Report> findAllByReportId(int reportId) {
//        return reportRepository.findAllByReportId(reportId);
//    }

    @Override
    public List<Report> findByMid(int mid) {
        return reportRepository.findAllByMId(mid);
    }

    @Override
    public Page<Report> findByReportPage(Pageable pageable, int mId) {

        return reportRepository.findByReportPage(mId,pageable);
    }

    @Override
    public List<Report> findReportByMId(int mid) {
        return reportRepository.findReportByMId(mid);
    }

    @Override
    public void deleteReports(List<Report> report) {
        reportRepository.deleteAll(report);
    }

    @Override
    public List<Report> findByMidpaixu(int mid) {
        return reportRepository.findByMidpaixu(mid);
    }

}

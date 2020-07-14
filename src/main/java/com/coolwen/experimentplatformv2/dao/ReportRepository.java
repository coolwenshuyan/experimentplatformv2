package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 淮南
 * @date 2020/5/13 20:12
 */
public interface ReportRepository extends BaseRepository<Report, Integer>, JpaSpecificationExecutor<Report> {

//    通过实验报告id找到该实验报告的信息
    Report findByReportId(int reportId);

//    删除实验报告
    @Transactional
    int deleteByReportId(int reportId);

//    List<Report> findAllByReportId(int reportId);

    //findAllByMid找不到啊
//    通过模块id找到所有实验报告
    @Query("select r from Report r where r.mId = ?1")
    List<Report> findAllByMId(int mid);

//    通过mid找到所有实验报告
    @Query("select r from Report r where r.mId = ?1")
    List<Report> findReportByMId(int mid);

//    通过模块id分页
    @Query("select q from ExpModel e, Report q where e.m_id =q.mId and e.m_id = ?1")
    Page<Report> findByReportPage(@Param("mid") int mid, Pageable pageable);


    @Query("select r from Report r where r.mId = ?1 order by r.reportOrder")
    List<Report> findByMidpaixu(int mid);

    @Modifying
    @Transactional(readOnly = false)
    @Query("delete from Report r where r.mId = ?1")
    void deleteReportByModelId(int mid);
}

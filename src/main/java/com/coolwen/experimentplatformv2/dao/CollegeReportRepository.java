package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.CollegeReport;
import com.coolwen.experimentplatformv2.model.DTO.CollegeReportStuExpDto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CollegeReportRepository extends BaseRepository<CollegeReport, Integer>, JpaSpecificationExecutor<CollegeReport> {

    @Query("select c from CollegeReport c where c.stuid = ?1 and c.mid = ?2")
    CollegeReport findStuidAndMid(int stuid, int mid);


    @Query("select new com.coolwen.experimentplatformv2.model.DTO.CollegeReportStuExpDto" +
            "(expm.m_id,cr.crClassName,cr.crDress,cr.crDate,cr.crTeacher,cr.crExpPurpose,cr.crExpEvr,cr.crExpContent," +
            "cr.crExpSummary,cr.crTcComment,cr.crScore,cr.crTcState,st.stuName,st.stuXuehao,c.className,expm.m_name)" +
            "from CollegeReport cr left join Student st on cr.stuid = st.id " +
            "left join ClassModel c on c.classId = st.classId " +
            "left join ExpModel expm on cr.mid = expm.m_id " +
            "where st.id = ?1 and expm.m_id = ?2")
    CollegeReportStuExpDto findByStuidMid(int stuid, int mid);

    @Query("select c from CollegeReport c where c.mid = ?1")
    List<CollegeReport> findCollegeReportsByMid(int mid);

    @Modifying
    @Transactional
    @Query(value="DELETE t_college_report FROM t_college_report WHERE t_college_report.m_id = ? and t_college_report.stu_id=?",nativeQuery=true)
    void deleteByStuIdModelId(int m_id, int id);

    @Modifying
    @Transactional(readOnly = false)
    @Query("delete from CollegeReport c where c.mid = ?1")
    void deleteByModelId(int mid);
}

package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.DTO.CourseClassInfo;
import com.coolwen.experimentplatformv2.model.DTO.StuTotalScoreCurrentDTO;
import com.coolwen.experimentplatformv2.model.TotalScorePass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TotalScorePassRepository extends BaseRepository<TotalScorePass, Integer>, JpaSpecificationExecutor<TotalScorePass> {
    @Query("select t from TotalScorePass t where t.stuId = ?1")
    TotalScorePass findTotalScorePassByStuId(int id);

    List<TotalScorePass> findAll();

    List<TotalScorePass> findByStuId(int stuId);

    @Modifying
    @Transactional(readOnly = false)
    @Query("select new com.coolwen.experimentplatformv2.model.DTO.CourseClassInfo(cui.courseName,csi.className,cci.courseId,cci.classId) from TotalScorePass cci,CourseInfo cui,ClassModel csi where cci.courseId = cui.id and cci.classId=csi.classId and cci.teacherGongHao=?1 group by cci.courseId,cci.classId order by cci.courseId,cci.classId ")
    List<CourseClassInfo> findClassAndCoursebyGongHao(String gonghao);


    @Query("select cui from CourseInfo cui left join TotalScorePass cci on cci.courseId = cui.id where cci.teacherGongHao=?1 group by cui.id order by cui.id ")
    List<CourseInfo> findCoursebyGongHao(String gonghao);

    @Query("select new com.coolwen.experimentplatformv2.model.DTO.StuTotalScoreCurrentDTO " +
            "(st.stuXuehao,st.stuName,cla.className,tsc.mTotalScore,tsc.testScore,tsc.totalScore) " +
            "from Student st left join TotalScorePass tsc on st.id = tsc.stuId " +
            "left join ClassModel cla on st.classId=cla.classId where tsc.courseId = ?1 and tsc.classId = ?2")
    Page<StuTotalScoreCurrentDTO> findTotalScorePassbyCourseIdClassId(Pageable page, int courseId, int classId, String select_orderId);

    @Query("select new com.coolwen.experimentplatformv2.model.DTO.StuTotalScoreCurrentDTO " +
            "(st.stuXuehao,st.stuName,cla.className,tsc.mTotalScore,tsc.testScore,tsc.totalScore) " +
            "from Student st left join TotalScorePass tsc on st.id = tsc.stuId " +
            "left join ClassModel cla on st.classId=cla.classId where tsc.courseId = ?1 and tsc.classId = ?2")
    List<StuTotalScoreCurrentDTO> findallTotalScorePassbyCourseIdClassId(int courseId, int classId);


    TotalScorePass findTotalScorePassByStuIdAndCourseId(int stuid,int courseid);
}

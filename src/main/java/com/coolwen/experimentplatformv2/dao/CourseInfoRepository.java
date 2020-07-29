package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.ClassModel;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.DTO.CourseClassInfo;
import com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto;
import com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto2;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseInfoRepository extends BaseRepository<CourseInfo, Integer>, JpaSpecificationExecutor<CourseInfo> {


    @Query("select c from CourseInfo c where c.teacherId = ?1")
    List<CourseInfo> getclassByCharge(int teacher_id);


    @Query("select c from CourseInfo c,ArrangeClass a where c.id = a.courseId and a.teacherId = ?1 group by a.courseId,a.teacherId")
    List<CourseInfo> getclass_by_arrangeteacher(int teacher_id);

    @Query("select c from ClassModel c,ArrangeClass a where c.id = a.classId and a.teacherId= ?1 and a.courseId = ?2")
    List<ClassModel> getclass_by_arrangecourseid(int teacher_id, int course_id);

    @Query("select new com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto " +
            "(ci.id,ci.courseName,ci.courseCode,ci.teacherId,ci.courseImgurl,ci.courseIntruduce,ac.id) " +
            "from CourseInfo ci,ArrangeClass ac where ci.id=ac.courseId and ci.id=?1 and ac.classId = ?2")
    CourseInfoDto findByCourseInfoIdAndClassId(int courseInfoId, int classId);

    @Query("select new com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto2 " +
            "(ci.id, ci.courseName, ci.courseCode, ci.teacherId, ci.courseImgurl, ci.courseIntruduce, ac.id, ac.classId, ac.teacherId, ac.arrangeStart, ac.arrangeEnd, ac.skAddress) " +
            "from CourseInfo ci,ArrangeClass ac where ci.id=ac.courseId and ac.classId = ?1")
    List<CourseInfoDto2> findByArrangeCourseInfoDto2byClassId(int classId);

    @Query("select c from CourseInfo c where c.id = ?1")
    CourseInfo findCourseInfoById(int id);


    @Query("select csi from ClassModel csi,TotalScorePass cci where cci.classId=csi.classId and cci.teacherGongHao=?1 and cci.courseId =?2 group by cci.courseId,cci.classId order by csi.classId ")
    List<ClassModel> getClassByCourseidUseridpass(String teacherid, int courseId);

    @Query("select ci from CourseInfo ci where ci.courseCode = ?1")
    List<CourseInfo> findAllByCourseCode(String courseCode);
}

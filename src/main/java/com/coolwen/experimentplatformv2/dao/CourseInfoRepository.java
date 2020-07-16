package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.ClassModel;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto;
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

    public CourseInfo findCourseInfoById(int id);


}

package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseInfoRepository extends BaseRepository<CourseInfo, Integer>, JpaSpecificationExecutor<CourseInfo> {


    @Query("select c from CourseInfo c where c.teacherId = ?1")
    List<CourseInfo> getclassByCharge(int teacher_id);


    @Query("select c from CourseInfo c,ArrangeClass a where c.id = a.courseId and a.teacherId = ?1")
    List<CourseInfo> getclass_by_arrangeteacher(int teacher_id);
}

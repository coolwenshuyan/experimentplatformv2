package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ArrangeClassRepository extends BaseRepository<ArrangeClass, Integer>, JpaSpecificationExecutor<ArrangeClass> {



    @Query(value = "select  new com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto(a.id,cin.courseName,u.username,c.className,a.arrangeStart,a.arrangeEnd,a.skAddress) from ArrangeClass a,CourseInfo cin,ClassModel c,User u where a.teacherId = u.id and a.classId=c.classId and a.courseId=cin.id")
    Page<ArrangeClassDto> findByAll(Pageable pageable);

    @Query(value = "select  new com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto" +
            "(a.id,cin.courseName,u.username,c.className,a.arrangeStart,a.arrangeEnd) " +
            "from ArrangeClass a,CourseInfo cin,ClassModel c,User u " +
            "where a.teacherId = u.id and a.classId=c.classId and a.courseId=cin.id and cin.courseName like %?1% and u.username like %?2% and c.className like %?3%")
    Page<ArrangeClassDto> findBycidAndtidAndclaidLike(String courseName,String teacherName, String className,Pageable pager);

    ArrangeClass findByCourseIdAndTeacherIdAndClassId(int courseId, int teacherId, int classId);


//    @Query(value = "select new com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto(a.id,cin.courseName,u.username,c.className,a.arrangeStart,a.arrangeEnd) from t_arrange_class a,t_course_info cin,t_class c,t_user u where a.teacher_id = u.id and a.class_id=c.class_id and a.course_id=cin.id and if(?1 !='',cin.course_name=?1,1=1) and if(?2 !='',u.username=?2,1=1) and if(?3 !='',c.class_name=?3,1=1)",nativeQuery = true)
//    Page<ArrangeClassDto> findBycidAndtidAndclaidLike(String courseName,String teacherName, String className,Pageable pager);
}

//    select a.id,cin.course_name,u.username,c.class_name,a.arrange_start,a.arrange_end from t_arrange_class a,t_course_info cin,t_class c,t_user u where a.teacher_id = u.id and a.class_id=c.class_id and a.course_id=cin.id and if(?1 !=NULL,name=?1,1=1) and
























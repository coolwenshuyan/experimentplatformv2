package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeInfoDTO;
import com.coolwen.experimentplatformv2.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArrangeClassRepository extends BaseRepository<ArrangeClass, Integer>, JpaSpecificationExecutor<ArrangeClass> {


    @Query(value = "select new com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto" +
            "(a.id,cin.courseName,u.nickname,c.className,a.arrangeStart,a.arrangeEnd,a.skAddress,c.classGrade) " +
            "from ArrangeClass a,CourseInfo cin,ClassModel c,User u " +
            "where a.teacherId = u.id and a.classId=c.classId and a.courseId=cin.id")
    Page<ArrangeClassDto> findByAll(Pageable pageable);

    @Query(value = "select  new com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto" +
            "(a.id,cin.courseName,u.nickname,c.className,a.arrangeStart,a.arrangeEnd,a.skAddress,c.classGrade) " +
            "from ArrangeClass a,CourseInfo cin,ClassModel c,User u " +
            "where a.teacherId = u.id and a.classId=c.classId and a.courseId=cin.id and cin.courseName like %?1% and u.nickname like %?2% and c.className like %?3% and c.classGrade like %?4%")
    Page<ArrangeClassDto> findBycidAndtidAndclaidLike(String courseName, String teacherName, String className,String classGrade, Pageable pager);

    ArrangeClass findByCourseIdAndClassId(int courseId, int classId);


    @Query(value = "select  new com.coolwen.experimentplatformv2.model.DTO.ArrangeInfoDTO" +
            "(ac.id,cin.courseName,c.className,u.nickname,ac.skAddress,ac.arrangeStart,ac.arrangeEnd) " +
            "from ArrangeClass ac,CourseInfo cin,ClassModel c,User u " +
            "where ac.teacherId = u.id and ac.classId=c.classId and ac.courseId=cin.id and ac.teacherId = ?1")
    List<ArrangeInfoDTO> findArrangeInfoDTOByTeacherId(int teacherId);

    @Query(value = "select st from  Student st,ArrangeClass ac " +
            "where st.classId = ac.classId and ac.id=?1")
    List<Student> findStudentByarrangeID(int arrageid);


    List<ArrangeClass> findByClassId(int classId);

    List<ArrangeClass> findByCourseId(int id);

    @Modifying
    @Transactional(readOnly = false)
    @Query("delete from TotalScoreCurrent ts where ts.arrageId = ?1")
    void deleteTotalScoreByArrangeId(int arrangeId);


    @Query("select ac from Student s left join ArrangeClass ac on s.classId = ac.classId where s.id = ?1")
    List<ArrangeClass> findArrangeClassesBystudentId(int id);

//    @Query(value = "select new com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto(a.id,cin.courseName,u.username,c.className,a.arrangeStart,a.arrangeEnd) from t_arrange_class a,t_course_info cin,t_class c,t_user u where a.teacher_id = u.id and a.class_id=c.class_id and a.course_id=cin.id and if(?1 !='',cin.course_name=?1,1=1) and if(?2 !='',u.username=?2,1=1) and if(?3 !='',c.class_name=?3,1=1)",nativeQuery = true)
//    Page<ArrangeClassDto> findBycidAndtidAndclaidLike(String courseName,String teacherName, String className,Pageable pager);


    //查询课程参与人数
    @Query(value = "SELECT count(*) FROM t_arrange_class,t_student WHERE " +
            " t_arrange_class.class_id = t_student.class_id and t_arrange_class.course_id = ?",nativeQuery=true)
    int findNumberOfParticipants(int id);

}

//    select a.id,cin.course_name,u.username,c.class_name,a.arrange_start,a.arrange_end from t_arrange_class a,t_course_info cin,t_class c,t_user u where a.teacher_id = u.id and a.class_id=c.class_id and a.course_id=cin.id and if(?1 !=NULL,name=?1,1=1) and
























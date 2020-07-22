package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.DTO.KaoheModuleProgressDTO;
import com.coolwen.experimentplatformv2.model.ExpModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ExpModelRepository extends BaseRepository<ExpModel,Integer> {

    @Query("select e from ExpModel e where e.m_name=?1")
    List<ExpModel> findExpModelsByM_name(String m_name);

    @Query(value ="select * from t_exp_model",nativeQuery=true)
    Page<ExpModel> findAllexp(Pageable pageable1);

    @Query(value = "select t_exp_model.imageurl from t_exp_model where m_id= ?",nativeQuery=true)
    String findexpimg(int parseInt);

    //找到改学生考核model的相关信息

    @Query("select e.m_id from ExpModel e where e.m_id =?1")
    int findByM_id(int m_id);

    @Query("select exp from ExpModel exp,KaoheModel km where exp.m_id = km.m_id and km.m_id = ?1")
    ExpModel findExpModelsByKaoheMid(int mid);

    @Query("select e from ExpModel e order by e.m_id ASC ")
    Page<ExpModel> findExpModels(Pageable pageable);

    @Query("select new com.coolwen.experimentplatformv2.model.DTO.KaoheModuleProgressDTO(stu.stuXuehao,stu.stuName,cm.className,e.m_name,khs.mTeststate,khs.mTestScore,khs.mReportstate,khs.mReportScore,khs.mReportteacherstate,khs.mScore,e.m_id,cm.classId,e.report_type,stu.id,kh.kaohe_starttime,kh.kaohe_endtime) from CourseInfo ci left join Teacher t on ci.teacherId = t.id and t.course_id = ci.id left join ExpModel e on ci.id = e.courseId left join KaoheModel kh on e.m_id = kh.m_id left join ClassModel cm on t.person_name = cm.classTeacher left join Student stu on cm.classId = stu.classId left join KaoHeModelScore khs on stu.id = khs.stuId and kh.id = khs.tKaohemodleId where ci.id = ?1 and cm.classId = ?2 and e.m_id = ?3")
    Page<KaoheModuleProgressDTO> findExpModels(int course_id, int class_id, int m_id, PageRequest pageRequest);


    @Query("select exp from ExpModel exp where exp.courseId = ?1")
    Page<ExpModel> findOneCourseModelList(int courseId, Pageable pageable);

    @Query("select e from KaoheModel kh left join ExpModel e on e.m_id = kh.m_id where e.courseId = ?1")
    Page<ExpModel> findExpModelsByCourse_id(int courseId,PageRequest pageRequest);

    @Query("select e from ExpModel e left join CourseInfo c on e.courseId = c.id left join User u on u.id = c.teacherId where u.id=?1")
    Page<ExpModel> findAllByTeacher(Pageable pageable,int id);


    List<ExpModel> findByCourseId(int id);

    @Query("select c from ExpModel e,CourseInfo c where e.courseId = c.id and e.m_id = ?1")
    CourseInfo findCourseNameByMid(int mid);
}

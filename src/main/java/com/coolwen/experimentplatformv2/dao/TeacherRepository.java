package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeacherRepository extends BaseRepository<Teacher,Integer>, JpaSpecificationExecutor<Teacher> {
    @Query(value = "select * from t_teacher where id = ?",nativeQuery = true)
    Teacher findTeacherById(int id);

    @Query("select t from Teacher t,CourseInfo c where t.course_id = c.id and c.teacherId = ?1")
    Page<Teacher> findAllByUid(int uid,Pageable pageable);

    @Query("select t from Teacher t where t.course_id = ?1")
    List<Teacher> findByCourse_id(int CourseId);
}

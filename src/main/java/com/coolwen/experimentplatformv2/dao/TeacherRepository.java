package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.Teacher;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TeacherRepository extends BaseRepository<Teacher,Integer>, JpaSpecificationExecutor<Teacher> {
    @Query(value = "select * from t_teacher where id = ?",nativeQuery = true)
    Teacher findTeacherById(int id);

}

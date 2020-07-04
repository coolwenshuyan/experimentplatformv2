package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.model.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeacherRepository extends PagingAndSortingRepository<Teacher,Integer> {
    @Query(value = "select * from t_teacher where id = ?",nativeQuery = true)
    Teacher findTeacherById(int id);

}

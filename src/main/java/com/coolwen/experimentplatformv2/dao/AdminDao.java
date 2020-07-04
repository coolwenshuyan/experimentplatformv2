package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.model.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdminDao extends PagingAndSortingRepository<Admin, Integer> {
    @Query(value = "select * from t_admin where id=?",nativeQuery = true)
    Admin findTeacherById(int id);

    Admin findByUname(String name);
}

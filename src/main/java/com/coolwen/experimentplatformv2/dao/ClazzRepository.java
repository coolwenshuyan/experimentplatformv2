package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.ClassModel;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClazzRepository extends BaseRepository<ClassModel,Integer> {

    @Query("select  c from ClassModel c where c.className=?1")
    ClassModel findClazzByClass_name(String class_name);

    @Query("select c from ClassModel c,Student s where s.classId = c.classId and s.id = ?1")
    ClassModel findClassModelByStuId(int stuid);

    @Query("select c from ClassModel c where c.classIscurrent = false")
    List<ClassModel> findCurrentClass();

    @Query("select c from ClassModel c where c.classIscurrent = true ")
    List<ClassModel> findPassClass();
}

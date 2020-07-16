package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.ClassModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClazzService {

    Page<ClassModel> findClassList(int pageNum);

    ClassModel findById(int id);

    void saveClazz(ClassModel clazz);

    void deleteClazz(int id);

    List<ClassModel> findAllClass();

    ClassModel findClassModelByStuId(int stuid);

    List<ClassModel> findCurrentClass();

    ClassModel findClassModelByClassName(String className);


    List<ClassModel> findPassClass();

    Integer findStudentNumByClassId(int classId);

    Integer findStuMTestByClassId(int classId, int mid);

    Integer findStuMReportStateByClassId(int classid, int mid);

    List<ClassModel> findClassByClassIdsIn(List<Integer> integers);

}

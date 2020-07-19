package com.coolwen.experimentplatformv2.service;


import com.coolwen.experimentplatformv2.model.ClassModel;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto;
import com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto2;

import java.util.List;

public interface CourseInfoService {

    void add(CourseInfo effect);

    CourseInfo findById(int id);

    void delete(int id);

    List<CourseInfo> list();

    //根据教师ID获得该教师是课程负责人的课程对列
    List<CourseInfo> getclassByCharge(int teacher_id);

    //根据教师ID获得课程安排表中的课程对列
    List<CourseInfo> getclass_by_arrangeteacher(int teacher_id);

    //根据教师ID和课程ID获得课程安排表中对应的该课程的班级对列
    List<ClassModel> getclass_by_arrangecourseid(int teacher_id, int course_id);

    List<CourseInfo> findAll();


    List<CourseInfo> findByArrangeClassIds(List<Integer> arrageClassIds);

    CourseInfoDto findByCourseInfoIdAndClassId(int courseInfoId, int classId);

    List<CourseInfoDto2> findByArrangeCourseInfoDto2byClassId(int classId);

    List<ClassModel> getClassByCourseidUseridpass(String id, int courseId);
}

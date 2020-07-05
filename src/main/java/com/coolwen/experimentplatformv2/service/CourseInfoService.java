package com.coolwen.experimentplatformv2.service;


import com.coolwen.experimentplatformv2.model.CourseInfo;

import java.util.List;

public interface CourseInfoService {

    void add(CourseInfo effect);

    CourseInfo findById(int id);

    void delete(int id);

    List<CourseInfo> list();

    List<CourseInfo> getclassByCharge(int teacher_id);

    List<CourseInfo> getclass_by_arrangeteacher(int teacher_id);

}

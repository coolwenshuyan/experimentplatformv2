package com.coolwen.experimentplatformv2.service;


import com.coolwen.experimentplatformv2.model.CourseInfo;

public interface CourseInfoService {

    void add(CourseInfo effect);

    CourseInfo findById(int id);

    void delete(int id);
}
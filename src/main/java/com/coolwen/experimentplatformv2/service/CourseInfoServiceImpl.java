package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.CourseInfoRepository;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 朱治汶
 * @version 1.0
 * @date 2020/7/4 23:44
 **/
@Service
public class CourseInfoServiceImpl implements CourseInfoService {

    @Autowired
    CourseInfoRepository courseInfoRepository;

    @Override
    public void add(CourseInfo courseInfo) {
        courseInfoRepository.save(courseInfo);
    }

    @Override
    public CourseInfo findById(int id) {
        return courseInfoRepository.findById(id).get();
    }

    @Override
    public void delete(int id) {
        courseInfoRepository.deleteById(id);
    }

    @Override
    public List<CourseInfo> list() {
        return courseInfoRepository.findAll();
    }
}

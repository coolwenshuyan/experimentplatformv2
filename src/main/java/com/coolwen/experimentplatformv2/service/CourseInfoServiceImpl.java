package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.CourseInfoRepository;
import com.coolwen.experimentplatformv2.model.ClassModel;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto;
import com.coolwen.experimentplatformv2.model.DTO.CourseInfoDto2;
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
        return courseInfoRepository.findCourseInfoById(id);
    }

    @Override
    public void delete(int id) {
        courseInfoRepository.deleteById(id);
    }

    @Override
    public List<CourseInfo> list() {
        return courseInfoRepository.findAll();
    }

    @Override
    public List<CourseInfo> getclassByCharge(int teacher_id) {
        return courseInfoRepository.getclassByCharge(teacher_id);
    }

    @Override
    public List<CourseInfo> getclass_by_arrangeteacher(int teacher_id) {
        return courseInfoRepository.getclass_by_arrangeteacher(teacher_id);
    }

    @Override
    public List<ClassModel> getclass_by_arrangecourseid(int teacher_id, int course_id) {
        return courseInfoRepository.getclass_by_arrangecourseid(teacher_id, course_id);
    }

    @Override
    public List<CourseInfo> findAll() {
        return courseInfoRepository.findAll();
    }

    @Override
    public List<CourseInfo> findByArrangeClassIds(List<Integer> arrageClassIds) {
        return courseInfoRepository.findAllById(arrageClassIds);
    }

    @Override
    public CourseInfoDto findByCourseInfoIdAndClassId(int courseInfoId, int classId) {
        return courseInfoRepository.findByCourseInfoIdAndClassId(courseInfoId, classId);
    }

    @Override
    public List<CourseInfoDto2> findByArrangeCourseInfoDto2byClassId(int classId) {
        return courseInfoRepository.findByArrangeCourseInfoDto2byClassId(classId);
    }

    @Override
    public List<ClassModel> getClassByCourseidUseridpass(String teacherid, int courseId) {
        return courseInfoRepository.getClassByCourseidUseridpass(teacherid, courseId) ;
    }

    @Override
    public List<CourseInfo> findAllByCourseCode(String courseCode) {
        return courseInfoRepository.findAllByCourseCode(courseCode);
    }

    @Override
    public List<CourseInfo> findCourseInfosByclassid(int classid) {
        return courseInfoRepository.findCourseInfosByclassid(classid);
    }

//    @Override
//    public List<CourseInfoDto3> findByArrangeCourseInfoDto3byClassIdAndStuId(int classId, int stuid) {
//        return courseInfoRepository.findByArrangeCourseInfoDto3byClassIdAndStuId(classId, stuid);
//    }

    @Override
    public int findOneCourseInfoPassNum(int courseId) {
        return courseInfoRepository.findOneCourseInfoPassNum(courseId);
    }
}

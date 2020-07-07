package com.coolwen.experimentplatformv2.service;


import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ArrangeClassService {

    void add(ArrangeClass effect);

    ArrangeClass findById(int id);

    void delete(int id);

    Page<ArrangeClassDto> findByAll(Pageable pageable);

    Page<ArrangeClassDto> findBycidAndtidAndclaidLike(Integer pageNum, String courseName, String teacherName, String className);

    List<ArrangeClassDto> findByTeacherIdAndCourseId(int tid, int cid);

    List<Integer> findArrangeIdByTeacherIdAndCourseId(int tid, int cid);

    ArrangeClass findByCourseIdAndTeacherIdAndClassId(int courseId, int teacherId, int classId);
}

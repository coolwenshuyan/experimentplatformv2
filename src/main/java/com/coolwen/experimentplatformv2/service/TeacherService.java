package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {
    void add(Teacher teacher);

    void delete(int id);

    void updata(Teacher teacher);

    Teacher findById(int id);


    Page<Teacher> findAllByCourseId(int pageNum, int courseId);

    Page<Teacher> findAllByUid(int uid, Pageable pageable);

    List<Teacher> findByCourseId(int id);

    Page<Teacher> findAllByCourseIdAndTeacherId(int pageNum, int courseId, int teacherId);
}

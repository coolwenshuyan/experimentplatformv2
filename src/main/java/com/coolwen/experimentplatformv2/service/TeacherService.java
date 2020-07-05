package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.Teacher;
import org.springframework.data.domain.Page;

public interface TeacherService {
    void add(Teacher teacher);

    void delete(int id);

    void updata(Teacher teacher);

    Teacher findById(int id);


    Page<Teacher> findAllByCourseId(int pageNum, int courseId);
}

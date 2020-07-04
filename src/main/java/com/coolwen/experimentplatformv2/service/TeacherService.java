package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.Teacher;

public interface TeacherService {
    void add(Teacher teacher);

    void delete(int id);

    void updata(Teacher teacher);

    Teacher findById(int id);




}

package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.Student;

public interface SetStudentInfoService {

    Student findById(int id);

    void add(Student student);

    String findByClassName(int id);

}

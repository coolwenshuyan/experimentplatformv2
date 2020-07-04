package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.TeacherRepository;
import com.coolwen.experimentplatformv2.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Override
    public void add(Teacher teacher) {
        teacherRepository.save(teacher);

    }

    @Override
    public void delete(int id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public void updata(Teacher teacher) {

    }

    @Override
    public Teacher findById(int id) {
        Teacher teacher = new Teacher();
        teacher = teacherRepository.findTeacherById(id);
        return teacher;

    }
}

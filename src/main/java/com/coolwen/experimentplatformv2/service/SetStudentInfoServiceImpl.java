package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.ClassDao;
import com.coolwen.experimentplatformv2.dao.SetStudentInfoRepository;
import com.coolwen.experimentplatformv2.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetStudentInfoServiceImpl implements SetStudentInfoService {

    @Autowired
    SetStudentInfoRepository setStudentInfoRepository;

    @Autowired
    ClassDao classDao;

    @Override
    public Student findById(int id) {
        Student student = setStudentInfoRepository.findStudentById(id);
        return student;
    }

    @Override
    public void add(Student student) {
        setStudentInfoRepository.save(student);
    }

    @Override
    public String findByClassName(int id) {
        return classDao.findByClassName(id);
    }
}

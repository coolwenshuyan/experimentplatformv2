package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.ClazzRepository;
import com.coolwen.experimentplatformv2.model.ClassModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    ClazzRepository clazzRepository;
    @Override
    public Page<ClassModel> findClassList(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum,10);
        return clazzRepository.findAll(pageable);
    }

    @Override
    public ClassModel findById(int id) {
        return clazzRepository.findById(id).get();
    }

    @Override
    public void saveClazz(ClassModel clazz) {
        clazzRepository.save(clazz);
    }

    @Override
    public void deleteClazz(int id) {
        ClassModel clazz = findById(id);
        clazzRepository.delete(clazz);
    }

    @Override
    public List<ClassModel> findAllClass() {
        return clazzRepository.findAll();
    }

    @Override
    public ClassModel findClassModelByStuId(int stuid) {
        return clazzRepository.findClassModelByStuId(stuid);
    }

    @Override
    public List<ClassModel> findCurrentClass() {
        return clazzRepository.findCurrentClass();
    }

    @Override
    public ClassModel findClassModelByClassName(String className) {
        return clazzRepository.findClazzByClass_name(className);
    }

    @Override
    public List<ClassModel> findPassClass() {
        return clazzRepository.findPassClass();
    }

    @Override
    public Integer findStudentNumByClassId(int classId) {
        return clazzRepository.findStudentNumByClassId(classId);
    }

    @Override
    public Integer findStuMTestByClassId(int classId, int mid) {
        return clazzRepository.findStuMTestByClassId(classId,mid);
    }

    @Override
    public Integer findStuMReportStateByClassId(int classid, int mid) {
        return clazzRepository.findStuMReportStateByClassId(classid, mid);
    }


}

package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.ArrangeClassRepository;
import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto;
import com.coolwen.experimentplatformv2.model.Student;
import com.coolwen.experimentplatformv2.specification.SimpleSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author 朱治汶
 * @version 1.0
 * @date 2020/7/4 23:44
 **/
@Service
public class ArrangeClassServiceImpl implements ArrangeClassService {

    @Autowired
    ArrangeClassRepository arrangeClassRepository;

    @Value("${SimplePageBuilder.pageSize}")
    int size;

    @Override
    public void add(ArrangeClass arrangeClass) {
        arrangeClassRepository.save(arrangeClass);
    }

    @Override
    public ArrangeClass findById(int id) {
        return arrangeClassRepository.findById(id).get();
    }

    @Override
    public void delete(int id) {
        arrangeClassRepository.deleteById(id);
    }

    @Override
    public Page<ArrangeClassDto> findByAll(Pageable pageable) {
        return arrangeClassRepository.findByAll(pageable);
    }

    @Override
    public Page<ArrangeClassDto> findBycidAndtidAndclaidLike(Integer pageNum, String courseName,String teacherName, String className) {
        Pageable pager = PageRequest.of(pageNum, size);
        Page<ArrangeClassDto> arrangeClassDtos = arrangeClassRepository.findBycidAndtidAndclaidLike(courseName,teacherName,className,pager);
        return arrangeClassDtos;
    }

//    @Override
//    public Page<ArrangeClassDto> findBycidAndtidAndclaidLike(Pageable pageable, String courseId, String teacherId, String classId) {
//        Pageable pager = PageRequest.of(page, size);
//        Page<ArrangeClassDto> arrangeClassDtos = studentRepository.findAll(new SimpleSpecificationBuilder<Student>(
//                "classId", "=", classId)
//                .generateSpecification(), pager);
//        return arrangeClassDtos;
//    }
}

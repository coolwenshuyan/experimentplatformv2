package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.TeacherRepository;
import com.coolwen.experimentplatformv2.model.DTO.StuTotalScoreCurrentDTO;
import com.coolwen.experimentplatformv2.model.Student;
import com.coolwen.experimentplatformv2.model.Teacher;
import com.coolwen.experimentplatformv2.specification.SimpleSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Value("${SimplePageBuilder.pageSize}")
    int size;

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

    @Override
    public Page<Teacher> findAllByCourseId(int pageNum, int courseId) {

        Pageable pager = PageRequest.of(pageNum, size);
        Page<Teacher> teacherPage= teacherRepository.findAll(new SimpleSpecificationBuilder<Teacher>(
                "course_id", "=", courseId)
                .generateSpecification(), pager);
        return teacherPage;
    }

    @Override
    public Page<Teacher> findAllByUid(int uid,Pageable pageable) {
        return teacherRepository.findAllByUid(uid,pageable);
    }

    @Override
    public List<Teacher> findByCourseId(int id) {
        return teacherRepository.findByCourse_id(id);
    }
}

package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.ClazzRepository;
import com.coolwen.experimentplatformv2.dao.StudentRepository;
import com.coolwen.experimentplatformv2.kit.ShiroKit;
import com.coolwen.experimentplatformv2.model.ClassModel;
import com.coolwen.experimentplatformv2.model.DTO.*;
import com.coolwen.experimentplatformv2.model.Student;
import com.coolwen.experimentplatformv2.specification.SimpleSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Artell
 * @version 2020/5/15 21:31
 */

@Service
public class StudentServiceImpl implements StudentService {

    protected static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    @Autowired
    public StudentRepository studentRepository;


    @Autowired
    ClazzRepository clazzRepository;

    @Value("${SimplePageBuilder.pageSize}")
    int size;

    @Override
    public Page<Student> findAll(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 10);
        return studentRepository.findAll(pageable);
    }


    @Override
    public Student findByUname(String uName) {
        return studentRepository.findAllByStuUname(uName);
    }

    @Override
    public Student addStudent(Student student) {
//        Student stu = studentRepository.findByStuMobile(student.getStuMobile());
//        if (stu != null){
//            throw new UserException("手机号已被使用");
//        }
        if (ShiroKit.isEmpty(student.getStuXuehao()) || ShiroKit.isEmpty(student.getStuPassword())) {
            throw new RuntimeException("用户名或者密码不能为空！");
        }
        Student u = studentRepository.findByStuXuehao(student.getStuXuehao());
        if (u != null) {
            throw new RuntimeException("用户名已经存在!");
        }
        student.setStuPassword(ShiroKit.md5(student.getStuPassword(), student.getStuXuehao()));
        return studentRepository.save(student);
    }

    @Override
    public Page<StudentVo> findStudentsByStuCheckstate(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 10);
        return studentRepository.findStudentsByStuCheckstate(pageable);
    }

    @Override
    public StudentVo findStudentsByStuXuehao(String xuehao) {
        return studentRepository.findStudentsByStuXuehao(xuehao);
    }

    @Override
    public void deleteStudentById(int id) {
        Student student = findStudentById(id);
        studentRepository.delete(student);
    }

    @Override
    public Student findStudentById(int id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public StudentListDTO findStudentDTOById(int id) {
        return studentRepository.findStudentsById(id);
    }

    @Override
    public ClassModel findClazzByClassName(String className) {
        return clazzRepository.findClazzByClass_name(className);
    }

    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Page<Student> findToBeReviewedStudent(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 10);
        return studentRepository.findToBeReviewedStudent(pageable);
    }

    @Override
    public void deleteStudent(int id) {
        Student student = findStudentById(id);
        studentRepository.delete(student);
    }

    @Override
    public Student findStudentByStuXuehao(String xuehao) {
        return studentRepository.findStudentByStuXuehao(xuehao);
    }

    @Override
    public Student findclassStudentByStuXuehao(String xuehao) {
        return studentRepository.findclassStudentByStuXuehao(xuehao);
    }

    @Override
    public List<Student> findStudentByClassId(int class_id) {
        return studentRepository.findStudentByClassId(class_id);
    }

    @Override
    public Page<Student> pageStudentByClassId(int page, int classId) {
        Pageable pager = PageRequest.of(page, size);
        Page<Student> studentsPage = studentRepository.findAll(new SimpleSpecificationBuilder<Student>(
                "classId", "=", classId)
                .generateSpecification(), pager);
        return studentsPage;
    }


//    @Override
//    public Page<Student> pageStudentByClassId(int class_id) {
//        return studentRepository.pageStudentByClassId(class_id);
//    }

//    @Override
//    public Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTO(int pageNum) {
//        Pageable pager = PageRequest.of(pageNum, size);
//        Page<StuTotalScoreCurrentDTO> stuTotalScoreCurrentDTOSPage = studentRepository.findAll(new SimpleSpecificationBuilder<StuTotalScoreCurrentDTO>()
//                .generateSpecification(), pager);
////        userPage.
//        return stuTotalScoreCurrentDTOSPage;
//    }

    @Override
    public Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTO(int pageNum, String select_orderId) {
        Pageable pager = PageRequest.of(pageNum, size);
//        return studentRepository.listStuTotalScoreCurrentDTO(pager);
        Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTOPage;
        if (select_orderId == "" || select_orderId.equals("")) {
            listStuTotalScoreCurrentDTOPage = studentRepository.listStuTotalScoreCurrentDTO(pager);
        } else {
            select_orderId = "%" + select_orderId + "%";
            listStuTotalScoreCurrentDTOPage = studentRepository.listStuTotalScoreCurrentDTO(select_orderId, pager);
        }

        return listStuTotalScoreCurrentDTOPage;
    }


    @Override
    public Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTOByClassId(int pageNum, String select_orderId, int classId) {
        Pageable pager = PageRequest.of(pageNum, size);
//        return studentRepository.listStuTotalScoreCurrentDTO(pager);
        Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTOPage;
        listStuTotalScoreCurrentDTOPage = studentRepository.listStuTotalScoreCurrentDTOByClassId(classId, pager);


        return listStuTotalScoreCurrentDTOPage;
    }

    //往期成绩查询
    @Override
    public Page<StuTotalScoreCurrentDTO> listStuTotalScorePassDTO(int pageNum) {

        return null;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

//    @Override
//    public Page<Student> findStudentPageAndXuehaoAndClass(int page, String select_orderId, int classId) {
//        Pageable pager = PageRequest.of(page, size);
//        Page<Student> studentsPage = studentRepository.findAll(new SimpleSpecificationBuilder<Student>(
//                "stuXuehao", ":", select_orderId).add("classId",":",classId)
////                .add(SpecificationOperator.Join.and, key, operator, value);
//                .generateSpecification(), pager);
//        return studentsPage;
//    }

    @Override
    public Page<Student> findStudentPageAndXuehao(int page, String select_orderId) {
        Pageable pager = PageRequest.of(page, size);
        Page<Student> studentsPage = studentRepository.findAll(new SimpleSpecificationBuilder<Student>(
                "stuXuehao", ":", select_orderId)
                .generateSpecification(), pager);
        return studentsPage;
    }

    @Override
    public Page<StudentLastTestScoreDTO> listStudentLastTestAnswerDTO(int pageNum) {
        Pageable pager = PageRequest.of(pageNum, size);
        return studentRepository.listStudentLastTestScoreDTO(pager);
    }

    @Override
    public Page<StudentLastTestScoreDTO> listStudentLastTestScoreDTOBYClassID(int pageNum, int classId) {
        Pageable pager = PageRequest.of(pageNum, size);
        return studentRepository.listStudentLastTestScoreDTOByClassID(classId, pager);
    }

    @Override
    public Student findByStuMobile(String tel) {
        return studentRepository.findByStuMobile(tel);
    }

    @Override
    public Student findByStuXuehao(String stu_xuehao) {
        return studentRepository.findByStuXuehao(stu_xuehao);
    }

    @Override
    public List<Student> findStudentByNotClassId() {
        return studentRepository.findStudentByNotClassId();
    }

    @Override
    public List<Student> findStudentIsCurrentkaoheByStuid(int stuId) {

        return studentRepository.findStudentIsCurrentkaoheByStuid(stuId);
    }

    @Override
    public Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTOOfPass(int pageNum, String select_orderId) {
        Pageable pager = PageRequest.of(pageNum, size);
//        return studentRepository.listStuTotalScoreCurrentDTO(pager);
        Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTOPage;
        if (select_orderId == "" || select_orderId.equals("")) {
            logger.debug("不筛选");
            listStuTotalScoreCurrentDTOPage = studentRepository.listStuTotalScoreCurrentDTOOfPass(pager);
        } else {
            logger.debug("筛选");
            select_orderId = "%" + select_orderId + "%";
            listStuTotalScoreCurrentDTOPage = studentRepository.listStuTotalScoreCurrentDTOOfPassSelect(select_orderId, pager);
        }

        for (StuTotalScoreCurrentDTO i : listStuTotalScoreCurrentDTOPage) {
            logger.debug("listStuTotalScoreCurrentDTOPage!!!" + i);
        }

        return listStuTotalScoreCurrentDTOPage;
    }

    @Override
    public Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTOOfPassByClassId(int pageNum, String select_orderId, int classId) {
        Pageable pager = PageRequest.of(pageNum, size);
//        return studentRepository.listStuTotalScoreCurrentDTO(pager);
        Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTOPage = studentRepository.listStuTotalScoreCurrentDTOOfPassByClassId(classId, pager);

        return listStuTotalScoreCurrentDTOPage;
    }

    @Override
    public List<StuTotalScoreCurrentDTO> listAllStuTotalScoreCurrentDTOOfPass() {
        return studentRepository.listAllStuTotalScoreCurrentDTOOfPass();
    }

    @Override
    public Student findStudentByXueHao(String xuehao) {
        return studentRepository.findByStuXuehao(xuehao);
    }

    @Override
    public Page<Student> findStudentPageAndXuehaoAndClass(int page, int classId, String select_orderId) {
        Pageable pager = PageRequest.of(page, size);
        Page<Student> studentsPage = studentRepository.findAll(new SimpleSpecificationBuilder<Student>(
                "stuXuehao", ":", select_orderId).add("classId", "=", classId)
                .generateSpecification(), pager);
        return studentsPage;
    }


}

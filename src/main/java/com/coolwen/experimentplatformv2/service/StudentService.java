package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.ClassModel;
import com.coolwen.experimentplatformv2.model.DTO.*;
import com.coolwen.experimentplatformv2.model.Student;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author CoolWen
 * @version 2020-05-16 13:48
 */
public interface StudentService {
    Page<Student> findAll(int pageNum);

    public Student findByUname(String uName);

    public Student addStudent(Student student);

    public Student updateStudent(Student student);

    Page<StudentVo> findStudentsByStuCheckstate(int pageNum);

    Page<StudentVo> findStudentsByStuCheckstate(int pageNum, String xuehao);

    StudentVo findStudentsByStuXuehao(String xuehao);

    void deleteStudentById(int id);

    Student findStudentById(int id);

    StudentListDTO findStudentDTOById(int id);

    ClassModel findClazzByClassName(String className);

    void saveStudent(Student student);

    Page<Student> findToBeReviewedStudent(int pageNum);

    Page<Student> findToBeReviewedStudent(int pageNum, String stuXueHao);

    void deleteStudent(int id);

    Student findStudentByStuXuehao(String xuehao);

    Student findclassStudentByStuXuehao(String xuehao);

    List<Student> findStudentByClassId(int class_id);

    Page<Student> pageStudentByClassId(int class_id, int classid);

    Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTO(int pageNum, String select_orderId);

    Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTOByClassId(int pageNum, String select_orderId, int classId);

    Page<StuTotalScoreCurrentDTO> listStuTotalScorePassDTO(int pageNum);

    List<Student> findAll();

    List<Student> findByClassModelIdAndIsChecked(boolean isChecked, int classId);

//    List<Student> findAllBy

    public Page<Student> findStudentPageAndXuehao(int page, String select_orderId);

    Page<StudentLastTestScoreDTO> listStudentLastTestAnswerDTO(int pageNum);

    Page<StudentLastTestScoreDTO> listStudentLastTestScoreDTOBYClassID(int pageNum, int classId);

    Student findByStuMobile(String tel);

    Student findByStuXuehao(String stu_xuehao);

    List<Student> findStudentByNotClassId();

    List<Student> findStudentIsCurrentkaoheByStuid(int stuId);

    Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTOOfPass(int pageNum, String select_orderId);


    Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTOOfPassByClassId(int pageNum, String select_orderId, int classId);

    List<StuTotalScoreCurrentDTO> listAllStuTotalScoreCurrentDTOOfPass();

    Student findStudentByXueHao(String xuehao);


    public Page<Student> findStudentPageAndXuehaoAndClass(int page, int classId, String select_orderId);

    Page<StudentLastTestScoreDTO> listStudentLastTestAnswerDTO(int pageNum, int arrangeId);

    Page<StuTotalScoreCurrentDTO> listStuTotalScoreCurrentDTO(int pageNum, String select_orderId, int arrageId);

    Page<Student> pageStudentByArrangeId(Integer pageNum, int arrangeId);

    List<StudentTestScoreDTO> listStudentMTestAnswerDTOByArrangeId(int arrangeId);
}

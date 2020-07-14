package com.coolwen.experimentplatformv2.service;


import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeInfoDTO;
import com.coolwen.experimentplatformv2.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArrangeClassService {

    void add(ArrangeClass effect);

    ArrangeClass findById(int id);

    void delete(int id);

    Page<ArrangeClassDto> findByAll(Pageable pageable);

    Page<ArrangeClassDto> findBycidAndtidAndclaidLike(Integer pageNum, String courseName, String teacherName, String className);

    List<ArrangeClassDto> findByTeacherIdAndCourseId(int tid, int cid);

    List<Integer> findArrangeIdByTeacherIdAndCourseId(int tid, int cid);

    List<ArrangeInfoDTO> findArrangeInfoDTOByTeacherId(int teacherId);

    ArrangeClass findByCourseIdAndClassId(int courseId, int classId);

    List<Student> findStudentByarrangeID(int arrageid);

    //生成当期总评成绩表(单个学生)
    void currentResults(int studentId,int arrangeId);

    //删除一个考核模块
    void deleteKaohemodel(int id,int arrangeId);

    List<ArrangeClass> findByCourseID(int id);

    void deleteTotalScoreByArrangeId(int arrangeId);


    List<ArrangeClass> findByClassId(int classId);
}

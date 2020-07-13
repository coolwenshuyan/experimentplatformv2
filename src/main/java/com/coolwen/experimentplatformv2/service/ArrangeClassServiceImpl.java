package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.ArrangeClassRepository;
import com.coolwen.experimentplatformv2.dao.KaoHeModelScoreRepository;
import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto;
import com.coolwen.experimentplatformv2.model.KaoHeModelScore;
import com.coolwen.experimentplatformv2.model.TotalScoreCurrent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeInfoDTO;
import com.coolwen.experimentplatformv2.model.Student;
import com.coolwen.experimentplatformv2.specification.SimpleSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 朱治汶
 * @version 1.0
 * @date 2020/7/4 23:44
 **/
@Service
public class ArrangeClassServiceImpl implements ArrangeClassService {

    protected static final Logger logger = LoggerFactory.getLogger(ArrangeClassServiceImpl.class);

    @Autowired
    ArrangeClassRepository arrangeClassRepository;
    @Autowired
    TotalScoreCurrentService totalScoreCurrentService;
    @Autowired
    KaoheModelService kaoheModelService;
    @Autowired
    KaoHeModelScoreRepository kaoHeModelScoreRepository;
    @Autowired
    ModuleTestAnswerStuService moduleTestAnswerStuService;
    @Autowired
    CollegeReportService collegeReportService;
    @Autowired
    ReportAnswerService reportAnswerService;



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
    public Page<ArrangeClassDto> findBycidAndtidAndclaidLike(Integer pageNum, String courseName, String teacherName, String className) {
        Pageable pager = PageRequest.of(pageNum, size);
        Page<ArrangeClassDto> arrangeClassDtos = arrangeClassRepository.findBycidAndtidAndclaidLike(courseName, teacherName, className, pager);
        return arrangeClassDtos;
    }

    @Override
    public List<ArrangeClassDto> findByTeacherIdAndCourseId(int tid, int cid) {
        String sql = "SELECT  t1.id,t1.class_id,t1.course_id,t1.teacher_id,t2.class_name \n" +
                "FROM \n" +
                "t_arrange_class AS t1\n" +
                "LEFT JOIN t_class AS t2 ON (t1.class_id = t2.class_id) " +
                "where " +
                "t1.teacher_id=" + tid + " and t1.course_id=" + cid;
        List<Object[]> list = arrangeClassRepository.listBySQL(sql);
        List<ArrangeClassDto> arrangeClassList = new ArrayList<ArrangeClassDto>();
        for (Object[] objs : list) {
            ArrangeClassDto arrangeClassDto = new ArrangeClassDto();
            arrangeClassDto.setId((Integer) objs[0]);
            arrangeClassDto.setClassName((String) objs[4]);
            arrangeClassDto.setcIlassId((Integer) objs[1]);
            arrangeClassDto.setCourseId((Integer) objs[2]);
            arrangeClassDto.setTeacherId((Integer) objs[3]);
            logger.info(arrangeClassDto.toString());
            arrangeClassList.add(arrangeClassDto);
        }
        return arrangeClassList;
    }

    @Override
    public List<Integer> findArrangeIdByTeacherIdAndCourseId(int tid, int cid) {
        List<ArrangeClassDto> arrangeClassList = this.findByTeacherIdAndCourseId(tid, cid);
        List<Integer> integerList = arrangeClassList.stream().map(arrangeClassDto -> arrangeClassDto.getId()).collect(Collectors.toList());
        logger.info(integerList.toString());
        return integerList;
    }

    @Override
    public ArrangeClass findByCourseIdAndTeacherIdAndClassId(int courseId, int teacherId, int classId) {
        return arrangeClassRepository.findByCourseIdAndTeacherIdAndClassId(courseId,teacherId,classId);}

    @Override
    public List<Student> findStudentByarrangeID(int arrageid) {

        return arrangeClassRepository.findStudentByarrangeID(arrageid);
    }

    @Override
    public void currentResults(int studentId, int arrangeId) {
        TotalScoreCurrent t = totalScoreCurrentService.findTotalScoreCurrentByStuId2(studentId,arrangeId);
        if(t == null){
            TotalScoreCurrent totalScoreCurrent = new TotalScoreCurrent();
            totalScoreCurrent.setStuId(studentId);
            totalScoreCurrent.setArrageId(arrangeId);
            int kaoheNum = kaoheModelService.findKaoheNum();
            totalScoreCurrent.setKaoheNum(kaoheNum);
            totalScoreCurrentService.add(totalScoreCurrent);
        }
    }

    @Override
    public void deleteKaohemodel(int id, int arrangeId) {
        int mid = kaoheModelService.findById(id).getM_id();
        List<Student> studentslist = arrangeClassRepository.findStudentByarrangeID(arrangeId);
        for (Student temp_st : studentslist) {
            int studentid = temp_st.getId();
            //删除考核模块测试答案
            moduleTestAnswerStuService.deleteByStuIdModelId(mid, studentid);
            //删除学院版报告
            collegeReportService.deleteByStuIdModelId(mid, studentid);
            //删除自定义版答题报告
            reportAnswerService.deleteByStuIdModelId(mid, studentid);
            //删除考核模块学生记录表
            List<KaoHeModelScore> kaoHeModelScores = kaoHeModelScoreRepository.findKaoheModuleScoreByStuIdAndArrangeId(studentid,arrangeId);
            kaoHeModelScoreRepository.deleteAll(kaoHeModelScores);
        }
        // 删除表11中该考核模块
        kaoheModelService.delete(id);
    }

    @Override
    public void deleteArrangeClass(int studentId, int arrangeId) {
        //删除学生对应课程当期总评成绩
        totalScoreCurrentService.deleteTotalScoreCurrentByStuIdAndArrangeId(studentId,arrangeId);
    }

    @Override
    public List<ArrangeInfoDTO> findArrangeInfoDTOByTeacherId(int teacherId) {
        return arrangeClassRepository.findArrangeInfoDTOByTeacherId(teacherId);
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

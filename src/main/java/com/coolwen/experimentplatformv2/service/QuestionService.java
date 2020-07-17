package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.DTO.QuestionStudentDto;
import com.coolwen.experimentplatformv2.model.Question;
import com.coolwen.experimentplatformv2.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @author yellow
 */
public interface QuestionService {

    //添加问题
    void add(Question question);

    //删问题
    void delete(int id);

    //通过id查问题内容
    public String findByquestioncontent(int id);

    //    通过id查问题所有属性
    public Question findById(int id);

    //查所有问题
    public List<Question> getAll();

    //  通过seesion的用户名查DTO
    public Page<QuestionStudentDto> findAndUname(Pageable pageable);

    //查提问者
    public String findQuestionUname(int id);

    void setIsreply(boolean b);

    Page<QuestionStudentDto> findAllByCourseId(Pageable pageable, int courseId);

    Page<Question> findByCourse_idAndDic_datetimeAndIsreply(int pageNum, Integer courseId, boolean isreply);


    Page<QuestionStudentDto> findAllByCourseId(int pageNum, Question question);


    public Page<QuestionStudentDto> findByCourseIdAndTeacherId(int courseId, int teacherId, int pageNum);

    public Page<QuestionStudentDto> findByTeacherId(int teacherId, int pageNum);


}

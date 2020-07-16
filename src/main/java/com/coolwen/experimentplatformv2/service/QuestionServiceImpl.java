package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.QuestionRepository;
import com.coolwen.experimentplatformv2.model.DTO.QuestionStudentDto;
import com.coolwen.experimentplatformv2.model.Question;
import com.coolwen.experimentplatformv2.specification.SimplePageBuilder;
import com.coolwen.experimentplatformv2.specification.SimpleSortBuilder;
import com.coolwen.experimentplatformv2.specification.SimpleSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yellow
 */
@Service
public class QuestionServiceImpl implements QuestionService {


    @Value("${SimplePageBuilder.pageSize}")
    int size;
    //    注入
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void add(Question question) {
        questionRepository.save(question);
    }

    //    删除问题
    @Override
    public void delete(int id) {
        questionRepository.deleteById(id);
    }

    //通过id查问题内容
    @Override
    public String findByquestioncontent(int id) {
        return questionRepository.findByquestioncontent(id);
    }

    //    通过id查问题所有属性
    @Override
    public Question findById(int id) {
        Question question = new Question();
        question = questionRepository.findById(id);
        return question;
    }

    //查所有问题
    @Override
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    //  通过seesion的用户名查DTO
    @Override
    public Page<QuestionStudentDto> findAndUname(Pageable pageable) {
        return questionRepository.findAndUname(pageable);
    }

    //查提问者
    @Override
    public String findQuestionUname(int id) {
        return questionRepository.findQuestionUname(id);
    }

    @Override
    public void setIsreply(boolean b) {
        questionRepository.setIsreply(b);
    }

    @Override
    public Page<QuestionStudentDto> findAllByCourseId(Pageable pageable, int courseId) {
        return questionRepository.findAllByCourseId(courseId, pageable);
    }

    @Override
    public Page<Question> findByCourse_idAndDic_datetimeAndIsreply(int pageNum, Integer courseId, boolean isreply) {
        Pageable pager = PageRequest.of(pageNum, size);

//        Page<Question> questionPage = questionRepository.findAll(new SimpleSpecificationBuilder<Question>(
//                "course_id", "=", courseId)
//                .add("isreply", "=", isreply)
//                .add("dic_datetime", ">", date)
//                .generateSpecification(), pager);
        if (courseId == 0) {
            courseId = null;
        }
        Page<Question> stus = questionRepository.findAll(
                new SimpleSpecificationBuilder<Question>("course_id", "=", courseId).add("isreply", "=", isreply).generateSpecification(),
                SimplePageBuilder.generate(pageNum, SimpleSortBuilder.generateSort("questionDatetime")));
        return stus;
    }

    @Override
    public Page<QuestionStudentDto> findAllByCourseId(int pageNum, Question question) {
        Pageable pager = PageRequest.of(pageNum, size);
        if (question.getCourseId() == 0) {
            return this.findAndUname(pager);
        } else {
            return questionRepository.findByCourse_idAndIsreply(question.getCourseId(), question.getIsreply(), pager);
        }
    }

    @Override
    public Page<QuestionStudentDto> findByCourseIdAndTeacherId(int courseId, int teacherId, int pageNum) {
        Pageable pager = PageRequest.of(pageNum, size);
        return questionRepository.findByCourseIdAndTeacherId(courseId, teacherId, pager);
    }


}

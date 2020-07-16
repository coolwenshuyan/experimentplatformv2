package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.DTO.QuestionStudentDto;
import com.coolwen.experimentplatformv2.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 写的查询语句
 *
 * @author yellow
 */

public interface QuestionRepository extends BaseRepository<Question, Integer>, JpaSpecificationExecutor<Question> {

    //    查询问题内容
    @Query(value = "select content from t_question where id = ?", nativeQuery = true)
    public String findByquestioncontent(int id);

    //    查询问题全部属性
    @Query(value = "select * from t_question where id = ?", nativeQuery = true)
    public Question findById(int id);

    //    通过登录的seesion的用户名查出id等DTO里内容
    @Query(value = "select  new com.coolwen.experimentplatformv2.model.DTO.QuestionStudentDto" +
            "(q.id,q.sid,q.content,q.questionDatetime,s.stuUname,q.isreply,c.courseName,c.id,s.stuName) " +
            "from Question q,Student s,CourseInfo c " +
            "where q.sid=s.id and q.courseId=c.id order by q.isreply,q.questionDatetime desc ")
    public Page<QuestionStudentDto> findAndUname(Pageable pageable);

    //    查出提问学生姓名
    @Query(value = "select stu_uname from t_student where id = ？", nativeQuery = true)
    public String findQuestionUname(int id);

    //    当老师或学生回复时修改是否回复
    @Modifying
    @Transactional
    @Query(value = "update t_question set isreply = ? where id = ?", nativeQuery = true)
    void setIsreply(boolean b);

    @Query(value = "select  new com.coolwen.experimentplatformv2.model.DTO.QuestionStudentDto" +
            "(q.id,q.sid,q.content,q.questionDatetime,s.stuUname,q.isreply,c.courseName,c.id,s.stuName) " +
            "from Question q,Student s,CourseInfo c " +
            "where q.sid=s.id and q.courseId=c.id and q.courseId=?1 order by q.isreply,q.questionDatetime desc ")
    Page<QuestionStudentDto> findAllByCourseId(int courseId, Pageable pageable);

    @Query(value = "select  new com.coolwen.experimentplatformv2.model.DTO.QuestionStudentDto" +
            "(q.id,q.sid,q.content,q.questionDatetime,s.stuUname,q.isreply,c.courseName,c.id,s.stuName) " +
            "from Question q,Student s,CourseInfo c " +
            "where q.sid=s.id and q.courseId=c.id and q.courseId=?1 and q.isreply=?2 " +
            "order by q.isreply,q.questionDatetime desc ")
    public Page<QuestionStudentDto> findByCourse_idAndIsreply(int courseId, boolean isreply, Pageable pageable);

//    Page<Question> findAll(SimpleSpecificationBuilder<Question> questionSimpleSpecificationBuilder, Pageable pageable);

    @Query(value = "select  new com.coolwen.experimentplatformv2.model.DTO.QuestionStudentDto" +
            "(q.id,q.sid,q.content,q.questionDatetime,s.stuUname,q.isreply,c.courseName,c.id,s.stuName) " +
            "from Question q,Student s,CourseInfo c, User u ,ArrangeClass a " +
            "where q.sid=s.id and q.courseId=c.id and q.courseId=?1 and u.id=?2 and u.id=a.teacherId and a.courseId=q.courseId " +
            "order by q.isreply,q.questionDatetime desc ")
    public Page<QuestionStudentDto> findByCourseIdAndTeacherId(int courseId, int teacherId, Pageable pageable);
}

package com.coolwen.experimentplatformv2.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 表t_question
 *  @author yellow
 */
@Entity
@Table(name = "t_question")
public class Question {

//    id自增
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "question_id")
    @TableGenerator(name = "question_id",initialValue = 0,allocationSize = 1,table = "seq_table")
    private int id;
    private int sid;            //学生id
    private String content;     //问题内容
    private Date questionDatetime;    //记录时间
    private Boolean isreply;    //是否回复
    private int courseId;


    public Question(int sid, String content, Date questionDatetime, Boolean isreply) {
        this.sid = sid;
        this.content = content;
        this.questionDatetime = questionDatetime;
        this.isreply = isreply;
    }

    public Question() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getQuestionDatetime() {
        return questionDatetime;
    }

    public void setQuestionDatetime(Date questionDatetime) {
        this.questionDatetime = questionDatetime;
    }

    public Boolean getIsreply() {
        return isreply;
    }

    public void setIsreply(Boolean isreply) {
        this.isreply = isreply;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", sid=" + sid +
                ", content='" + content + '\'' +
                ", dic_datetime=" + questionDatetime +
                ", isreply=" + isreply +
                ", course_id=" + courseId +
                '}';
    }
}

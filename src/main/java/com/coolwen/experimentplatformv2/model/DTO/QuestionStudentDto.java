package com.coolwen.experimentplatformv2.model.DTO;

import java.util.Date;

/**
 * 建立一个通过用户名查id的DTO
 *
 * @author yellow
 */

public class QuestionStudentDto {

    private int id;   //t_question   id
    private int sid;  //学生id
    private String content;  //问题
    private Date questionDatetime;  //时间
    private String stu_uname;   //学生账号
    private Boolean isreply;    //是否回复
    private String courseName; //课程名字
    private int courseInfoId; //课程di

    private String stuName;   //学生昵称

    public QuestionStudentDto(int id, int sid, String content, Date questionDatetime, String stu_uname, Boolean isreply, String courseName, int courseInfoId, String stuName) {
        this.id = id;
        this.sid = sid;
        this.content = content;
        this.questionDatetime = questionDatetime;
        this.stu_uname = stu_uname;
        this.isreply = isreply;
        this.courseName = courseName;
        this.courseInfoId = courseInfoId;
        this.stuName = stuName;
    }

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

    public String getStu_uname() {
        return stu_uname;
    }

    public void setStu_uname(String stu_uname) {
        this.stu_uname = stu_uname;
    }

    public Boolean getIsreply() {
        return isreply;
    }

    public void setIsreply(Boolean isreply) {
        this.isreply = isreply;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseInfoId() {
        return courseInfoId;
    }

    public void setCourseInfoId(int courseInfoId) {
        this.courseInfoId = courseInfoId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @Override
    public String toString() {
        return "QuestionStudentDto{" +
                "id=" + id +
                ", sid=" + sid +
                ", content='" + content + '\'' +
                ", questionDatetime=" + questionDatetime +
                ", stu_uname='" + stu_uname + '\'' +
                ", isreply=" + isreply +
                ", courseName='" + courseName + '\'' +
                ", courseInfoId=" + courseInfoId +
                ", stuName='" + stuName + '\'' +
                '}';
    }
}

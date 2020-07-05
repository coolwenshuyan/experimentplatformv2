package com.coolwen.experimentplatformv2.model.DTO;

import java.util.Date;

/**
 * @author 朱治汶
 * @version 1.0
 * @date 2020/7/5 9:33
 **/
public class ArrangeClassDto {

    private int id;
    //课程名称
    private String courseName;
    //教师名字
    private String teacherName;
    //班级名
    private String className;
    //开始时间
    private Date arrangeStart;
    //结束时间
    private Date arrangeEnd;

    public ArrangeClassDto(int id, String courseName, String teacherName, String className, Date arrangeStart, Date arrangeEnd) {
        this.id = id;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.className = className;
        this.arrangeStart = arrangeStart;
        this.arrangeEnd = arrangeEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getArrangeStart() {
        return arrangeStart;
    }

    public void setArrangeStart(Date arrangeStart) {
        this.arrangeStart = arrangeStart;
    }

    public Date getArrangeEnd() {
        return arrangeEnd;
    }

    public void setArrangeEnd(Date arrangeEnd) {
        this.arrangeEnd = arrangeEnd;
    }
}

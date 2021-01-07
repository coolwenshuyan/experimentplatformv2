package com.coolwen.experimentplatformv2.model.DTO;

import java.util.Date;

/**
 * @author CoolWen
 * @version 2020-07-14 21:28
 */
public class CourseInfoDto2 {
    private int courseInfoId;

    private String courseName;

    private String courseCode;

    private int chargeteacherId;

    private String courseImgurl;

    private String courseIntruduce;

    private int arrangeId;

    private int classId;

    private int teacherId;

    private Date arrangeStart;

    private Date arrangeEnd;

    private String skAddress;

    public CourseInfoDto2() {
    }

    public CourseInfoDto2(int courseInfoId, String courseName, String courseCode, int chargeteacherId, String courseImgurl, String courseIntruduce, int arrangeId, int classId, int teacherId, Date arrangeStart, Date arrangeEnd, String skAddress) {
        this.courseInfoId = courseInfoId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.chargeteacherId = chargeteacherId;
        this.courseImgurl = courseImgurl;
        this.courseIntruduce = courseIntruduce;
        this.arrangeId = arrangeId;
        this.classId = classId;
        this.teacherId = teacherId;
        this.arrangeStart = arrangeStart;
        this.arrangeEnd = arrangeEnd;
        this.skAddress = skAddress;
    }

    public int getCourseInfoId() {
        return courseInfoId;
    }

    public void setCourseInfoId(int courseInfoId) {
        this.courseInfoId = courseInfoId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getChargeteacherId() {
        return chargeteacherId;
    }

    public void setChargeteacherId(int chargeteacherId) {
        this.chargeteacherId = chargeteacherId;
    }

    public String getCourseImgurl() {
        return courseImgurl;
    }

    public void setCourseImgurl(String courseImgurl) {
        this.courseImgurl = courseImgurl;
    }

    public String getCourseIntruduce() {
        return courseIntruduce;
    }

    public void setCourseIntruduce(String courseIntruduce) {
        this.courseIntruduce = courseIntruduce;
    }

    public int getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(int arrangeId) {
        this.arrangeId = arrangeId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
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

    public String getSkAddress() {
        return skAddress;
    }

    public void setSkAddress(String skAddress) {
        this.skAddress = skAddress;
    }

    @Override
    public String toString() {
        return "CourseInfoDto2{" +
                "courseInfoId=" + courseInfoId +
                ", courseName='" + courseName + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", chargeteacherId=" + chargeteacherId +
                ", courseImgurl='" + courseImgurl + '\'' +
                ", courseIntruduce='" + courseIntruduce + '\'' +
                ", arrangeId=" + arrangeId +
                ", classId=" + classId +
                ", teacherId=" + teacherId +
                ", arrangeStart=" + arrangeStart +
                ", arrangeEnd=" + arrangeEnd +
                ", skAddress='" + skAddress + '\'' +
                '}';
    }
}

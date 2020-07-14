package com.coolwen.experimentplatformv2.model.DTO;

/**
 * @author CoolWen
 * @version 2020-07-14 21:28
 */
public class CourseInfoDto {
    private int courseInfoId;

    private String courseName;

    private String courseCode;

    private int teacherId;

    private String courseImgurl;

    private String courseIntruduce;

    private int arrageClassId;

    public CourseInfoDto() {
    }

    public CourseInfoDto(int courseInfoId, String courseName, String courseCode, int teacherId, String courseImgurl, String courseIntruduce, int arrageClassId) {
        this.courseInfoId = courseInfoId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.teacherId = teacherId;
        this.courseImgurl = courseImgurl;
        this.courseIntruduce = courseIntruduce;
        this.arrageClassId = arrageClassId;
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

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
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

    public int getArrageClassId() {
        return arrageClassId;
    }

    public void setArrageClassId(int arrageClassId) {
        this.arrageClassId = arrageClassId;
    }

    @Override
    public String toString() {
        return "CourseInfoDto{" +
                "courseInfoId=" + courseInfoId +
                ", courseName='" + courseName + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", teacherId=" + teacherId +
                ", courseImgurl='" + courseImgurl + '\'' +
                ", courseIntruduce='" + courseIntruduce + '\'' +
                ", arrageClassId=" + arrageClassId +
                '}';
    }
}

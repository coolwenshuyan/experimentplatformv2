package com.coolwen.experimentplatformv2.model.DTO;

/**
 * @author 朱治汶
 * @version 2021-01-02 21:28
 */
public class CourseInfoDto5 {

    private int courseInfoId;

    private String courseName;

    private String courseCode;

    private String teacherName;

    private String courseImgurl;

    private String courseIntruduce;

    //课程学习人数
    private int numberOfLearning;

    public CourseInfoDto5() {
    }

    public CourseInfoDto5(int courseInfoId, String courseName, String courseCode, String teacherName,
                          String courseImgurl, String courseIntruduce) {
        this.courseInfoId = courseInfoId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.teacherName = teacherName;
        this.courseImgurl = courseImgurl;
        this.courseIntruduce = courseIntruduce;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public int getNumberOfLearning() {
        return numberOfLearning;
    }

    public void setNumberOfLearning(int numberOfLearning) {
        this.numberOfLearning = numberOfLearning;
    }
}

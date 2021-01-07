package com.coolwen.experimentplatformv2.model.DTO;

/**
 * @Description
 * @Author Metal
 * @Date 2021/1/4 16:50
 * @Version 1.0
 */
public class CourseInfoDto4 {
    private CourseInfoDto2 courseInfoDto2;
    private int NotFinishedkaoheNum;
    private String willBegin;
    private String WillFinfish;
    private float totalScore;

    public CourseInfoDto4(CourseInfoDto2 courseInfoDto2, int notFinishedkaoheNum, String willBegin, String willFinfish) {
        this.courseInfoDto2 = courseInfoDto2;
        NotFinishedkaoheNum = notFinishedkaoheNum;
        this.willBegin = willBegin;
        WillFinfish = willFinfish;

    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public CourseInfoDto2 getCourseInfoDto2() {
        return courseInfoDto2;
    }

    public void setCourseInfoDto2(CourseInfoDto2 courseInfoDto2) {
        this.courseInfoDto2 = courseInfoDto2;
    }

    public int getNotFinishedkaoheNum() {
        return NotFinishedkaoheNum;
    }

    public void setNotFinishedkaoheNum(int notFinishedkaoheNum) {
        NotFinishedkaoheNum = notFinishedkaoheNum;
    }

    public String getWillBegin() {
        return willBegin;
    }

    public void setWillBegin(String willBegin) {
        this.willBegin = willBegin;
    }

    public String getWillFinfish() {
        return WillFinfish;
    }

    public void setWillFinfish(String willFinfish) {
        WillFinfish = willFinfish;
    }
}

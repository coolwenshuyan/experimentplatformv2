package com.coolwen.experimentplatformv2.model.DTO;

/**
 * @Description
 * @Author Metal
 * @Date 2021/1/3 18:22
 * @Version 1.0
 */
public class CourseInfoDto3 {

   private CourseInfoDto2 courseInfoDto2;
    private int kaoheNum;

    private float totalScore;


    public CourseInfoDto3(CourseInfoDto2 courseInfoDto2, int kaoheNum, float totalScore) {
        this.courseInfoDto2 = courseInfoDto2;
        this.kaoheNum = kaoheNum;
        this.totalScore = totalScore;
    }


    public CourseInfoDto2 getCourseInfoDto2() {
        return courseInfoDto2;
    }

    public void setCourseInfoDto2(CourseInfoDto2 courseInfoDto2) {
        this.courseInfoDto2 = courseInfoDto2;
    }

    public int getKaoheNum() {
        return kaoheNum;
    }

    public void setKaoheNum(int kaoheNum) {
        this.kaoheNum = kaoheNum;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }
}

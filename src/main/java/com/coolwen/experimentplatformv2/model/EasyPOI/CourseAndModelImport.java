package com.coolwen.experimentplatformv2.model.EasyPOI;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @author Artell
 * @version 2020/7/26 18:05
 */


public class CourseAndModelImport {

    @Excel(name = "课程名称")
    private String courseName;

    @Excel(name = "课程代码")
    private String courseCode;

    @Excel(name = "实验名称")
    private String mName;

    @Excel(name = "实验负责人姓名")
    private String mManager;

    @Excel(name = "实验类型")
    private String mType;

    @Excel(name = "课时")
    private String classHour;

    @Excel(name = "实验介绍")
    private String introduce;

    @Excel(name = "实验目的")
    private String purpose;

    @Excel(name = "实验原理")
    private String principle;

    @Excel(name = "实验内容")
    private String mContent;

    @Excel(name = "实验资料描述")
    private String mDataIntro;

    @Excel(name = "实验步骤")
    private String mStep;

    @Excel(name = "实验平台链接")
    private String mInurl;

    public CourseAndModelImport() {
    }

    public CourseAndModelImport(String courseName, String courseCode, String mName, String mManager, String mType, String classHour, String introduce, String purpose, String principle, String mContent, String mDataIntro, String mStep, String mInurl) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.mName = mName;
        this.mManager = mManager;
        this.mType = mType;
        this.classHour = classHour;
        this.introduce = introduce;
        this.purpose = purpose;
        this.principle = principle;
        this.mContent = mContent;
        this.mDataIntro = mDataIntro;
        this.mStep = mStep;
        this.mInurl = mInurl;
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

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmManager() {
        return mManager;
    }

    public void setmManager(String mManager) {
        this.mManager = mManager;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getClassHour() {
        return classHour;
    }

    public void setClassHour(String classHour) {
        this.classHour = classHour;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmDataIntro() {
        return mDataIntro;
    }

    public void setmDataIntro(String mDataIntro) {
        this.mDataIntro = mDataIntro;
    }

    public String getmStep() {
        return mStep;
    }

    public void setmStep(String mStep) {
        this.mStep = mStep;
    }

    public String getmInurl() {
        return mInurl;
    }

    public void setmInurl(String mInurl) {
        this.mInurl = mInurl;
    }

    @Override
    public String toString() {
        return "CourseAndModelImport{" +
                "courseName='" + courseName + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", mName='" + mName + '\'' +
                ", mManager='" + mManager + '\'' +
                ", mType='" + mType + '\'' +
                ", classHour='" + classHour + '\'' +
                ", introduce='" + introduce + '\'' +
                ", purpose='" + purpose + '\'' +
                ", principle='" + principle + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mDataIntro='" + mDataIntro + '\'' +
                ", mStep='" + mStep + '\'' +
                ", mInurl='" + mInurl + '\'' +
                '}';
    }
}

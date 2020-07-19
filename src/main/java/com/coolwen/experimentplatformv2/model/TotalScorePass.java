package com.coolwen.experimentplatformv2.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Artell
 * @version 2020/5/12 18:27
 */

@Entity
@Table(name = "t_totalscore_pass")
public class TotalScorePass {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "t_totalscore_pass")
    @TableGenerator(name = "t_totalscore_pass", initialValue = 0, allocationSize = 1,table = "seq_table")
    private int id;

    @Column(name = "stu_id",length = 11,nullable = false)
    private int stuId;

    @Column(name = "kaohe_num",nullable = false)
    private int kaoheNum;

    @Column(name = "kaohe_name",columnDefinition = "text")
    private String kaoheName;

    @Column(name = "kaohe_mtestscore",columnDefinition = "text")
    private String kaoheMtestscore;

    @Column(name = "kaohe_mreportscore",columnDefinition = "text")
    private String kaoheMreportscore;

    @Column(name = "kaohe_mtestscore_baifengbi",columnDefinition = "text")
    private String kaoheMtestscoreBaifengbi;

    @Column(name = "kaohe_mreportscore_baifengbi",columnDefinition = "text")
    private String kaoheMreportscoreBaifengbi;

    @Column(name = "kaohe_mscale",columnDefinition = "text")
    private String kaoheMscale;

    @Column(name = "m_total_score",nullable = false,columnDefinition = "float default 0")
    private float mTotalScore;

    @Column(name = "test_score",nullable = false,columnDefinition = "float default 0")
    private float testScore;

    @Column(name = "test_baifenbi",nullable = false,columnDefinition = "float default 0")
    private float testBaifenbi;

    @Column(name = "kaohe_baifenbi",nullable = false,columnDefinition = "float default 0")
    private float kaoheBaifenbi;

    @Column(name = "total_score",nullable = false,columnDefinition = "float default 0")
    private float totalScore;

    @Column(name = "final_datetime",nullable = false)
    private Date finalDatetime;

    @Column(name = "course_name",nullable = false)
    private String courseName;

    @Column(name = "course_id",nullable = false)
    private int courseId;

    @Column(name = "class_name",nullable = false)
    private String className;

    @Column(name = "class_id",nullable = false)
    private int classId;

    @Column(name = "teacher_name",nullable = false)
    private String teacherName;


    @Column(name = "teacher_gonghao",nullable = false)
    private String teacherGongHao;

    public TotalScorePass() {
    }

    public TotalScorePass(int stuId, int kaoheNum, String kaoheName, String kaoheMtestscore, String kaoheMreportscore, String kaoheMtestscoreBaifengbi, String kaoheMreportscoreBaifengbi, String kaoheMscale, float mTotalScore, float testScore, float testBaifenbi, float kaoheBaifenbi, float totalScore, Date finalDatetime, String courseName, int courseId, String className, int classId, String teacherName, String teacherGongHao) {
        this.stuId = stuId;
        this.kaoheNum = kaoheNum;
        this.kaoheName = kaoheName;
        this.kaoheMtestscore = kaoheMtestscore;
        this.kaoheMreportscore = kaoheMreportscore;
        this.kaoheMtestscoreBaifengbi = kaoheMtestscoreBaifengbi;
        this.kaoheMreportscoreBaifengbi = kaoheMreportscoreBaifengbi;
        this.kaoheMscale = kaoheMscale;
        this.mTotalScore = mTotalScore;
        this.testScore = testScore;
        this.testBaifenbi = testBaifenbi;
        this.kaoheBaifenbi = kaoheBaifenbi;
        this.totalScore = totalScore;
        this.finalDatetime = finalDatetime;
        this.courseName = courseName;
        this.courseId = courseId;
        this.className = className;
        this.classId = classId;
        this.teacherName = teacherName;
        this.teacherGongHao = teacherGongHao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public int getKaoheNum() {
        return kaoheNum;
    }

    public void setKaoheNum(int kaoheNum) {
        this.kaoheNum = kaoheNum;
    }

    public String getKaoheName() {
        return kaoheName;
    }

    public void setKaoheName(String kaoheName) {
        this.kaoheName = kaoheName;
    }

    public String getKaoheMtestscore() {
        return kaoheMtestscore;
    }

    public void setKaoheMtestscore(String kaoheMtestscore) {
        this.kaoheMtestscore = kaoheMtestscore;
    }

    public String getKaoheMreportscore() {
        return kaoheMreportscore;
    }

    public void setKaoheMreportscore(String kaoheMreportscore) {
        this.kaoheMreportscore = kaoheMreportscore;
    }

    public String getKaoheMtestscoreBaifengbi() {
        return kaoheMtestscoreBaifengbi;
    }

    public void setKaoheMtestscoreBaifengbi(String kaoheMtestscoreBaifengbi) {
        this.kaoheMtestscoreBaifengbi = kaoheMtestscoreBaifengbi;
    }

    public String getKaoheMreportscoreBaifengbi() {
        return kaoheMreportscoreBaifengbi;
    }

    public void setKaoheMreportscoreBaifengbi(String kaoheMreportscoreBaifengbi) {
        this.kaoheMreportscoreBaifengbi = kaoheMreportscoreBaifengbi;
    }

    public String getKaoheMscale() {
        return kaoheMscale;
    }

    public void setKaoheMscale(String kaoheMscale) {
        this.kaoheMscale = kaoheMscale;
    }

    public float getmTotalScore() {
        return mTotalScore;
    }

    public void setmTotalScore(float mTotalScore) {
        this.mTotalScore = mTotalScore;
    }

    public float getTestScore() {
        return testScore;
    }

    public void setTestScore(float testScore) {
        this.testScore = testScore;
    }

    public float getTestBaifenbi() {
        return testBaifenbi;
    }

    public void setTestBaifenbi(float testBaifenbi) {
        this.testBaifenbi = testBaifenbi;
    }

    public float getKaoheBaifenbi() {
        return kaoheBaifenbi;
    }

    public void setKaoheBaifenbi(float kaoheBaifenbi) {
        this.kaoheBaifenbi = kaoheBaifenbi;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public Date getFinalDatetime() {
        return finalDatetime;
    }

    public void setFinalDatetime(Date finalDatetime) {
        this.finalDatetime = finalDatetime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherGongHao() {
        return teacherGongHao;
    }

    public void setTeacherGongHao(String teacherGongHao) {
        this.teacherGongHao = teacherGongHao;
    }
}

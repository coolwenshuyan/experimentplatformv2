package com.coolwen.experimentplatformv2.model.DTO;

import java.util.Date;

/**
 * @Description
 * @Author 张健银
 * @Date 2020/7/6 11:08
 * @Version 1.0
 */
public class KaoheModuleProgressDTO {
    private String stu_xuehao;
    private String stu_name;
    private String class_name;
    private String m_name;
    private boolean m_teststate;
    private float m_test_score;
    private boolean m_reportstate;
    private float m_report_score;
    //老师报告评分状态
    private boolean mReportteacherstate;
    //模块总分
    private float m_score;
    private int m_id;
    private int classId;
    //模块报告类型
    private boolean report_type;
    private int stuId;

    private Date kaohe_starttime;
    private Date kaohe_endtime;

    public KaoheModuleProgressDTO(String stu_xuehao, String stu_name, String class_name, String m_name, boolean m_teststate, float m_test_score, boolean m_reportstate, float m_report_score, boolean mReportteacherstate, float m_score,int m_mid,int classId,boolean report_type,int stuId,Date kaohe_starttime,Date kaohe_endtime) {
        this.stu_xuehao = stu_xuehao;
        this.stu_name = stu_name;
        this.class_name = class_name;
        this.m_name = m_name;
        this.m_teststate = m_teststate;
        this.m_test_score = m_test_score;
        this.m_reportstate = m_reportstate;
        this.m_report_score = m_report_score;
        this.mReportteacherstate = mReportteacherstate;
        this.m_score = m_score;
        this.m_id = m_mid;
        this.classId = classId;
        this.report_type = report_type;
        this.stuId = stuId;
        this.kaohe_starttime = kaohe_starttime;
        this.kaohe_endtime = kaohe_endtime;
    }

    public Date getKaohe_starttime() {
        return kaohe_starttime;
    }

    public void setKaohe_starttime(Date kaohe_starttime) {
        this.kaohe_starttime = kaohe_starttime;
    }

    public Date getKaohe_endtime() {
        return kaohe_endtime;
    }

    public void setKaohe_endtime(Date kaohe_endtime) {
        this.kaohe_endtime = kaohe_endtime;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public boolean isReport_type() {
        return report_type;
    }

    public void setReport_type(boolean report_type) {
        this.report_type = report_type;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getStu_xuehao() {
        return stu_xuehao;
    }

    public void setStu_xuehao(String stu_xuehao) {
        this.stu_xuehao = stu_xuehao;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public boolean isM_teststate() {
        return m_teststate;
    }

    public void setM_teststate(boolean m_teststate) {
        this.m_teststate = m_teststate;
    }

    public float getM_test_score() {
        return m_test_score;
    }

    public void setM_test_score(float m_test_score) {
        this.m_test_score = m_test_score;
    }

    public boolean isM_reportstate() {
        return m_reportstate;
    }

    public void setM_reportstate(boolean m_reportstate) {
        this.m_reportstate = m_reportstate;
    }

    public float getM_report_score() {
        return m_report_score;
    }

    public void setM_report_score(float m_report_score) {
        this.m_report_score = m_report_score;
    }

    public boolean ismReportteacherstate() {
        return mReportteacherstate;
    }

    public void setmReportteacherstate(boolean mReportteacherstate) {
        this.mReportteacherstate = mReportteacherstate;
    }

    public float getM_score() {
        return m_score;
    }

    public void setM_score(float m_score) {
        this.m_score = m_score;
    }

    @Override
    public String toString() {
        return "KaoheModuleProgressDTO{" +
                "stu_xuehao='" + stu_xuehao + '\'' +
                ", stu_name='" + stu_name + '\'' +
                ", class_name='" + class_name + '\'' +
                ", m_name='" + m_name + '\'' +
                ", m_teststate=" + m_teststate +
                ", m_test_score=" + m_test_score +
                ", m_reportstate=" + m_reportstate +
                ", m_report_score=" + m_report_score +
                ", mReportteacherstate=" + mReportteacherstate +
                ", m_score=" + m_score +
                ", m_id=" + m_id +
                ", classId=" + classId +
                ", report_type=" + report_type +
                ", stuId=" + stuId +
                ", kaohe_starttime=" + kaohe_starttime +
                ", kaohe_endtime=" + kaohe_endtime +
                '}';
    }
}

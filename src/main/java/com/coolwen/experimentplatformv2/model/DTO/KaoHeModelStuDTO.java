package com.coolwen.experimentplatformv2.model.DTO;

import java.util.Date;

public class KaoHeModelStuDTO {

    private int m_id;//模块id
    private int stu_id;//学生id

    private Boolean m_teststate;//测试完成状态
    private Boolean m_reportstate;//报告完成状态

    private float m_scale;//总成绩中该模块权重
    private float m_score;//模块成绩

    private String m_name;//模块名字
    private String m_imageurl;//模块图片
    private String inurl;
    private boolean report_type;
    private Date kaohe_starttime;
    private Date kaohe_endtime;

    public KaoHeModelStuDTO(int m_id, int stu_id, Boolean m_teststate, Boolean m_reportstate, float m_scale, float m_score, String m_name, String m_imageurl, String inurl, boolean report_type, Date kaohe_starttime, Date kaohe_endtime) {
        this.m_id = m_id;
        this.stu_id = stu_id;
        this.m_teststate = m_teststate;
        this.m_reportstate = m_reportstate;
        this.m_scale = m_scale;
        this.m_score = m_score;
        this.m_name = m_name;
        this.m_imageurl = m_imageurl;
        this.inurl = inurl;
        this.report_type = report_type;
        this.kaohe_starttime = kaohe_starttime;
        this.kaohe_endtime = kaohe_endtime;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public int getStu_id() {
        return stu_id;
    }

    public void setStu_id(int stu_id) {
        this.stu_id = stu_id;
    }

    public Boolean getM_teststate() {
        return m_teststate;
    }

    public void setM_teststate(Boolean m_teststate) {
        this.m_teststate = m_teststate;
    }

    public Boolean getM_reportstate() {
        return m_reportstate;
    }

    public void setM_reportstate(Boolean m_reportstate) {
        this.m_reportstate = m_reportstate;
    }

    public float getM_scale() {
        return m_scale;
    }

    public void setM_scale(float m_scale) {
        this.m_scale = m_scale;
    }

    public float getM_score() {
        return m_score;
    }

    public void setM_score(float m_score) {
        this.m_score = m_score;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_imageurl() {
        return m_imageurl;
    }

    public void setM_imageurl(String m_imageurl) {
        this.m_imageurl = m_imageurl;
    }

    public String getInurl() {
        return inurl;
    }

    public void setInurl(String inurl) {
        this.inurl = inurl;
    }

    public boolean isReport_type() {
        return report_type;
    }

    public void setReport_type(boolean report_type) {
        this.report_type = report_type;
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
}
package com.coolwen.experimentplatformv2.model.DTO;

/**
 * @Description
 * @Author Metal
 * @Date 2020/7/8 18:52
 * @Version 1.0
 */
public class KaoheProgressMainDTO {

    private int m_id;
    private String m_name;
    private String imageurl;
    private int classTotalNum;
    //测试未完成人数
    private int mTestFalseNum;
    //报告未完成
    private int mReportFalseNum;

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getClassTotalNum() {
        return classTotalNum;
    }

    public void setClassTotalNum(int classTotalNum) {
        this.classTotalNum = classTotalNum;
    }

    public int getmTestFalseNum() {
        return mTestFalseNum;
    }

    public void setmTestFalseNum(int mTestFalseNum) {
        this.mTestFalseNum = mTestFalseNum;
    }

    public int getmReportFalseNum() {
        return mReportFalseNum;
    }

    public void setmReportFalseNum(int mReportFalseNum) {
        this.mReportFalseNum = mReportFalseNum;
    }

    @Override
    public String toString() {
        return "KaoheProgressMainDTO{" +
                "m_id=" + m_id +
                ", m_name='" + m_name + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", classTotalNum=" + classTotalNum +
                ", mTestFalseNum=" + mTestFalseNum +
                ", mReportFalseNum=" + mReportFalseNum +
                '}';
    }

    public KaoheProgressMainDTO(int m_id, String m_name, String imageurl, int classTotalNum, int mTestFalseNum, int mReportFalseNum) {
        this.m_id = m_id;
        this.m_name = m_name;
        this.imageurl = imageurl;
        this.classTotalNum = classTotalNum;
        this.mTestFalseNum = mTestFalseNum;
        this.mReportFalseNum = mReportFalseNum;
    }
}

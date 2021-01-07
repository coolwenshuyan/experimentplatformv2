package com.coolwen.experimentplatformv2.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 王雨来
 * @version 2020/12/30 9:53
 */

@Entity
@Table(name = "t_exp_log")
public class ExpLog {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "exp_log_id")
    @TableGenerator(name = "exp_log_id", initialValue = 0, allocationSize = 1, table = "seq_table")
    @Column(name = "log_id")
    //记录id
    private int logId;
    //学生ID
    @Column(nullable = false)
    private int studentId;

    //安排ID
    @Column(nullable = true)
    private int arrangeId;

    //实验ID
    @Column(nullable = true)
    private int expId;

    //记录时间
    @Column(nullable = false)
    private Date logTime;

    //ip地址
    @Column(name = "ip")
    private String ip;

    //任务类型
    @Column(nullable = true)//length = 2暂时不限定长度
    private String actionType;

    public ExpLog(int studentId, int arrangeId, int expId, Date logTime, String actionType) {
        this.studentId = studentId;
        this.arrangeId = arrangeId;
        this.expId = expId;
        this.logTime = logTime;
        this.actionType = actionType;
    }

    public ExpLog() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(int arrangeId) {
        this.arrangeId = arrangeId;
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return "ExpLog{" +
                "logId=" + logId +
                ", studentId=" + studentId +
                ", arrangeId=" + arrangeId +
                ", expId=" + expId +
                ", logTime=" + logTime +
                ", ip='" + ip + '\'' +
                ", actionType='" + actionType + '\'' +
                '}';
    }
}
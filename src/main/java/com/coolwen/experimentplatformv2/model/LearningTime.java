package com.coolwen.experimentplatformv2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Artell
 * @version 2020/11/5 2:22
 */

@Entity
@Table(name = "t_learning_time")
public class LearningTime {
    @Id
    //学生ID
    @Column(name = "sid")
    private int sid;

    //登录状态(0表示未登录 1表示已经登录)
    @Column(name = "is_online",columnDefinition = "Boolean default false")
    private Boolean isOnline;

    //登录时间
    @Column(name = "login_time")
    private Date logInTime;

    //退出时间
    @Column(name = "postset_time")
    private Date postSetTime;

    //累计时长min
    @Column(name = "total_time",columnDefinition = "int default 0")
    private int totalTime;

    public LearningTime() {
    }


    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Date getLogInTime() {
        return logInTime;
    }

    public void setLogInTime(Date logInTime) {
        this.logInTime = logInTime;
    }

    public Date getPostSetTime() {
        return postSetTime;
    }

    public void setPostSetTime(Date postSetTime) {
        this.postSetTime = postSetTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "LearningTime{" +
                "sid=" + sid +
                ", isOnline=" + isOnline +
                ", logInTime=" + logInTime +
                ", postSetTime=" + postSetTime +
                ", totalTime=" + totalTime +
                '}';
    }
}

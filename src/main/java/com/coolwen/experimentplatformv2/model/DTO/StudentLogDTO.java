package com.coolwen.experimentplatformv2.model.DTO;

import java.util.Date;

/**
 * @Description
 * @Author 张健银
 * @Date 2020/7/6 11:08
 * @Version 1.0
 */
public class StudentLogDTO {
    private String stu_xuehao;
    private String stu_name;
    private Date date;
    private String ip;
    private String action;
    private int arrangeid;
    private int exp_id;
    private String exp_name;

    public StudentLogDTO(String stu_xuehao, String stu_name, Date date, String ip, String action, int arrangeid, int exp_id, String exp_name) {
        this.stu_xuehao = stu_xuehao;
        this.stu_name = stu_name;
        this.date = date;
        this.ip = ip;
        this.action = action;
        this.arrangeid = arrangeid;
        this.exp_id = exp_id;
        this.exp_name = exp_name;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getArrangeid() {
        return arrangeid;
    }

    public void setArrangeid(int arrangeid) {
        this.arrangeid = arrangeid;
    }

    public int getExp_id() {
        return exp_id;
    }

    public void setExp_id(int exp_id) {
        this.exp_id = exp_id;
    }

    public String getExp_name() {
        return exp_name;
    }

    public void setExp_name(String exp_name) {
        this.exp_name = exp_name;
    }

    @Override
    public String toString() {
        return "StudentLogDTO{" +
                "stu_xuehao='" + stu_xuehao + '\'' +
                ", stu_name='" + stu_name + '\'' +
                ", date=" + date +
                ", ip='" + ip + '\'' +
                ", action='" + action + '\'' +
                ", arrangeid=" + arrangeid +
                ", exp_id=" + exp_id +
                ", exp_name='" + exp_name + '\'' +
                '}';
    }
}

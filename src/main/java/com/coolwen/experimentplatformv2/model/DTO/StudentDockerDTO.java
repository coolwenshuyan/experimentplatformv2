package com.coolwen.experimentplatformv2.model.DTO;


/**
 * @Description
 * @Author Metal
 * @Date 2020/7/23 12:19
 * @Version 1.0
 */
public class StudentDockerDTO {
    private int id;
    //登陆账号
    private String stuUname;
    //密码
    private String stuPassword;
    //学生姓名昵称
    private String stuName;
    //学号
    private String stuXuehao;
    //手机
    private String stuMobile;
    //账号状态
    private boolean stuCheckstate;
    //是否本校
    private boolean stuIsinschool;
    //所属班级id
    private int classId;

    private Boolean dockerState;

    public StudentDockerDTO(int id, String stuUname, String stuPassword, String stuName, String stuXuehao, String stuMobile, boolean stuCheckstate, boolean stuIsinschool, int classId, boolean dockerState) {
        this.id = id;
        this.stuUname = stuUname;
        this.stuPassword = stuPassword;
        this.stuName = stuName;
        this.stuXuehao = stuXuehao;
        this.stuMobile = stuMobile;
        this.stuCheckstate = stuCheckstate;
        this.stuIsinschool = stuIsinschool;
        this.classId = classId;
        this.dockerState = dockerState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStuUname() {
        return stuUname;
    }

    public void setStuUname(String stuUname) {
        this.stuUname = stuUname;
    }

    public String getStuPassword() {
        return stuPassword;
    }

    public void setStuPassword(String stuPassword) {
        this.stuPassword = stuPassword;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuXuehao() {
        return stuXuehao;
    }

    public void setStuXuehao(String stuXuehao) {
        this.stuXuehao = stuXuehao;
    }

    public String getStuMobile() {
        return stuMobile;
    }

    public void setStuMobile(String stuMobile) {
        this.stuMobile = stuMobile;
    }

    public boolean isStuCheckstate() {
        return stuCheckstate;
    }

    public void setStuCheckstate(boolean stuCheckstate) {
        this.stuCheckstate = stuCheckstate;
    }

    public boolean isStuIsinschool() {
        return stuIsinschool;
    }

    public void setStuIsinschool(boolean stuIsinschool) {
        this.stuIsinschool = stuIsinschool;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public Boolean getDockerState() {
        return dockerState;
    }

    public void setDockerState(Boolean dockerState) {
        this.dockerState = dockerState;
    }

    @Override
    public String toString() {
        return "StudentDockerDTO{" +
                "id=" + id +
                ", stuUname='" + stuUname + '\'' +
                ", stuPassword='" + stuPassword + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuXuehao='" + stuXuehao + '\'' +
                ", stuMobile='" + stuMobile + '\'' +
                ", stuCheckstate=" + stuCheckstate +
                ", stuIsinschool=" + stuIsinschool +
                ", classId=" + classId +
                ", dockerState=" + dockerState +
                '}';
    }
}

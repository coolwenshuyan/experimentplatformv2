package com.coolwen.experimentplatformv2.model;

import javax.persistence.*;

/**
 * @author CoolWen
 * @version 2018-10-31 6:50
 */
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_id")
    @TableGenerator(name = "user_id", initialValue = 0, allocationSize = 1,table = "seq_table")
    private int id;
    //老师的身份证号码，登陆账号
    private String username;
    //老师的姓名
    private String nickname;
    private String password;
    private Boolean status;

    private String gonghao;

    //教师签名图片
    private String signature;


    public User() {
    }

    public User(String username, String nickname, String password, Boolean status) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getGonghao() {
        return gonghao;
    }

    public void setGonghao(String gonghao) {
        this.gonghao = gonghao;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", gonghao='" + gonghao + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}

package com.coolwen.experimentplatformv2.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 表t_reply
 *  @author yellow
 */
@Entity
@Table(name = "t_reply")
public class Reply {

//    id自增
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "reply_id")
    @TableGenerator(name = "reply_id",initialValue = 0,allocationSize = 1,table = "seq_table")
    public int id;
    private int qid;            //问题id
    private String replyPname; //回复用户名
    private String content;     //回复内容
    private Date dicDatetime;  //记录时间

    public Reply(int qid, String replyPname, String content, Date dicDatetime) {
        this.qid = qid;
        this.replyPname = replyPname;
        this.content = content;
        this.dicDatetime = dicDatetime;
    }

    public Reply() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getReplyPname() {
        return replyPname;
    }

    public void setReplyPname(String replyPname) {
        this.replyPname = replyPname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDicDatetime() {
        return dicDatetime;
    }

    public void setDicDatetime(Date dicDatetime) {
        this.dicDatetime = dicDatetime;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", qid=" + qid +
                ", reply_pname='" + replyPname + '\'' +
                ", content='" + content + '\'' +
                ", dic_datetime=" + dicDatetime +
                '}';
    }
}

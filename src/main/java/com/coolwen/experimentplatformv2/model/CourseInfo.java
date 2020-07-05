package com.coolwen.experimentplatformv2.model;

import javax.persistence.*;

/**
 * @author 朱治汶
 * @version 1.0
 * @date 2020/6/13 22:06
 **/
@Entity
@Table(name = "t_course_info")
public class CourseInfo {
    @Id //自动获取id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "courseinfo_id")
    @TableGenerator(name = "courseinfo_id", initialValue = 0, allocationSize = 1, table = "seq_table")
    private int id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "teacher_id")
    private int teacherId;

    @Column(name = "course_imgurl")
    private String courseImgurl;

    @Column(name = "course_intruduce")
    private String courseIntruduce;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseImgurl() {
        return courseImgurl;
    }

    public void setCourseImgurl(String courseImgurl) {
        this.courseImgurl = courseImgurl;
    }

    public String getCourseIntruduce() {
        return courseIntruduce;
    }

    public void setCourseIntruduce(String courseIntruduce) {
        this.courseIntruduce = courseIntruduce;
    }
}

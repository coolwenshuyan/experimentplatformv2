package com.coolwen.experimentplatformv2.model;

import javax.persistence.*;

/**
 * @author 朱治汶
 * @version 1.0
 * @className ArrangeClass
 * @description TODO
 * @date 2020/7/4 23:34
 **/
@Entity
@Table(name = "t_arrange_class")
public class ArrangeClass {
    @Id //自动获取id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "arrangeclass_id")
    @TableGenerator(name = "arrangeclass_id", initialValue = 0, allocationSize = 1, table = "seq_table")
    private int id;

    @Column(name = "course_id")
    private String courseId;

    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name = "class_id")
    private String classId;

    @Column(name = "arrange_start")
    private String arrangeStart;

    @Column(name = "arrange_end")
    private String arrangeEnd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getArrangeStart() {
        return arrangeStart;
    }

    public void setArrangeStart(String arrangeStart) {
        this.arrangeStart = arrangeStart;
    }

    public String getArrangeEnd() {
        return arrangeEnd;
    }

    public void setArrangeEnd(String arrangeEnd) {
        this.arrangeEnd = arrangeEnd;
    }
}

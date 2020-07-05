package com.coolwen.experimentplatformv2.model;

import javax.persistence.*;
import java.util.Date;

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
    private int courseId;

    @Column(name = "teacher_id")
    private int teacherId;

    @Column(name = "class_id")
    private int classId;

    @Column(name = "arrange_start")
    private Date arrangeStart;

    @Column(name = "arrange_end")
    private Date arrangeEnd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public Date getArrangeStart() {
        return arrangeStart;
    }

    public void setArrangeStart(Date arrangeStart) {
        this.arrangeStart = arrangeStart;
    }

    public Date getArrangeEnd() {
        return arrangeEnd;
    }

    public void setArrangeEnd(Date arrangeEnd) {
        this.arrangeEnd = arrangeEnd;
    }
}

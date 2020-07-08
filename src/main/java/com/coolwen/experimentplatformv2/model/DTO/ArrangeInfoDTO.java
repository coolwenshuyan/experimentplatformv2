package com.coolwen.experimentplatformv2.model.DTO;

import java.util.Date;

/**
 * @author Artell
 * @version 2020/7/7 15:57
 */


public class ArrangeInfoDTO {
    private int arrangeId;
    private String courseName;
    private String className;
    private String teacherName;
    private String skAddress;
    private Date arrange_start;
    private Date arrange_end;

    public ArrangeInfoDTO() {
    }

    public ArrangeInfoDTO(int arrangeId, String courseName, String className, String teacherName, String skAddress, Date arrange_start, Date arrange_end) {
        this.arrangeId = arrangeId;
        this.courseName = courseName;
        this.className = className;
        this.teacherName = teacherName;
        this.skAddress = skAddress;
        this.arrange_start = arrange_start;
        this.arrange_end = arrange_end;
    }

    public int getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(int arrangeId) {
        this.arrangeId = arrangeId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSkAddress() {
        return skAddress;
    }

    public void setSkAddress(String skAddress) {
        this.skAddress = skAddress;
    }

    public Date getArrange_start() {
        return arrange_start;
    }

    public void setArrange_start(Date arrange_start) {
        this.arrange_start = arrange_start;
    }

    public Date getArrange_end() {
        return arrange_end;
    }

    public void setArrange_end(Date arrange_end) {
        this.arrange_end = arrange_end;
    }

    @Override
    public String toString() {
        return "ArrangeInfoDTO{" +
                "arrangeId=" + arrangeId +
                ", courseName='" + courseName + '\'' +
                ", className='" + className + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", skAddress='" + skAddress + '\'' +
                ", arrange_start=" + arrange_start +
                ", arrange_end=" + arrange_end +
                '}';
    }
}

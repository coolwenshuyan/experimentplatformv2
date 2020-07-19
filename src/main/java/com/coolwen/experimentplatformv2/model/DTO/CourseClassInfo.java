package com.coolwen.experimentplatformv2.model.DTO;

public class CourseClassInfo {

    private String courseName;

    private String className;

    private int courseId;

    private int classId;

    public CourseClassInfo() {
    }

    public CourseClassInfo(String courseName, String className, int courseId, int classId) {
        this.courseName = courseName;
        this.className = className;
        this.courseId = courseId;
        this.classId = classId;
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "CourseClassInfo{" +
                "courseName='" + courseName + '\'' +
                ", className='" + className + '\'' +
                ", courseId=" + courseId +
                ", classId=" + classId +
                '}';
    }
}

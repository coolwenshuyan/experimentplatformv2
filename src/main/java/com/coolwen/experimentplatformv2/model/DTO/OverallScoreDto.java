package com.coolwen.experimentplatformv2.model.DTO;

/**
 * @author 朱治汶
 * @version 1.0
 * @date 2021/1/5 9:33
 **/
public class OverallScoreDto {

    //学号
    private String stuXueHao;
    //平均成绩
    private String averageScores;
    //时长
    private String learningTime;

    public OverallScoreDto() {
    }

    public OverallScoreDto(String stuXueHao, String averageScores, String learningTime) {
        this.stuXueHao = stuXueHao;
        this.averageScores = averageScores;
        this.learningTime = learningTime;
    }

    public OverallScoreDto(String stuXueHao, String averageScores, Object o) {
        this.stuXueHao = stuXueHao;
        this.averageScores = averageScores;
        this.learningTime = (String) o;
    }

    public String getStuXueHao() {
        return stuXueHao;
    }

    public void setStuXueHao(String stuXueHao) {
        this.stuXueHao = stuXueHao;
    }

    public String getAverageScores() {
        return averageScores;
    }

    public void setAverageScores(String averageScores) {
        this.averageScores = averageScores;
    }

    public String getLearningTime() {
        return learningTime;
    }

    public void setLearningTime(String learningTime) {
        this.learningTime = learningTime;
    }

    @Override
    public String toString() {
        return "OverallScoreDto{" +
                "stuXueHao='" + stuXueHao + '\'' +
                ", averageScores='" + averageScores + '\'' +
                ", learningTime='" + learningTime + '\'' +
                '}';
    }
}

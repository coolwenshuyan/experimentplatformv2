package com.coolwen.experimentplatformv2.model.EasyPOI;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @author Artell
 * @version 2020/7/23 17:38
 */


public class QuestionsImport {

    @Excel(name = "序号")
    private String order;

    @Excel(name = "题型")
    private String types;

    @Excel(name = "分数")
    private String questScore;


    @Excel(name = "题干")
    private String stem;

    @Excel(name = "正确答案")
    private String rightAnswers;

    @Excel(name = "选项数")
    private String num;

    @Excel(name = "选项1")
    private String option1;

    @Excel(name = "选项2")
    private String option2;

    @Excel(name = "选项3")
    private String option3;

    @Excel(name = "选项4")
    private String option4;

    @Excel(name = "选项5")
    private String option5;

    @Excel(name = "选项6")
    private String option6;

    @Excel(name = "选项7")
    private String option7;

    @Excel(name = "选项8")
    private String option8;

    @Excel(name = "选项9")
    private String option9;

    @Excel(name = "选项10")
    private String option10;

    public QuestionsImport() {
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getQuestScore() {
        return questScore;
    }

    public void setQuestScore(String questScore) {
        this.questScore = questScore;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(String rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getOption5() {
        return option5;
    }

    public void setOption5(String option5) {
        this.option5 = option5;
    }

    public String getOption6() {
        return option6;
    }

    public void setOption6(String option6) {
        this.option6 = option6;
    }

    public String getOption7() {
        return option7;
    }

    public void setOption7(String option7) {
        this.option7 = option7;
    }

    public String getOption8() {
        return option8;
    }

    public void setOption8(String option8) {
        this.option8 = option8;
    }

    public String getOption9() {
        return option9;
    }

    public void setOption9(String option9) {
        this.option9 = option9;
    }

    public String getOption10() {
        return option10;
    }

    public void setOption10(String option10) {
        this.option10 = option10;
    }

    @Override
    public String toString() {
        return "QuestionsImport{" +
                "order='" + order + '\'' +
                ", types='" + types + '\'' +
                ", questScore='" + questScore + '\'' +
                ", stem='" + stem + '\'' +
                ", rightAnswers='" + rightAnswers + '\'' +
                ", num='" + num + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", option4='" + option4 + '\'' +
                ", option5='" + option5 + '\'' +
                ", option6='" + option6 + '\'' +
                ", option7='" + option7 + '\'' +
                ", option8='" + option8 + '\'' +
                ", option9='" + option9 + '\'' +
                ", option10='" + option10 + '\'' +
                '}';
    }
}
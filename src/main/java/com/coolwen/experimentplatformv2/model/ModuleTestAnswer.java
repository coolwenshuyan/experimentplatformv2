package com.coolwen.experimentplatformv2.model;

import javax.persistence.*;

/**
 * @author 淮南
 * @date 2020/5/12 12:59
 */
@Entity
@Table(name = "t_mtest_answer")
public class ModuleTestAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "mtest_answer_id")
    @TableGenerator(name = "mtest_answer_id", initialValue = 0, allocationSize = 1,table = "seq_table")
    @Column(name = "answer_id")
    private int answerId;

    /**
     * 这个是答案的选项
     */
    @Column(name = "answer_describe")
    private String answerDescribe;

    /**
     * 选项的序号
     */
    @Column(name = "answer_order")
    private int answerOrder;

    @Column(name = "quest_id")
    private int questId;

    public ModuleTestAnswer(String answerDescribe, int answerOrder, int questId) {
        this.answerDescribe = answerDescribe;
        this.answerOrder = answerOrder;
        this.questId = questId;
    }

    public ModuleTestAnswer() {
    }


    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswerDescribe() {
        return answerDescribe;
    }

    public void setAnswerDescribe(String answerDescribe) {
        this.answerDescribe = answerDescribe;
    }

    public int getAnswerOrder() {
        return answerOrder;
    }

    public void setAnswerOrder(int answerOrder) {
        this.answerOrder = answerOrder;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    @Override
    public String toString() {
        return "ModuleTestAnswer{" +
                "answerId=" + answerId +
                ", answerDescribe='" + answerDescribe + '\'' +
                ", answerOrder=" + answerOrder +
                ", questId=" + questId +
                '}';
    }

}

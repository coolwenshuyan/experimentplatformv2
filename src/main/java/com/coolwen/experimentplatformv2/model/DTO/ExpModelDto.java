package com.coolwen.experimentplatformv2.model.DTO;


/**
 * @author 朱治汶
 * @version 2021-01-02 21:28
 */
public class ExpModelDto {
    //模块id
    private int mid;
    //模块名
    private String mName;
    //是否是考核模块
    private boolean needKaohe;
    //当前参与人数
    private int currentParticipants;
    //总人数
    private int allNum;

    public ExpModelDto() {
    }


    public ExpModelDto(int mid, String mName) {
        this.mid = mid;
        this.mName = mName;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public boolean isNeedKaohe() {
        return needKaohe;
    }

    public void setNeedKaohe(boolean needKaohe) {
        this.needKaohe = needKaohe;
    }

    public int getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }
}

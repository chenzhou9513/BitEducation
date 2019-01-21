package com.bitedu.dto;

import java.io.Serializable;

public class UserCharge implements Serializable {


    private String rechargeID;

    private String user;

    private Integer tokenNum;

    public UserCharge(String rechargeID, String user, Integer tokenNum) {
        this.rechargeID = rechargeID;
        this.user = user;
        this.tokenNum = tokenNum;
    }

    public String getRechargeID() {
        return rechargeID;
    }

    public void setRechargeID(String rechargeID) {
        this.rechargeID = rechargeID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getTokenNum() {
        return tokenNum;
    }

    public void setTokenNum(Integer tokenNum) {
        this.tokenNum = tokenNum;
    }

    public UserCharge(){

    }
}

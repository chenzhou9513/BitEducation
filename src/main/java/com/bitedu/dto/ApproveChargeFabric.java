package com.bitedu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ApproveChargeFabric implements Serializable {

    @JsonProperty(value = "$class")
    private String className;

    private String rechargeID;

    public ApproveChargeFabric(){}

    public ApproveChargeFabric(String className, String rechargeID) {
        this.className = className;
        this.rechargeID = rechargeID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRechargeID() {
        return rechargeID;
    }

    public void setRechargeID(String rechargeID) {
        this.rechargeID = rechargeID;
    }
}
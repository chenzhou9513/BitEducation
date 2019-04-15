package com.bitedu.pojo.fabric;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class Company implements Serializable {


    @JsonProperty(value = "$class")
    private String className;

    private int state;

    private String email;

    private double accountBalance;

    private String qualificationId;

    public Company(){}

    public Company(String className, int state, String email, double accountBalance, String qualificationId) {
        this.className = className;
        this.state = state;
        this.email = email;
        this.accountBalance = accountBalance;
        this.qualificationId = qualificationId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId;
    }
}

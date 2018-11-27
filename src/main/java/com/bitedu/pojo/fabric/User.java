package com.bitedu.pojo.fabric;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class User implements Serializable {

    @JsonProperty(value = "$class")
    private String className;

    private int state;

    private String email;

    private double accountBalance;

    public User(String className, int state, String email, double accountBalance) {
        this.className = className;
        this.state = state;
        this.email = email;
        this.accountBalance = accountBalance;
    }

    public User(){}

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
}

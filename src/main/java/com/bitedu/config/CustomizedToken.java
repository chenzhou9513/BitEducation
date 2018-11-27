package com.bitedu.config;

import org.apache.shiro.authc.UsernamePasswordToken;


public class CustomizedToken extends UsernamePasswordToken {



    private int loginType;

    public CustomizedToken(final String username, final String password,int loginType) {
        super(username,password);
        this.loginType = loginType;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

}
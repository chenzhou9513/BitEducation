package com.bitedu.dto;

public class UserConsumeService {

    private String serviceID;
    private  String user;

    public UserConsumeService(){

    }

    public UserConsumeService(String serviceID, String user) {
        this.serviceID = serviceID;
        this.user = user;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

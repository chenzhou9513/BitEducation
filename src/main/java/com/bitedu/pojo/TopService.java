package com.bitedu.pojo;

import java.io.Serializable;

public class TopService implements Serializable {

    private String name;
    private String serviceId;


    public TopService(){

    }

    public TopService(String name, String serviceId) {
        this.name = name;
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}

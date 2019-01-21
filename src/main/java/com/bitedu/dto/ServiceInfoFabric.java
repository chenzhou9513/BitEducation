package com.bitedu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceInfoFabric {

    /*
    {
      "$class": "token.Service",
      "serviceID": "asdasdasdasd",
      "serviceName": "edwfsdfsdf",
      "servicePrice": 10,
      "serviceType": 1,
      "company": "company1@email.com"
    }
    */

    @JsonProperty(value = "$class")
    private String className;

    private String serviceID;

    private String serviceName;

    private int servicePrice;

    private int serviceType;

    private String company;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}

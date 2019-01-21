package com.bitedu.dto;

import com.bitedu.pojo.ServiceInfo;
import com.bitedu.pojo.ServiceSchedule;

import java.io.Serializable;
import java.util.List;

public class ServiceInfoWithSchedule implements Serializable {



    private ServiceInfo serviceInfo;

    private List<ServiceSchedule> schedules;

    public ServiceInfoWithSchedule(){}

    public ServiceInfoWithSchedule(ServiceInfo serviceInfo, List<ServiceSchedule> schedules) {
        this.serviceInfo = serviceInfo;
        this.schedules = schedules;
    }

    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public List<ServiceSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ServiceSchedule> schedules) {
        this.schedules = schedules;
    }
}

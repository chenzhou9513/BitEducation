package com.bitedu.service;

import com.bitedu.dto.ServiceInfoWithSchedule;
import com.bitedu.pojo.ServiceInfo;

import java.util.List;

public interface ConsumptionService {



    public ServiceInfo getServiceByServiceId(String serviceId);

    public Object userConsumeService(String email,String serviceId);

    public List<ServiceInfo> selectServiceSelective(ServiceInfo record);

    public List<ServiceInfo> selectCompanyService(String email);

    public List<ServiceInfo> selectUserService(String email);


    public ServiceInfo selectByPrimaryKey(String id);

    public Object insertServiceWithSchedule(ServiceInfoWithSchedule serviceInfoWithSchedule);

}
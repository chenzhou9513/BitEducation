package com.bitedu.dao;

import com.bitedu.pojo.ServiceInfo;

import java.util.List;

public interface ServiceInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ServiceInfo record);

    int insertSelective(ServiceInfo record);

    ServiceInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ServiceInfo record);

    int updateByPrimaryKey(ServiceInfo record);

    List<ServiceInfo> selectServiceSelective(ServiceInfo record);

    List<ServiceInfo> selectCompanyService(String email);

    List<ServiceInfo> selectUserService(String email);


}
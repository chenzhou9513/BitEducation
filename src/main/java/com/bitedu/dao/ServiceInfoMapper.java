package com.bitedu.dao;

import com.bitedu.pojo.ServiceInfo;

public interface ServiceInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ServiceInfo record);

    int insertSelective(ServiceInfo record);

    ServiceInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ServiceInfo record);

    int updateByPrimaryKey(ServiceInfo record);
}
package com.bitedu.dao;

import com.bitedu.pojo.ServiceCategory;

public interface ServiceCategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(ServiceCategory record);

    int insertSelective(ServiceCategory record);

    ServiceCategory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ServiceCategory record);

    int updateByPrimaryKey(ServiceCategory record);
}
package com.bitedu.dao;

import com.bitedu.pojo.ServiceSchedule;

import java.util.List;

public interface ServiceScheduleMapper {
    int deleteByPrimaryKey(String id);

    int insert(ServiceSchedule record);

    int insertSelective(ServiceSchedule record);

    ServiceSchedule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ServiceSchedule record);

    int updateByPrimaryKey(ServiceSchedule record);

    List<ServiceSchedule> selectByServiceId(String id);

}
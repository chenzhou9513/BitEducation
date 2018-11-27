package com.bitedu.dao;

import com.bitedu.pojo.AdminInfo;

public interface AdminInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(AdminInfo record);

    int insertSelective(AdminInfo record);

    AdminInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AdminInfo record);

    int updateByPrimaryKey(AdminInfo record);

    AdminInfo selectByAdminId(String adminId);

    int updateByAdminIdSelective(AdminInfo record);




}
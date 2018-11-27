package com.bitedu.dao;

import com.bitedu.pojo.CompanyInfo;

public interface CompanyInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(CompanyInfo record);

    int insertSelective(CompanyInfo record);

    CompanyInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CompanyInfo record);

    int updateByPrimaryKey(CompanyInfo record);

    int updateByEmailSelective(CompanyInfo record);

    CompanyInfo selectByEmail(String email);


}
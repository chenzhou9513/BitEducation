package com.bitedu.dao;

import com.bitedu.pojo.QualificationInfo;

public interface QualificationInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(QualificationInfo record);

    int insertSelective(QualificationInfo record);

    QualificationInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QualificationInfo record);

    int updateByPrimaryKey(QualificationInfo record);
}
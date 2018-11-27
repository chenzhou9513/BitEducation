package com.bitedu.dao;

import com.bitedu.pojo.QualificationApply;

public interface QualificationApplyMapper {
    int deleteByPrimaryKey(String id);

    int insert(QualificationApply record);

    int insertSelective(QualificationApply record);

    QualificationApply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QualificationApply record);

    int updateByPrimaryKey(QualificationApply record);
}
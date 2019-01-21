package com.bitedu.dao;

import com.bitedu.pojo.ChargeApply;

import java.util.List;

public interface ChargeApplyMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChargeApply record);

    int insertSelective(ChargeApply record);

    ChargeApply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChargeApply record);

    int updateByPrimaryKey(ChargeApply record);

    List<ChargeApply> selectAllSelective(ChargeApply record);
}
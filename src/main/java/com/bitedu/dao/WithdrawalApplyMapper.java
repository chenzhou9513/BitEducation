package com.bitedu.dao;

import com.bitedu.pojo.WithdrawalApply;

import java.util.List;

public interface WithdrawalApplyMapper {
    int deleteByPrimaryKey(String id);

    int insert(WithdrawalApply record);

    int insertSelective(WithdrawalApply record);

    WithdrawalApply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WithdrawalApply record);

    int updateByPrimaryKey(WithdrawalApply record);

    List<WithdrawalApply> selectAllWithdrawalApply();

}
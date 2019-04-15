package com.bitedu.dao;

import com.bitedu.pojo.SuperviseRange;

public interface SuperviseRangeMapper {
    int deleteByPrimaryKey(String id);

    int insert(SuperviseRange record);

    int insertSelective(SuperviseRange record);

    SuperviseRange selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SuperviseRange record);

    int updateByPrimaryKey(SuperviseRange record);
}
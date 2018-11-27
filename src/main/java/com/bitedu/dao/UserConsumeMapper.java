package com.bitedu.dao;

import com.bitedu.pojo.UserConsume;

public interface UserConsumeMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserConsume record);

    int insertSelective(UserConsume record);

    UserConsume selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserConsume record);

    int updateByPrimaryKey(UserConsume record);
}
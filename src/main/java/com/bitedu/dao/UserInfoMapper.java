package com.bitedu.dao;

import com.bitedu.pojo.UserInfo;
import org.springframework.cache.annotation.Cacheable;

public interface UserInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    int updateByEmailSelective(UserInfo record);

    UserInfo selectByEmail(String email);
}
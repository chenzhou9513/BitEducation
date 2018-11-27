package com.bitedu.service;

import com.bitedu.common.Result;
import com.bitedu.pojo.UserInfo;
import org.springframework.stereotype.Service;

public interface UserService {

    public Object insertUser(UserInfo userInfo);

    public UserInfo getUserByEmail(String email);

    public Object updateUser(UserInfo userInfo);

}

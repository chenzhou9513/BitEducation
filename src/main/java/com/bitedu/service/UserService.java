package com.bitedu.service;

import com.bitedu.common.Result;
import com.bitedu.pojo.ChargeApply;
import com.bitedu.pojo.UserInfo;
import org.springframework.stereotype.Service;

public interface UserService {

    public Object insertUser(UserInfo userInfo);

    public Object insertUser2(UserInfo userInfo);

    public UserInfo getUserByEmail(String email);

    public Object updateUser(UserInfo userInfo);

    public Object chargeApply(ChargeApply chargeApply);

}

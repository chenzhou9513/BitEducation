package com.bitedu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.Result;
import com.bitedu.common.SnowFlake;
import com.bitedu.common.StatusCode;
import com.bitedu.dao.UserInfoMapper;
import com.bitedu.pojo.UserInfo;
import com.bitedu.pojo.fabric.User;
import com.bitedu.service.UserService;
import com.bitedu.service.fabric.FabricUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private FabricUserClient fabricUserClient;


    @Autowired
    private UserInfoMapper userInfoMapper;


    @Autowired
    private SnowFlake snowFlake;


    @Override
    @Transactional
    public Object insertUser(UserInfo userInfo) {

        User user = new User("token.User",0 ,userInfo.getEmail() ,userInfo.getBalance() );
        JSONObject jsonObject;

        try {

            jsonObject = JSONObject.parseObject((fabricUserClient.insertUser(user)));

        }catch (Exception e){

            //    public Result(boolean flag, int code, String message) {
            return new Result(false,StatusCode.FABRICERROR,e.toString());

        }

        userInfo.setId(String.valueOf(snowFlake.nextId()));

        userInfoMapper.insert(userInfo);


        return new Result(true,StatusCode.SUCCESS,"创建成功",userInfo);

    }


    @Override
    public UserInfo getUserByEmail(String email) {
        return this.userInfoMapper.selectByEmail(email);
    }


    @Override
    public Object updateUser(UserInfo userInfo) {

        int res = this.userInfoMapper.updateByEmailSelective(userInfo);
        if(res == 0){
            return new Result(false,StatusCode.NOTEXISTERROR,"用户不存在");
        }


        return new Result(true,StatusCode.SUCCESS,"更新成功");
    }
}

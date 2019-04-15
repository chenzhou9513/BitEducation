package com.bitedu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.PathUtils;
import com.bitedu.common.Result;
import com.bitedu.common.SnowFlake;
import com.bitedu.common.StatusCode;
import com.bitedu.dao.ChargeApplyMapper;
import com.bitedu.dao.UserInfoMapper;
import com.bitedu.dto.UserCharge;
import com.bitedu.pojo.ChargeApply;
import com.bitedu.pojo.UserInfo;
import com.bitedu.pojo.fabric.User;
import com.bitedu.service.UserService;
import com.bitedu.service.fabric.FabricUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Queue;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private FabricUserClient fabricUserClient;


    @Autowired
    private UserInfoMapper userInfoMapper;


    @Autowired
    private SnowFlake snowFlake;


    @Autowired
    private ChargeApplyMapper chargeApplyMapper;

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

        userInfoMapper.insertSelective(userInfo);


        return new Result(true,StatusCode.SUCCESS,"创建成功",userInfo);

    }


    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;

    public Object insertUser2(UserInfo userInfo){


        HashMap hashMap = new HashMap();
        hashMap.put("$class", "token.User");
        hashMap.put("url","/user" );
        hashMap.put("method","post");
        hashMap.put("email", userInfo.getEmail());
        hashMap.put("accountBalance",userInfo.getBalance());

        jmsMessagingTemplate.convertAndSend(queue, hashMap);

        /*
                User user = new User("token.User",0 ,userInfo.getEmail() ,userInfo.getBalance() );
                JSONObject jsonObject;



                try {

                    jsonObject = JSONObject.parseObject((fabricUserClient.insertUser(user)));

                }catch (Exception e){

                    //    public Result(boolean flag, int code, String message) {
                    return new Result(false,StatusCode.FABRICERROR,e.toString());

                }

                userInfo.setId(String.valueOf(snowFlake.nextId()));

                userInfoMapper.insertSelective(userInfo);


                return new Result(true,StatusCode.SUCCESS,"创建成功",userInfo);
        */
        return null;
    }


    @Override
    /*
        @Cacheable(value = "UserInfoCache",key="#email",unless = "#result==null")
    */
    public UserInfo getUserByEmail(String email) {
        return this.userInfoMapper.selectByEmail(email);
    }


    @Override
    /*
        @CachePut(value = "UserInfoCache", key="#userInfo.email",unless = "#result==null")
    */
    public Object updateUser(UserInfo userInfo) {

        int res = this.userInfoMapper.updateByEmailSelective(userInfo);

        if(res==0){
            return null;
        }else{
            return this.userInfoMapper.selectByEmail(userInfo.getEmail());
        }


    }


    @Override
    public Object chargeApply(ChargeApply chargeApply) {
        chargeApply.setId("C"+String.valueOf(snowFlake.nextId()));

        JSONObject jsonObject = null;

        try {

            jsonObject = JSONObject.parseObject(this.fabricUserClient.chargeApply(
                    new UserCharge(chargeApply.getId(),chargeApply.getEmail(),chargeApply.getNums())
            ));

        }catch (Exception e){

            //    public Result(boolean flag, int code, String message) {
            return new Result(false,StatusCode.FABRICERROR,e.toString());

        }

        return this.chargeApplyMapper.insertSelective(chargeApply);
    }
}

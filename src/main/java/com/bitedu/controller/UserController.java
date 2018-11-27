package com.bitedu.controller;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.bitedu.common.JwtUtil;
import com.bitedu.common.Result;
import com.bitedu.common.StatusCode;
import com.bitedu.pojo.UserInfo;
import com.bitedu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.POST)
    public Result insetUser(@RequestBody UserInfo userInfo){

        return (Result) userService.insertUser(userInfo);

    }

    @RequestMapping("/{email}")
    public Result getUserByEmail(@PathVariable("email")String email){


        Subject currentUser = SecurityUtils.getSubject();
        String currentUserName = (String)currentUser.getPrincipal();

        if(!(""+email).equals(currentUserName)){

            return new Result(false,StatusCode.PERMISSIONERROR,"无权访问");
        }

        UserInfo userInfo = userService.getUserByEmail(email);
        if(userInfo!=null){
            return new Result(true,StatusCode.SUCCESS,"获取用户成功",userInfo);
        }else{

            return new Result(false,StatusCode.NOTEXISTERROR,"用户不存在");

        }

    }




    @RequestMapping(value = "/{email}",method = RequestMethod.PUT)
    public Result updateUserByEmail(@PathVariable("email")String email,@RequestBody UserInfo userInfo) {

        Subject currentUser = SecurityUtils.getSubject();
        String currentUserName = (String)currentUser.getPrincipal();

        if(!(""+email).equals(currentUserName)){

            return new Result(false,StatusCode.PERMISSIONERROR,"无权访问");
        }
        userInfo.setEmail(email);

        return (Result)userService.updateUser(userInfo);


    }


}

package com.bitedu.controller;


import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.Result;
import com.bitedu.common.StatusCode;
import com.bitedu.config.CustomizedToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {



    @RequestMapping("/unlogin")
    public Result getUnloginError(){
        return new Result(false,StatusCode.PERMISSIONERROR,"无权访问,请先登录");
    }


    @RequestMapping(value = "/login/user",method = RequestMethod.POST)
    public Object userLogin(@RequestBody JSONObject jsonObject){

        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");


        Subject subject =  SecurityUtils.getSubject();
        CustomizedToken usernamePasswordToken = new CustomizedToken(email,password,StatusCode.USER);

        try{

            subject.login(usernamePasswordToken);
        }catch (UnknownAccountException ue){
            return new Result(false,StatusCode.NOTEXISTERROR,"User not exist");
        }catch (IncorrectCredentialsException ie){
            return new Result(true, StatusCode.PASSWORDERROR,"Password incorrect");
        }
        return new Result(true,StatusCode.SUCCESS,"login success");

    }

    @RequestMapping(value = "/logout/user",method = RequestMethod.POST)
    public Object userLogout(@RequestBody String email){

        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return new Result(true,StatusCode.SUCCESS,"logout success");

    }


    @RequestMapping(value = "/login/company",method = RequestMethod.POST)
    public Object companyLogin(@RequestBody JSONObject jsonObject){

        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        Subject subject =  SecurityUtils.getSubject();
        CustomizedToken usernamePasswordToken = new CustomizedToken(email,password,StatusCode.COMPANY);
        try{

            subject.login(usernamePasswordToken);
        }catch (UnknownAccountException ue){
            return new Result(false,StatusCode.NOTEXISTERROR,"Company not exist");
        }catch (IncorrectCredentialsException ie){
            return new Result(true, StatusCode.PASSWORDERROR,"Password incorrect");
        }
        return new Result(true,StatusCode.SUCCESS,"login success");

    }

    @RequestMapping(value = "/logout/company",method = RequestMethod.POST)
    public Object companyLogout(@RequestBody String email){

        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return new Result(true,StatusCode.SUCCESS,"logout success");

    }



    @RequestMapping(value = "/login/admin",method = RequestMethod.POST)
    public Object adminLogin(@RequestBody JSONObject jsonObject){

        String email = jsonObject.getString("adminId");
        String password = jsonObject.getString("password");
        Subject subject =  SecurityUtils.getSubject();
        CustomizedToken usernamePasswordToken = new CustomizedToken(email,password,StatusCode.ADMIN);
        try{

            subject.login(usernamePasswordToken);
        }catch (UnknownAccountException ue){
            return new Result(false,StatusCode.NOTEXISTERROR,"admin not exist");
        }catch (IncorrectCredentialsException ie){
            return new Result(true, StatusCode.PASSWORDERROR,"Password incorrect");
        }
        return new Result(true,StatusCode.SUCCESS,"login success");

    }

    @RequestMapping(value = "/logout/admin",method = RequestMethod.POST)
    public Object adminLogout(@RequestBody String adminId){

        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return new Result(true,StatusCode.SUCCESS,"logout success");

    }




}

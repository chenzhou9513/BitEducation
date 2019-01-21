package com.bitedu.controller;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.JwtUtil;
import com.bitedu.common.Result;
import com.bitedu.common.StatusCode;
import com.bitedu.pojo.ChargeApply;
import com.bitedu.pojo.ServiceInfo;
import com.bitedu.pojo.UserInfo;
import com.bitedu.service.AdminService;
import com.bitedu.service.ConsumptionService;
import com.bitedu.service.UserService;
import com.github.pagehelper.PageHelper;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConsumptionService consumptionService;


    @Autowired
    private AdminService adminService;

    @RequestMapping(method = RequestMethod.POST)
    public Result insetUser(@RequestBody UserInfo userInfo){

        return (Result) userService.insertUser(userInfo);

    }


    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public Result getUserByEmail(@RequestBody JSONObject jsonObject ){

        String email = (String)jsonObject.get("email");
        System.out.println(email);
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




    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateUserByEmail(@RequestBody UserInfo userInfo) {

        String email = userInfo.getEmail();
        Subject currentUser = SecurityUtils.getSubject();
        String currentUserName = (String)currentUser.getPrincipal();

        if(!(""+email).equals(currentUserName)){

            return new Result(false,StatusCode.PERMISSIONERROR,"无权访问");
        }
        userInfo.setEmail(email);

        return (Result)userService.updateUser(userInfo);


    }

    @RequestMapping(value = "/charge",method = RequestMethod.POST)
    public Result chargeApply(@RequestBody JSONObject jsonObject){
        ChargeApply chargeApply = new ChargeApply();
        chargeApply.setEmail((String)jsonObject.get("email"));
        chargeApply.setAccount((String)jsonObject.get("account"));
        chargeApply.setIsApprove(0);
        chargeApply.setIsCharge(0);
        chargeApply.setStatus(0);
        chargeApply.setNums((Integer) jsonObject.get("nums"));

        this.userService.chargeApply(chargeApply);

        return new Result(true, StatusCode.SUCCESS, "申请成功");

    }


    @RequestMapping(value = "/charge",method = RequestMethod.GET)
    public Result getChargeApply(){
        /*
         *   0 待办
         *   1 已办
         * */
        Subject currentUser = SecurityUtils.getSubject();

        String email = (String)currentUser.getPrincipal();

        ChargeApply chargeApply = new ChargeApply();

        chargeApply.setEmail(email);
        List<ChargeApply> data = this.adminService.getAllChargeApply(chargeApply);

        return new Result(true, StatusCode.SUCCESS,"成功" ,data );

    }




    @RequestMapping(value = "/service",method = RequestMethod.GET)
    public Result getUserService(@RequestParam("index")String index, @RequestParam("pageSize")String pageSize){

        Subject currentCompany = SecurityUtils.getSubject();
        String currentCompanyName = (String)currentCompany.getPrincipal();

        PageHelper.startPage(Integer.parseInt(index),Integer.parseInt(pageSize));
        List<ServiceInfo> data = this.consumptionService.selectUserService(currentCompanyName);
        return new Result(true, StatusCode.SUCCESS, "查询成功",data);

    }


}

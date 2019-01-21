package com.bitedu.controller;


import com.alibaba.druid.pool.ValidConnectionCheckerAdapter;
import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.Result;
import com.bitedu.common.StatusCode;
import com.bitedu.pojo.AdminInfo;
import com.bitedu.pojo.ChargeApply;
import com.bitedu.pojo.UserInfo;
import com.bitedu.service.AdminService;
import com.bitedu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {



    @Autowired
    private AdminService adminService;


    @RequestMapping(method = RequestMethod.POST)
    public Result insetAdmin(@RequestBody AdminInfo adminInfo){

        return (Result) adminService.insertAdmin(adminInfo);

    }

    @RequestMapping("/info")
    public Result getAdminByAdminId(@RequestBody JSONObject jsonObject){


        String adminId = jsonObject.getString("adminId");

        Subject currentUser = SecurityUtils.getSubject();
        String currentUserName = (String)currentUser.getPrincipal();

        if(!(""+adminId).equals(currentUserName)){

            return new Result(false,StatusCode.PERMISSIONERROR,"无权访问");
        }

        AdminInfo adminInfo = adminService.getAdminByAdminId(adminId);
        if(adminInfo!=null){
            return new Result(true,StatusCode.SUCCESS,"获取管理员信息成功",adminInfo);
        }else{

            return new Result(false,StatusCode.NOTEXISTERROR,"管理员不存在");

        }

    }




    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Result updateAdminByAdminId(@RequestBody AdminInfo adminInfo) {

        String adminId = adminInfo.getAdminId();
        Subject currentUser = SecurityUtils.getSubject();
        String currentUserName = (String)currentUser.getPrincipal();

        if(!(""+adminInfo).equals(currentUserName)){

            return new Result(false,StatusCode.PERMISSIONERROR,"无权访问");
        }


        adminInfo.setAdminId(adminId);

        return (Result)adminService.updateAdmin(adminInfo);


    }

    @RequestMapping(value = "/charge",method = RequestMethod.GET)
    public Result getAllCharge(@RequestParam(value = "status",required = false)String status
                               ){
        /*
        *   0 待办
        *   1 已办
        * */

        ChargeApply chargeApply = new ChargeApply();
        if("0".equals(status)||"1".equals(status)) {
            chargeApply.setIsApprove(Integer.parseInt(status));
        }
        List<ChargeApply> data = this.adminService.getAllChargeApply(chargeApply);

        return new Result(true, StatusCode.SUCCESS,"成功" ,data );

    }

    @RequestMapping(value = "/charge/{chargeId}",method = RequestMethod.GET)
    public Result getChargeApply(@PathVariable("chargeId") String chargeId){
        /*
         *   0 待办
         *   1 已办
         * */

        ChargeApply chargeApply = new ChargeApply();

        chargeApply.setId(chargeId);
        List<ChargeApply> data = this.adminService.getAllChargeApply(chargeApply);

        return new Result(true, StatusCode.SUCCESS,"成功" ,data );

    }

    @RequestMapping(value = "/charge/approve",method = RequestMethod.POST)
    public Result approveCharge(@RequestBody JSONObject jsonObject){

        String adminId = (String)jsonObject.get("adminId");
        String email = (String)jsonObject.get("email");
        String isApprove = (String)jsonObject.get("isApprove");
        String applyId = (String)jsonObject.get("applyId");

        return (Result) this.adminService.approveCharge(email, adminId, isApprove, applyId);

    }



}

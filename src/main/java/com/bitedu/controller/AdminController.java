package com.bitedu.controller;


import com.bitedu.common.Result;
import com.bitedu.common.StatusCode;
import com.bitedu.pojo.AdminInfo;
import com.bitedu.pojo.UserInfo;
import com.bitedu.service.AdminService;
import com.bitedu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {



    @Autowired
    private AdminService adminService;


    @RequestMapping(method = RequestMethod.POST)
    public Result insetAdmin(@RequestBody AdminInfo adminInfo){

        return (Result) adminService.insertAdmin(adminInfo);

    }

    @RequestMapping("/{adminId}")
    public Result getAdminByAdminId(@PathVariable("adminId")String adminId){


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




    @RequestMapping(value = "/{adminId}",method = RequestMethod.PUT)
    public Result updateAdminByAdminId(@PathVariable("adminId")String adminId,@RequestBody AdminInfo adminInfo) {

        Subject currentUser = SecurityUtils.getSubject();
        String currentUserName = (String)currentUser.getPrincipal();

        if(!(""+adminInfo).equals(currentUserName)){

            return new Result(false,StatusCode.PERMISSIONERROR,"无权访问");
        }


        adminInfo.setAdminId(adminId);

        return (Result)adminService.updateAdmin(adminInfo);


    }


}

package com.bitedu.controller;

import com.bitedu.common.Result;
import com.bitedu.common.StatusCode;
import com.bitedu.pojo.CompanyInfo;
import com.bitedu.service.CompanyService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/company")
@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;



    @RequestMapping("/test")
    public Object getTest(){
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result insetCompany(@RequestBody CompanyInfo companyInfo){

        return (Result) companyService.insertCompany(companyInfo);

    }

    @RequestMapping("/{email}")
    public Result getCompanyByEmail(@PathVariable("email")String email){


        Subject currentCompany = SecurityUtils.getSubject();
        String currentCompanyName = (String)currentCompany.getPrincipal();

        if(!(""+email).equals(currentCompanyName)){

            return new Result(false,StatusCode.PERMISSIONERROR,"无权访问");
        }

        CompanyInfo companyInfo = companyService.getCompanyByEmail(email);
        if(companyInfo!=null){
            return new Result(true,StatusCode.SUCCESS,"获取用户成功",companyInfo);
        }else{

            return new Result(false,StatusCode.NOTEXISTERROR,"用户不存在");

        }

    }




    @RequestMapping(value = "/{email}",method = RequestMethod.PUT)
    public Result updateCompanyByEmail(@PathVariable("email")String email,@RequestBody CompanyInfo companyInfo) {

        Subject currentCompany = SecurityUtils.getSubject();
        String currentCompanyName = (String)currentCompany.getPrincipal();

        if(!(""+email).equals(currentCompanyName)){

            return new Result(false,StatusCode.PERMISSIONERROR,"无权访问");
        }
        companyInfo.setEmail(email);

        return (Result)companyService.updateCompany(companyInfo);


    }


    @RequestMapping("/unlogin")
    public Result getUnloginError(){
        return new Result(false,StatusCode.PERMISSIONERROR,"无权访问,请先登录");
    }


}

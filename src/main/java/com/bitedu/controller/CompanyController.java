package com.bitedu.controller;

import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.Result;
import com.bitedu.common.StatusCode;
import com.bitedu.pojo.CompanyInfo;
import com.bitedu.pojo.ServiceInfo;
import com.bitedu.service.CompanyService;
import com.bitedu.service.ConsumptionService;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RequestMapping("/company")
@RestController
public class CompanyController {


    private static final String UPLOADPATH = "./file/";

    @Autowired
    private CompanyService companyService;



    @Autowired
    private ConsumptionService consumptionService;

    @RequestMapping("/test")
    public Object getTest(){
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result insetCompany(@RequestBody CompanyInfo companyInfo){

        return (Result) companyService.insertCompany(companyInfo);

    }

    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public Result getCompanyByEmail(@RequestBody JSONObject jsonObject){


        String email = (String)jsonObject.get("email");
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




    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Result updateCompanyByEmail(@RequestBody CompanyInfo companyInfo) {

        String email = companyInfo.getEmail();
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



    @RequestMapping(value = "/service",method = RequestMethod.GET)
    public Result getUserService(@RequestParam("index")String index, @RequestParam("pageSize")String pageSize){

        Subject currentCompany = SecurityUtils.getSubject();
        String currentCompanyName = (String)currentCompany.getPrincipal();

        PageHelper.startPage(Integer.parseInt(index),Integer.parseInt(pageSize));
        List<ServiceInfo> data = this.consumptionService.selectCompanyService(currentCompanyName);
        return new Result(true, StatusCode.SUCCESS, "查询成功",data);

    }


    @CrossOrigin
    @RequestMapping(value = "/qualification",method = RequestMethod.POST)
    public Result qulification(@RequestParam("file")MultipartFile file, @RequestParam("title")String title, @RequestParam("applyInfo")String applyInfo){

        /*
            String fileId = this.

            if (file.isEmpty()) {
                return new Result(false, StatusCode.ERROR, "文件为空");
            }


            File path = new File(this.UPLOADPATH + file.getOriginalFilename());
            try{
                byte[] fileSize = file.getBytes();
                file.transferTo(path);
                return "上传成功";
            }catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "上传失败";

    */

        return null;

    }

}

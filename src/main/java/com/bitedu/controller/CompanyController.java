package com.bitedu.controller;

import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.PathUtils;
import com.bitedu.common.Result;
import com.bitedu.common.SnowFlake;
import com.bitedu.common.StatusCode;
import com.bitedu.pojo.CompanyInfo;
import com.bitedu.pojo.QualificationApply;
import com.bitedu.pojo.ServiceInfo;
import com.bitedu.pojo.WithdrawalApply;
import com.bitedu.pojo.fabric.Company;
import com.bitedu.service.CompanyService;
import com.bitedu.service.ConsumptionService;
import com.bitedu.service.QualificationApplyService;
import com.bitedu.service.fabric.FabricCompanyClient;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.swing.FilePane;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RequestMapping("/company")
@RestController
public class CompanyController {


    @Autowired
    private PathUtils pathUtils;

    @Autowired
    private CompanyService companyService;


    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private ConsumptionService consumptionService;

    @Autowired
    private QualificationApplyService qualificationApplyService;

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




    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateCompanyByEmail(@RequestBody CompanyInfo companyInfo) {

        String email = companyInfo.getEmail();
        Subject currentCompany = SecurityUtils.getSubject();
        String currentCompanyName = (String)currentCompany.getPrincipal();

        if(!(""+email).equals(currentCompanyName)){

            return new Result(false,StatusCode.PERMISSIONERROR,"无权访问");
        }
        companyInfo.setEmail(email);

        CompanyInfo companyInfo1 = (CompanyInfo)companyService.updateCompany(companyInfo);

        if(companyInfo1==null){
            return new Result(false,StatusCode.NOTEXISTERROR,"用户不存在");
        }else{
            return new Result(true,StatusCode.SUCCESS,"更新成功");
        }



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




    @RequestMapping(value = "/qualification",method = RequestMethod.POST)
    public Result qulificationApply(@RequestParam("file")MultipartFile file,
                                    @RequestParam("title")String title,
                                    @RequestParam("applyInfo")String applyInfo,
                                    @RequestParam("companyId")String companyId){


        String fileId = String.valueOf(snowFlake.nextId());
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (file.isEmpty()) {
            return new Result(false, StatusCode.ERROR, "文件为空");
        }
        File path = new File(this.pathUtils.getFilePath()+"/Qualification/" + fileId+"."+suffix);
        try{
            byte[] fileSize = file.getBytes();
            file.transferTo(path);
        }catch (IllegalStateException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "文件上传失败");
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "文件上传失败");
        }

        QualificationApply qualificationApply  = new QualificationApply();
        qualificationApply.setFileId(fileId);
        qualificationApply.setCompanyEmail(companyId);
        qualificationApply.setTitle(title);
        qualificationApply.setApplyInfo(applyInfo);

        return (Result)qualificationApplyService.insertNewApply(qualificationApply);

    }



    @RequestMapping(value = "/qualification/show")
    @ResponseBody
    public void showImg(@RequestParam("id") String id,HttpServletRequest request,HttpServletResponse response) {
        String pathName = this.pathUtils.getFilePath()+"/Qualification/"+id+".jpg";
        File imgFile = new File(pathName);
        FileInputStream fin = null;
        OutputStream output = null;
        try {
            output = response.getOutputStream();
            fin = new FileInputStream(imgFile);
            byte[] arr = new byte[1024 * 10];
            int n;
            while ((n = fin.read(arr)) != -1) {
                output.write(arr, 0, n);
            }
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/withdrawal",method = RequestMethod.POST)
    public Result withdrawalApply(@RequestBody JSONObject jsonObject){

        WithdrawalApply withdrawalApply = new WithdrawalApply();
        withdrawalApply.setAccount(jsonObject.getString("account"));
        withdrawalApply.setEmail(jsonObject.getString("email"));
        withdrawalApply.setNums(jsonObject.getDouble("nums"));

        return (Result)this.companyService.insertWithdrawalApply(withdrawalApply);


    }


    @RequestMapping(value = "testqulification")
    public Object testHtml(){
        return new ModelAndView("test");
    }



}

package com.bitedu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.Result;
import com.bitedu.common.SnowFlake;
import com.bitedu.common.StatusCode;
import com.bitedu.dao.CompanyInfoMapper;
import com.bitedu.dao.UserInfoMapper;
import com.bitedu.pojo.CompanyInfo;
import com.bitedu.pojo.UserInfo;
import com.bitedu.pojo.fabric.Company;
import com.bitedu.pojo.fabric.User;
import com.bitedu.service.CompanyService;
import com.bitedu.service.fabric.FabricCompanyClient;
import com.bitedu.service.fabric.FabricUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService {


    @Autowired
    private FabricCompanyClient fabricCompanyClient;


    @Autowired
    private CompanyInfoMapper companyInfoMapper;


    @Autowired
    private SnowFlake snowFlake;



    @Override
    @Transactional
    public Object insertCompany(CompanyInfo companyInfo) {

        String[] defaultQualification = {"-1"};

        Company company = new Company("token.Company",0 ,companyInfo.getEmail() ,0, defaultQualification);
        JSONObject jsonObject;

        try {

            jsonObject = JSONObject.parseObject((fabricCompanyClient.insertCompany(company)));

        }catch (Exception e){

            //    public Result(boolean flag, int code, String message) {
            return new Result(false,StatusCode.FABRICERROR,e.toString());

        }

        companyInfo.setId(String.valueOf(snowFlake.nextId()));

        companyInfoMapper.insert(companyInfo);


        return new Result(true,StatusCode.SUCCESS,"创建成功",companyInfo);




    }

    @Override
    public CompanyInfo getCompanyByEmail(String email) {
        return this.companyInfoMapper.selectByEmail(email);
    }

    @Override
    public Object updateCompany(CompanyInfo companyInfo) {
        int res = this.companyInfoMapper.updateByEmailSelective(companyInfo);
        if(res == 0){
            return new Result(false,StatusCode.NOTEXISTERROR,"用户不存在");
        }


        return new Result(true,StatusCode.SUCCESS,"更新成功");
    }
}

package com.bitedu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.Result;
import com.bitedu.common.SnowFlake;
import com.bitedu.common.StatusCode;
import com.bitedu.dao.*;
import com.bitedu.pojo.CompanyInfo;
import com.bitedu.pojo.SuperviseRange;
import com.bitedu.pojo.UserInfo;
import com.bitedu.pojo.WithdrawalApply;
import com.bitedu.pojo.fabric.Company;
import com.bitedu.pojo.fabric.User;
import com.bitedu.service.CompanyService;
import com.bitedu.service.fabric.FabricCompanyClient;
import com.bitedu.service.fabric.FabricUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service
public class CompanyServiceImpl implements CompanyService {


    @Autowired
    private FabricCompanyClient fabricCompanyClient;


    @Autowired
    private CompanyInfoMapper companyInfoMapper;


    @Autowired
    private WithdrawalApplyMapper withdrawalApplyMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private QueryMapper queryMapper;

    @Autowired
    private SuperviseRangeMapper superviseRangeMapper;


    @Override
    @Transactional
    public Object insertCompany(CompanyInfo companyInfo) {

        String defaultQualification = "-1";

        Company company = new Company("token.Company",0 ,companyInfo.getEmail() ,0, defaultQualification);
        JSONObject jsonObject;

        try {

            jsonObject = JSONObject.parseObject((fabricCompanyClient.insertCompany(company)));

        }catch (Exception e){

            //    public Result(boolean flag, int code, String message) {
            return new Result(false,StatusCode.FABRICERROR,e.toString());

        }

        companyInfo.setId(String.valueOf(snowFlake.nextId()));

        companyInfoMapper.insertSelective(companyInfo);


        return new Result(true,StatusCode.SUCCESS,"创建成功",companyInfo);


    }

    @Override
    /*
    @Cacheable(value = "CompanyInfoCache",key="#email",unless = "#result==null")
    */
    public CompanyInfo getCompanyByEmail(String email) {
        return this.companyInfoMapper.selectByEmail(email);
    }

    @Override
    /*
        @CachePut(value = "CompanyInfoCache", key="#companyInfo.email",unless = "#result==null")
    */
    public Object updateCompany(CompanyInfo companyInfo) {
        int res = this.companyInfoMapper.updateByEmailSelective(companyInfo);
        if(res==0){
            return null;
        }else{
            return this.companyInfoMapper.selectByEmail(companyInfo.getEmail());
        }
    }


    @Override
    public Object insertWithdrawalApply(WithdrawalApply withdrawalApply) {

        Date now=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString=dateFormat.format(now);
        HashMap<String,String> m = new HashMap();
        m.put("beginDate",dateString+" 00:00:00");
        m.put("endDate", dateString+" 23:59:59");
        m.put("email",withdrawalApply.getEmail());
        int dayCount = this.queryMapper.companyWithdrawalCount(m);

        SuperviseRange superviseRange = superviseRangeMapper.selectByPrimaryKey("biteducation");

        if(superviseRange.getDayRecTimes()<=dayCount)
            return new Result(true,StatusCode.ERROR,"当日提现次数已用尽");


        withdrawalApply.setId(String.valueOf(snowFlake.nextId()));
        this.withdrawalApplyMapper.insertSelective(withdrawalApply);
        return new Result(true,StatusCode.SUCCESS,"申请成功");

    }
}

package com.bitedu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.Result;
import com.bitedu.common.SnowFlake;
import com.bitedu.common.StatusCode;
import com.bitedu.dao.AdminInfoMapper;
import com.bitedu.dao.CompanyInfoMapper;
import com.bitedu.pojo.AdminInfo;
import com.bitedu.pojo.CompanyInfo;
import com.bitedu.pojo.fabric.Administrator;
import com.bitedu.pojo.fabric.Company;
import com.bitedu.service.AdminService;
import com.bitedu.service.fabric.FabricAdminClient;
import com.bitedu.service.fabric.FabricCompanyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private FabricAdminClient fabricAdminClient;


    @Autowired
    private AdminInfoMapper adminInfoMapper;


    @Autowired
    private SnowFlake snowFlake;

    @Override
    public Object insertAdmin(AdminInfo adminInfo) {

        Administrator administrator = new Administrator("token.Administrator",adminInfo.getAdminId() , adminInfo.getLevel());
        JSONObject jsonObject;

        try {

            jsonObject = JSONObject.parseObject((fabricAdminClient.insertAdmin(administrator)));

        }catch (Exception e){

            //    public Result(boolean flag, int code, String message) {
            return new Result(false,StatusCode.FABRICERROR,e.toString());

        }

        adminInfo.setId(String.valueOf(snowFlake.nextId()));

        adminInfoMapper.insert(adminInfo);


        return new Result(true,StatusCode.SUCCESS,"创建成功",adminInfo);


    }

    @Override
    public AdminInfo getAdminByAdminId(String adminId) {
        return this.adminInfoMapper.selectByAdminId(adminId);
    }

    @Override
    public Object updateAdmin(AdminInfo adminInfo) {
        int res = this.adminInfoMapper.updateByAdminIdSelective(adminInfo);
        if(res == 0){
            return new Result(false,StatusCode.NOTEXISTERROR,"管理员 用户 不存在");
        }


        return new Result(true,StatusCode.SUCCESS,"更新成功");    }
}

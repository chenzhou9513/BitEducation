package com.bitedu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.SnowFlake;
import com.bitedu.common.StatusCode;
import com.bitedu.dao.CompanyInfoMapper;
import com.bitedu.dao.QualificationApplyMapper;
import com.bitedu.dao.QualificationInfoMapper;
import com.bitedu.dto.QualificationApplyFabric;
import com.bitedu.dto.QualificationInfoFabric;
import com.bitedu.pojo.CompanyInfo;
import com.bitedu.pojo.QualificationApply;
import com.bitedu.pojo.QualificationInfo;
import com.bitedu.pojo.fabric.Company;
import com.bitedu.service.QualificationApplyService;
import com.bitedu.service.fabric.FabricCompanyClient;
import com.bitedu.service.fabric.FabricQualification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bitedu.common.Result;
import springfox.documentation.spring.web.json.Json;

@Service
public class QualificationApplyServiceImpl implements QualificationApplyService {


    @Autowired
    private FabricQualification fabricQualification;

    @Autowired
    private QualificationApplyMapper qualificationApplyMapper;

    @Autowired
    private QualificationInfoMapper qualificationInfoMapper;

    @Autowired
    private FabricCompanyClient fabricCompanyClient;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private SnowFlake snowFlake;


    @Override
    public Object selectAllApply() {


        return this.qualificationApplyMapper.selectAll();
    }

    @Override
    public Object insertNewApply(QualificationApply record) {

        String applyId = String.valueOf(this.snowFlake.nextId());
        record.setAuditstate(0);
        record.setId(applyId);

        QualificationApplyFabric qualificationApplyFabric = new QualificationApplyFabric(
        "token.QualificationApply",
                applyId,
        0,
        "None",
        record.getCompanyEmail());

        JSONObject jsonObject = null;

        try {

            jsonObject = JSONObject.parseObject((fabricQualification.insertQualificationApply(qualificationApplyFabric)));

        }catch (Exception e){

            //    public Result(boolean flag, int code, String message) {
            return new Result(false,StatusCode.FABRICERROR,e.toString());

        }

        try {
            this.qualificationApplyMapper.insertSelective(record);
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,e.toString());

        }

        return new Result(true,StatusCode.SUCCESS,"申请成功");

    }

    @Override
    public QualificationApply selectByPrimaryKey(String id) {
        return this.qualificationApplyMapper.selectByPrimaryKey(id);
    }


    @Override
    public Object approveQualification(String adminId, int state,String qualificationApplyId,String info) {


        QualificationApply qualificationApply = new QualificationApply();

        qualificationApply.setId(qualificationApplyId);
        qualificationApply.setAuditstate(state);
        qualificationApply.setAdminEmail(adminId);

        this.qualificationApplyMapper.updateByPrimaryKeySelective(qualificationApply);

        if(state == 1){




            QualificationApply apply = this.qualificationApplyMapper.selectByPrimaryKey(qualificationApplyId);

            QualificationInfo qualificationInfo = new QualificationInfo();
            qualificationInfo.setApplyId(qualificationApplyId);
            qualificationInfo.setId(String.valueOf(snowFlake.nextId()));
            qualificationInfo.setType(1);
            qualificationInfo.setState(1);
            qualificationInfo.setEmail(apply.getCompanyEmail());

            this.qualificationInfoMapper.insertSelective(qualificationInfo);


            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.setEmail(apply.getCompanyEmail());
            companyInfo.setQualificationId(qualificationApplyId);
            companyInfoMapper.updateByEmailSelective(companyInfo);



            Company company = new Company();
            company.setQualificationId(qualificationApplyId);
            company.setState(0);

            JSONObject jsonObject = null;
            try {

                jsonObject = JSONObject.parseObject((fabricCompanyClient.updateCompany(apply.getCompanyEmail(),company)));

            }catch (Exception e){

                //    public Result(boolean flag, int code, String message) {
                return new Result(false,StatusCode.FABRICERROR,e.toString());

            }

            QualificationApplyFabric qualificationApplyFabric = new QualificationApplyFabric();
            qualificationApplyFabric.setAdministrator(adminId);
            qualificationApplyFabric.setAuditState(1);
            qualificationApplyFabric.setCompany(apply.getCompanyEmail());

            try {

                jsonObject = JSONObject.parseObject((fabricQualification.updateQualificationApply(apply.getId(),qualificationApplyFabric)));

            }catch (Exception e){

                //    public Result(boolean flag, int code, String message) {
                return new Result(false,StatusCode.FABRICERROR,e.toString());

            }

            QualificationInfoFabric qualificationInfoFabric = new QualificationInfoFabric(
                    "token.Qualification",
                    apply.getId(),
                    1);

            try {

                jsonObject = JSONObject.parseObject((fabricQualification.insertQualification(qualificationInfoFabric)));

            }catch (Exception e){

                //    public Result(boolean flag, int code, String message) {
                return new Result(false,StatusCode.FABRICERROR,e.toString());

            }




        }

        return new Result(true,StatusCode.SUCCESS,"审批成功");

    }
}

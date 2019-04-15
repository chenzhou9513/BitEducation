package com.bitedu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.Result;
import com.bitedu.common.SnowFlake;
import com.bitedu.common.StatusCode;
import com.bitedu.dao.*;
import com.bitedu.dto.ApproveChargeFabric;
import com.bitedu.pojo.*;
import com.bitedu.pojo.fabric.Administrator;
import com.bitedu.pojo.fabric.Company;
import com.bitedu.pojo.fabric.Supervice;
import com.bitedu.service.AdminService;
import com.bitedu.service.fabric.FabricAdminClient;
import com.bitedu.service.fabric.FabricCompanyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private FabricAdminClient fabricAdminClient;


    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @Autowired
    private ChargeApplyMapper chargeApplyMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private WithdrawalApplyMapper withdrawalApplyMapper;


    @Autowired
    private FabricCompanyClient fabricCompanyClient;

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private SuperviseRangeMapper superviseRangeMapper;

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


        return new Result(true,StatusCode.SUCCESS,"更新成功");
    }

    @Override
    public List<ChargeApply> getAllChargeApply(ChargeApply record) {
        return this.chargeApplyMapper.selectAllSelective(record);
    }

    @Override
    public Object approveCharge(String email, String adminId, String isApprove, String applyId) {


        ApproveChargeFabric approveChargeFabric = new ApproveChargeFabric();
        approveChargeFabric.setRechargeID(applyId);
        try{
            if(isApprove.equals("1")){

                approveChargeFabric.setClassName("token.CheckUserRecharge");

                this.fabricAdminClient.checkUserRecharge(approveChargeFabric);
            }else{
                approveChargeFabric.setClassName("token.RejectUserRecharge");
                this.fabricAdminClient.rejectUserRecharge(approveChargeFabric);

            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"区块链调用出错");
        }
        ChargeApply chargeApply = this.chargeApplyMapper.selectByPrimaryKey(applyId);
        chargeApply.setIsApprove(Integer.parseInt(isApprove));
        this.chargeApplyMapper.updateByPrimaryKeySelective(chargeApply);

        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);
        userInfo.setBalance(userInfo.getBalance()+chargeApply.getNums());
        this.userInfoMapper.updateByEmailSelective(userInfo);

        return new Result(true, StatusCode.SUCCESS,"批准成功" );
    }

    @Override
    public Object approveWithdrawal(String email,String adminId, int isApprove, String withdrawalId) {

        if(isApprove==1){


            CompanyInfo companyInfo = this.companyInfoMapper.selectByEmail(email);
            WithdrawalApply w = this.withdrawalApplyMapper.selectByPrimaryKey(withdrawalId);
            if(companyInfo.getBalance()<w.getNums()){
                new Result(false,StatusCode.ERROR,"余额不足");
            }

            CompanyInfo c = new CompanyInfo();
            c.setEmail(companyInfo.getEmail());
            c.setBalance(companyInfo.getBalance()-w.getNums());
            this.companyInfoMapper.updateByEmailSelective(c);


            Company company = new Company();
            company.setAccountBalance(companyInfo.getBalance()-w.getNums());
            company.setQualificationId(companyInfo.getQualificationId());


            JSONObject jsonObject = null;
            try {

                jsonObject = JSONObject.parseObject((fabricCompanyClient.updateCompany(companyInfo.getEmail(),company)));

            }catch (Exception e){

                //    public Result(boolean flag, int code, String message) {
                return new Result(false,StatusCode.FABRICERROR,e.toString());

            }


        }

        WithdrawalApply withdrawalApply = new WithdrawalApply();
        withdrawalApply.setId(withdrawalId);
        withdrawalApply.setIsApprove(isApprove);
        withdrawalApply.setAdminId(adminId);

        this.withdrawalApplyMapper.updateByPrimaryKeySelective(withdrawalApply);

        return new Result(true, StatusCode.SUCCESS,"批准成功" );
    }

    @Override
    public Object updateSupervice(SuperviseRange superviseRange) {
        this.superviseRangeMapper.updateByPrimaryKeySelective(superviseRange);

        HashMap<String,String> m = new HashMap<>();
        m.put("$class", "token.SuperviseRange");
        if(superviseRange.getDayComNums()!=null){
            m.put("MaxDayComsumptionNums", ""+superviseRange.getDayComNums());
        }
        if(superviseRange.getDayComTimes()!=null){
            m.put("MaxDayComsumptionTimes",""+superviseRange.getDayComTimes());
        }
        if(superviseRange.getDayRecTimes()!=null){
            m.put("MaxDayReChargeTimes",""+superviseRange.getDayPubTimes());
        }
        if(superviseRange.getDayRecNums()!=null){
            m.put("MaxDayReChargeNums",""+superviseRange.getDayRecNums());
        }
        if(superviseRange.getDayPubTimes()!=null){
            m.put("MaxDayPublishTimes", ""+superviseRange.getDayPubTimes());
        }
        if(superviseRange.getMonthComNums()!=null){
            m.put("MaxMonthComsumptionNums", ""+superviseRange.getDayPubTimes());

        }
        if(superviseRange.getMonthComTimes()!=null){
            m.put("MaxMonthComsumptionTimes", ""+superviseRange.getMonthComTimes());

        }
        if(superviseRange.getMonthPubNums()!=null){
            m.put("MaxMonthPublishTimes", ""+superviseRange.getMonthPubNums());

        }
        if(superviseRange.getMonthRecNums()!=null){
            m.put("MaxMonthReChargeNums", ""+superviseRange.getMonthRecNums());

        }
        if(superviseRange.getMonthRecTimes()!=null){
            m.put("MaxMonthReChargeTimes", ""+superviseRange.getMonthRecTimes());

        }



        JSONObject jsonObject = null;
        try {

            jsonObject = JSONObject.parseObject((fabricAdminClient.updateSupervice(m)));

        }catch (Exception e){

            //    public Result(boolean flag, int code, String message) {
            return new Result(false,StatusCode.FABRICERROR,e.toString());

        }
        return null;
    }
}

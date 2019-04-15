package com.bitedu.controller;


import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.Result;
import com.bitedu.common.StatusCode;
import com.bitedu.dao.SuperviseRangeMapper;
import com.bitedu.dao.WithdrawalApplyMapper;
import com.bitedu.pojo.*;
import com.bitedu.service.AdminService;
import com.bitedu.service.QualificationApplyService;
import com.github.pagehelper.PageHelper;
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

    @Autowired
    private WithdrawalApplyMapper withdrawalApplyMapper;


    @Autowired
    private SuperviseRangeMapper superviseRangeMapper;

    @Autowired
    private QualificationApplyService qualificationApplyService;

    @RequestMapping(method = RequestMethod.POST)
    public Result insetAdmin(@RequestBody AdminInfo adminInfo) {

        return (Result) adminService.insertAdmin(adminInfo);

    }

    @RequestMapping("/info")
    public Result getAdminByAdminId(@RequestBody JSONObject jsonObject) {


        String adminId = jsonObject.getString("adminId");

        Subject currentUser = SecurityUtils.getSubject();
        String currentUserName = (String) currentUser.getPrincipal();

        if (!("" + adminId).equals(currentUserName)) {

            return new Result(false, StatusCode.PERMISSIONERROR, "无权访问");
        }

        AdminInfo adminInfo = adminService.getAdminByAdminId(adminId);
        if (adminInfo != null) {
            return new Result(true, StatusCode.SUCCESS, "获取管理员信息成功", adminInfo);
        } else {

            return new Result(false, StatusCode.NOTEXISTERROR, "管理员不存在");

        }

    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateAdminByAdminId(@RequestBody AdminInfo adminInfo) {

        String adminId = adminInfo.getAdminId();
        Subject currentUser = SecurityUtils.getSubject();
        String currentUserName = (String) currentUser.getPrincipal();

        if (!("" + adminId).equals(currentUserName)) {

            return new Result(false, StatusCode.PERMISSIONERROR, "无权访问");
        }


        adminInfo.setAdminId(adminId);

        return (Result) adminService.updateAdmin(adminInfo);


    }

    @RequestMapping(value = "/charge", method = RequestMethod.GET)
    public Result getAllCharge(@RequestParam(value = "status", required = false) String status
    ) {
        /*
         *   0 待办
         *   1 已办
         * */

        ChargeApply chargeApply = new ChargeApply();
        if ("0".equals(status) || "1".equals(status)) {
            chargeApply.setIsApprove(Integer.parseInt(status));
        }
        List<ChargeApply> data = this.adminService.getAllChargeApply(chargeApply);

        return new Result(true, StatusCode.SUCCESS, "成功", data);

    }

    @RequestMapping(value = "/charge/{chargeId}", method = RequestMethod.GET)
    public Result getChargeApply(@PathVariable("chargeId") String chargeId) {
        ChargeApply chargeApply = new ChargeApply();
        chargeApply.setId(chargeId);
        List<ChargeApply> data = this.adminService.getAllChargeApply(chargeApply);

        return new Result(true, StatusCode.SUCCESS, "成功", data);

    }

    @RequestMapping(value = "/charge/approve", method = RequestMethod.POST)
    public Result approveCharge(@RequestBody JSONObject jsonObject) {

        String adminId = (String) jsonObject.get("adminId");
        String email = (String) jsonObject.get("email");
        String isApprove = (String) jsonObject.get("isApprove");
        String applyId = (String) jsonObject.get("applyId");

        return (Result) this.adminService.approveCharge(email, adminId, isApprove, applyId);

    }


    @RequestMapping(value = "/qualification", method = RequestMethod.GET)
    public Result getAllQualificationApply(@RequestParam("index") String index, @RequestParam("pageSize") String pageSize) {

        PageHelper.startPage(Integer.parseInt(index), Integer.parseInt(pageSize));


        List<QualificationApply> qualificationApplies = (List<QualificationApply>) qualificationApplyService.selectAllApply();

        return new Result(true, StatusCode.SUCCESS, "查询成功", qualificationApplies);

    }

    @RequestMapping(value = "/qualification/{applyId}", method = RequestMethod.GET)
    public Result getQualificationApply(@PathVariable("applyId") String applyId) {

        return new Result(
                true,
                StatusCode.SUCCESS,
                "查询成功",
                this.qualificationApplyService.selectByPrimaryKey(applyId)

        );
    }

    @RequestMapping(value = "/qualification/approve", method = RequestMethod.POST)
    public Result getQualificationApply(@RequestBody JSONObject jsonObject) {

        int state = Integer.parseInt((String) jsonObject.get("state"));
        String applyId = (String) jsonObject.get("applyId");
        String info = (String) jsonObject.get("info");
        String adminId = (String) jsonObject.get("adminId");


        return (Result) this.qualificationApplyService.approveQualification(adminId, state, applyId, info);


    }

    @RequestMapping(value = "/withdrawal", method = RequestMethod.GET)
    public Result getAllWithdrawalApply(@RequestParam("index") String index, @RequestParam("pageSize") String pageSize) {

        PageHelper.startPage(Integer.parseInt(index), Integer.parseInt(pageSize));


        List<WithdrawalApply> withdrawalApplies = withdrawalApplyMapper.selectAllWithdrawalApply();

        return new Result(true, StatusCode.SUCCESS, "查询成功", withdrawalApplies);

    }


    @RequestMapping(value = "/withdrawal/{withdrawalId}", method = RequestMethod.GET)
    public Result getWithdrawalApply(@PathVariable("withdrawalId") String withdrawalId) {

        return new Result(
                true,
                StatusCode.SUCCESS,
                "查询成功",
                this.withdrawalApplyMapper.selectByPrimaryKey(withdrawalId)

        );
    }

    @RequestMapping(value = "/withdrawal/approve", method = RequestMethod.POST)
    public Result getWIthdrawalApply(@RequestBody JSONObject jsonObject) {

        int isApprove = jsonObject.getInteger("isApprove");
        String withdrawalId = (String) jsonObject.get("withdrawalId");
        String adminId = (String) jsonObject.get("adminId");
        String email = (String) jsonObject.get("email");


        return (Result) this.adminService.approveWithdrawal(email, adminId, isApprove, withdrawalId);


    }

    @RequestMapping(value = "/superviserange", method = RequestMethod.GET)
    public Result getSuperviseRange() {
        return
                new Result(true, StatusCode.SUCCESS, "查询成功", this.superviseRangeMapper.selectByPrimaryKey("biteducation"));


    }

    @RequestMapping(value = "/superviserange", method = RequestMethod.POST)
    public Result updateSuperviseRange(@RequestBody SuperviseRange superviseRange) {

        superviseRange.setId("biteducation");
        return new Result(true, StatusCode.SUCCESS, "更新成功", this.adminService.updateSupervice(superviseRange));


    }


}

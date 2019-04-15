package com.bitedu.service;

import com.bitedu.pojo.AdminInfo;
import com.bitedu.pojo.ChargeApply;
import com.bitedu.pojo.SuperviseRange;
import com.bitedu.pojo.fabric.Administrator;
import com.bitedu.pojo.fabric.Supervice;

import java.util.List;

public interface AdminService {

    public Object insertAdmin(AdminInfo adminInfo);

    public AdminInfo getAdminByAdminId(String adminId);

    public Object updateAdmin(AdminInfo adminInfo);

    List<ChargeApply> getAllChargeApply(ChargeApply record);

    public Object approveCharge(String email,String adminId, String isApprove, String applyId);

    public Object approveWithdrawal(String email, String adminId, int isApprove, String withdrawalId);

    public Object updateSupervice(SuperviseRange superviseRange);

}

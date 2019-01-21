package com.bitedu.service;

import com.bitedu.pojo.AdminInfo;
import com.bitedu.pojo.ChargeApply;
import com.bitedu.pojo.fabric.Administrator;

import java.util.List;

public interface AdminService {

    public Object insertAdmin(AdminInfo adminInfo);

    public AdminInfo getAdminByAdminId(String adminId);

    public Object updateAdmin(AdminInfo adminInfo);

    List<ChargeApply> getAllChargeApply(ChargeApply record);

    public Object approveCharge(String email,String adminId, String isApprove, String applyId);

}

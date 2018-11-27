package com.bitedu.service;

import com.bitedu.pojo.AdminInfo;
import com.bitedu.pojo.fabric.Administrator;

public interface AdminService {

    public Object insertAdmin(AdminInfo adminInfo);

    public AdminInfo getAdminByAdminId(String adminId);

    public Object updateAdmin(AdminInfo adminInfo);


}

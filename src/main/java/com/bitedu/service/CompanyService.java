package com.bitedu.service;

import com.bitedu.pojo.CompanyInfo;
import com.bitedu.pojo.UserInfo;

public interface CompanyService {


    public Object insertCompany(CompanyInfo companyInfo);

    public CompanyInfo getCompanyByEmail(String email);

    public Object updateCompany(CompanyInfo companyInfo);

}

package com.bitedu.config;

import com.bitedu.common.StatusCode;
import com.bitedu.pojo.AdminInfo;
import com.bitedu.pojo.CompanyInfo;
import com.bitedu.pojo.UserInfo;
import com.bitedu.service.AdminService;
import com.bitedu.service.CompanyService;
import com.bitedu.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private CompanyService companyService;


    @Autowired
    @Lazy
    private AdminService adminService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("执行授权逻辑");

        return null;

    }

    //认证 包括登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {


        CustomizedToken token = (CustomizedToken)authenticationToken;

        String email = token.getUsername();


        int loginType = token.getLoginType();

        if(loginType==StatusCode.USER) {

            UserInfo userInfo = userService.getUserByEmail(email);


            if (userInfo == null) {
                return null;
            }


            return new SimpleAuthenticationInfo(email, userInfo.getPassword(), "");

        }else if(loginType==StatusCode.COMPANY){

            CompanyInfo companyInfo = companyService.getCompanyByEmail(email);


            if (companyInfo == null) {
                return null;
            }


            return new SimpleAuthenticationInfo(email, companyInfo.getPassword(), "");


        }else if(loginType==StatusCode.ADMIN){

            AdminInfo adminInfo = adminService.getAdminByAdminId(email);

            if(adminInfo==null){
                return null;
            }

            return new SimpleAuthenticationInfo(email,adminInfo.getPassword(),"");

        }

        return null;
    }



}

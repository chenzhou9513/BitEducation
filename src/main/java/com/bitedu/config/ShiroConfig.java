package com.bitedu.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig{

    @Bean(name = "shiroRealm")
    public ShiroRealm getUserRealm(){

        return new ShiroRealm();
    }

    @Bean(name="securityManager")
    public DefaultWebSecurityManager getUserWebSecurityManager(@Qualifier("shiroRealm")ShiroRealm userShiroRealm){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setRealm(userShiroRealm);
        return securityManager;
    }

    @Bean(name="userShiroFilterFactoryBean")
    public ShiroFilterFactoryBean getUserShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager defaultWebSecurityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        /*
            添加过滤器拦截(url)
                常用过滤器
                    anon: 无需拦截
                    authc: 必须认证才能访问
                    user: 如果使用rememberMe的功能可以直接访问
                    perms: 该资源必须得到资源权限才可以访问
                    role: 该资源必须得到角色权限才可以访问
         */


        Map<String,String> filterMap = new LinkedHashMap<String,String>();

        filterMap.put("/user/*", "authc");
        filterMap.put("/logout/user", "authc");
        filterMap.put("/company/*", "authc");
        filterMap.put("/logout/company", "authc");

        /*

            或者
            filterMap.put("/company/*","authc");
            放行某个请求  filterMap.put("/company/login","anon");


         */



        //设置登录页面，authc没通过会跳转

        shiroFilterFactoryBean.setLoginUrl("/unlogin");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;

    }


}

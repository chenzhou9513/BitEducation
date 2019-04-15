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
        Map<String,String> filterMap = new LinkedHashMap<String,String>();
        filterMap.put("/user/*", "anon");
        filterMap.put("/company/*", "anon");
        filterMap.put("/admin/*", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;

    }


}
